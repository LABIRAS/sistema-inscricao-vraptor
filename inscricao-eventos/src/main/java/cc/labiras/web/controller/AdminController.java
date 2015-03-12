package cc.labiras.web.controller;

import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import cc.labiras.web.model.User;
import cc.labiras.web.util.PasswordUtils;
import cc.labiras.web.util.StringUtils;
import cc.labiras.web.util.controller.helper.ControllerHelper;
import cc.labiras.web.util.vraptor.Internationalization;
import cc.labiras.web.util.vraptor.component.Config;

/**
 * Controller para as funçõs de administração do sistema.
 * 
 * @author Rafael Lins - g0dkar
 *
 */
@Controller
public class AdminController extends ControllerHelper {
	public static final String USUARIO = "USUARIO_SESSAO";
	public static final String REDIRECT = "REDIRECT_USUARIO";
	public static final int REMEMBER_ME_COOKIE_TTL = 7 * 24 * 60 * 60;	// Time To Live - in seconds
	
	/** CDI @deprecated */ @Deprecated
	AdminController() { this(null, null, null, null, null, null); }
	
	@Inject
	public AdminController(final Result result, final HttpServletRequest request, final HttpServletResponse response, final Session session, final Internationalization i18n, final Config config) {
		super(result, request, response, session, i18n, config);
	}
	
	/**
	 * Verifica se há um usuário logado (faz login automático, se o usuário marcou "Lembrar de mim")
	 */
	private boolean logado() {
		if (request.getSession().getAttribute(USUARIO) == null && !authAuto(getCookieValue("aauth"), false)) {
			removeCookie("aauth");
			result.redirectTo(this).login();
			return false;
		}
		
		return true;
	}
	
	/**
	 * Tela de login. Se o cara já tá logado, manda ele para o dashboard (ou algum outro lugar via parâmetro na sessão)
	 */
	@Path("/login")
	public void login() {
		if (logado()) {
			if (request.getSession().getAttribute(REDIRECT) != null) {
				result.redirectTo(request.getSession().getAttribute(REDIRECT));
			}
			else {
				result.redirectTo(this).dashboard();
			}
		}
	}
	
	/**
	 * Faz o logout do usuário (invalida a sessão dele, remove o cookie de autenticação automática e o manda para a tela de login)
	 */
	@Path("/logout")
	public void logout() {
		removeCookie("aauth");
		request.getSession().invalidate();
		result.redirectTo(this).login();
	}
	
	/**
	 * Executa a autenticação
	 * @param username Nome de usuário ou e-mail
	 * @param password Senha
	 * @param remember "Lembrar de mim?"
	 */
	@Post
	@Path("/auth")
	public void auth(final String username, final String password, final String remember) {
		if (!StringUtils.isBlank(username) && !StringUtils.isBlank(password)) {
			final User user = (User) query("FROM User WHERE username = :username OR email = :username").setString("username", username).uniqueResult();
			if (user != null) {
				if (PasswordUtils.validatePasswordSafe(password, user.getPassword())) {
					request.getSession().setAttribute(USUARIO, user);
					
					if (StringUtils.parseBoolean(remember, false)) {
						createAutoAuthCookie(user);
					}
					
					result.include("success", "Bem-vindo(a) " + user.getUsername());
				}
				else {
					result.include("error", "Usuário ou senha errados!");
				}
			}
			else {
				result.include("error", "Usuário ou senha errados.");
			}
		}
		else {
			if (log.isWarnEnabled()) { log.warn("Acesso a /auth sem credenciais de autenticação vindo de " + request.getRemoteAddr()); }
		}
		
		result.redirectTo(this).login();
	}
	
	@Path("/dashboard")
	public void dashboard() {
		/* Só para o VRaptor mandar para a página certinha */
	}
	
	/**
	 * Faz o login automaticamente a partir de um token de login
	 * 
	 * @param token Auth Token
	 * @see #login()
	 */
	private boolean authAuto(final String token, final boolean redirect) {
		if (validateAutoAuthToken(token)) {
			final String userIDString = token.substring(141);
			final User user = (User) session.get(User.class, Long.parseLong(userIDString, 16));
			
			httpSession.setAttribute(USUARIO, user);
			
			if (redirect) {
				result.redirectTo(this).login();
			}
			
			return true;
		}
		else {
			if (redirect) {
				httpSession.removeAttribute(USUARIO);
				result.include("error", "Seu tempo de lembrança de login automático expirou, por favor faça login novamente.");
				result.redirectTo(this).login();
			}
			
			return false;
		}
	}
	
	/**
	 * Gera um token para autenticação automática
	 * 
	 * @param user Usuário que será autenticado
	 * @return O token de autenticação
	 */
	private String generateAutoAuthToken(final User user) {
		final long timestamp = System.currentTimeMillis();
		final String overhead = UUID.randomUUID().toString().replace("-", "").toUpperCase();
		final String ip = request.getRemoteAddr();
		final String token = String.format("%013X%s%s%X", timestamp, PasswordUtils.createHashSafe(ip + timestamp + user.getUsername() + user.getEmail() + user.getPassword()), overhead, user.getId());
		
		if (log.isDebugEnabled()) { log.debug("Generating Automatic Authentication token. IP: " + ip + ", Timestamp: " + timestamp + ", overhead: " + overhead + ", username: " + user.getUsername() + ", email: " + user.getEmail() + ", password: " + user.getPassword() + ", final token: " + token); }
		
		return token;
	}
	
	/**
	 * Faz a validação de um token de autenticação automática
	 * 
	 * @param token Automatic Authentication Token
	 * @return <code>true</code> if the token is valid (the user can be authenticated)
	 */
	private boolean validateAutoAuthToken(final String token) {
		if (log.isDebugEnabled()) { log.debug("Validating Automatic Authentication token..."); }
		
		if (token != null && token.length() > 141) {
			final String timestampString = token.substring(0, 13);
			
			if (timestampString.matches("[A-Z0-9]+")) {
				final long maxValidTimestamp = System.currentTimeMillis() - (REMEMBER_ME_COOKIE_TTL * 1000);
				final long timestamp = Long.parseLong(timestampString, 16);
				
				if (timestamp >= maxValidTimestamp) {
					final String userIDString = token.substring(141);
					final String ip = request.getRemoteAddr();
					final User user = (User) session.get(User.class, Long.parseLong(userIDString, 16));
					
					if (user != null) {
						final String password = token.substring(13, 109);
						final boolean validPassword = PasswordUtils.validatePasswordSafe(ip + timestamp + user.getUsername() + user.getEmail() + user.getPassword(), password);
						
						if (validPassword) {
							if (log.isDebugEnabled()) { log.debug("Automatic Authentication token is valid!"); }
							return validPassword;
						}
						else {
							if (log.isDebugEnabled()) { log.debug("Automatic Authentication token invalid: Credentials don't match (one of username, password or user e-mail have changed, thus this token is invalid)"); }
						}
					}
					else {
						if (log.isDebugEnabled()) {
							if (user == null) {
								log.debug("Automatic Authentication token invalid: User " + userIDString + " does not exist");
							}
						}
					}
				}
				else {
					if (log.isDebugEnabled()) { log.debug("Automatic Authentication token invalid: Timestamp is outdated"); }
				}
			}
			else {
				if (log.isDebugEnabled()) { log.debug("Automatic Authentication token invalid: invalid timestamp"); }
			}
		}
		
		if (log.isDebugEnabled()) { log.debug("Automatic Authentication token inexistent or shorter than 142 characters..."); }
		
		return false;
	}
	
	/**
	 * Creates a {@link Cookie} with an automatic authentication token (this method does <strong>not</strong> invoke the
	 * {@link HttpServletResponse#addCookie(Cookie)} method)
	 * 
	 * @param user User which the authentication token will be generated
	 * @return {@link Cookie} ready to be used
	 * @see HttpServletResponse#addCookie(Cookie)
	 */
	private Cookie createAutoAuthCookie(final User user) {
		final Cookie cookie = new Cookie("aauth", generateAutoAuthToken(user));
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		cookie.setMaxAge(REMEMBER_ME_COOKIE_TTL);
		
		return cookie;
	}
}
