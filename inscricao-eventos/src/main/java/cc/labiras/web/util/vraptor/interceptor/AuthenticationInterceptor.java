package cc.labiras.web.util.vraptor.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import cc.labiras.web.model.User;
import cc.labiras.web.util.CurrentUser;
import cc.labiras.web.util.vraptor.annotation.Auth;

/**
 * VRaptor's {@link Interceptor} to control access to {@link Auth certain areas of the system} marked with
 * the {@link Auth @Auth} annotation.
 * 
 * @author Rafael Lins - D00162108
 *
 */
//@Intercepts
public abstract class AuthenticationInterceptor implements Interceptor {
	private static final Logger log = LoggerFactory.getLogger(AuthenticationInterceptor.class);
	
	protected final Result result;
	protected final HttpServletRequest request;
	protected final HttpSession session;
	
	/**
	 * @return The Current User (as it'll be set at the {@link CurrentUser}'s {@link ThreadLocal})
	 */
	public abstract User getCurrentUser();
	
	/**
	 * What should be done when the access is unauthorised?
	 */
	public abstract void unauthorised();
	
	/**
	 * The user should be sent to the Login page
	 */
	public abstract void sendToLogin();
	
//	/** Needed by CDI - @deprecated */
//	@Deprecated	AuthenticationInterceptor() { this(null, null); }
//
//	@Inject
	public AuthenticationInterceptor(final Result result, final HttpServletRequest request) {
		this.result = result;
		this.request = request;
		session = request != null ? request.getSession() : null;
	}
	
	/**
	 * Returns <code>true</code> if the current request should be intercepted by this {@link Interceptor}.
	 * Since this is a lazy interceptor, it'll be initialised only when this returns true.
	 * 
	 * @see Interceptor
	 */
	public boolean accepts(final ControllerMethod method) {
		final boolean result = method.getController().getType().isAnnotationPresent(Auth.class) || method.getMethod().isAnnotationPresent(Auth.class);
		if (log.isDebugEnabled()) { log.debug(result ? "This request needs authentication to be served." : "This request does not need authentication to be served. Ignoring."); }
		return result;
	}
	
	/**
	 * Checks if there's an {@link Auth @Auth} annotation on the method that'll be called. If it's present,
	 * execute the whole validation of this access (authorisation of access).
	 */
	public void intercept(final InterceptorStack stack, final ControllerMethod method, final Object resourceInstance) throws InterceptionException {
		Auth authData = method.getMethod().getAnnotation(Auth.class);
		
		if (authData == null) {
			authData = method.getController().getType().getAnnotation(Auth.class);
			if (log.isDebugEnabled()) { log.debug("@Auth found on class."); }
		}
		else if (log.isDebugEnabled()) { log.debug("@Auth found on method."); }
		
		if (authData.value()) {
//			final User currentUser = (User) session.getAttribute(AuthController.SESSION_CURRENT_USER);
			final User currentUser = getCurrentUser();
			
			// Is there a current user?
			if (currentUser != null) {
				final String[] roleRestriction = authData.roleRestriction();
				
				if (roleRestriction != null && roleRestriction.length > 0) {
					if (log.isDebugEnabled()) { log.debug("User have to have an especific role to access this. Checking..."); }
					boolean hasRole = false;
					
					for (final String role : roleRestriction) {
						if (currentUser.isRole(role)) {
							if (log.isDebugEnabled()) { log.debug("User has the \"" + role + "\" role. Setting hasRole = true and breaking."); }
							hasRole = true;
							break;
						}
						else if (log.isDebugEnabled()) { log.debug("User does not have the \"" + role + "\" role."); }
					}
					
					if (!hasRole) {
						if (log.isDebugEnabled()) { log.debug("Current user does not have the necessary role to access this. Terminating request and redirecting to AuthController.unauthorized()..."); }
//						result.redirectTo(AuthController.class).unauthorised();
						unauthorised();
						return;
					}
				}
				else if (log.isDebugEnabled()) { log.debug("User simply have to be logged in to access this (no restrictions regarding a especific user role)."); }
				
				if (log.isDebugEnabled()) { log.debug("User have the right to execute this request. Continuing..."); }
				
				// Continue the execution of this request:
				CurrentUser.set(currentUser);
				stack.next(method, resourceInstance);
			}
			// The user is not logged in. Redirect to the login page:
			else {
				if (log.isDebugEnabled()) { log.debug("There is no user logged in. Terminating request and redirecting to AuthController.login()..."); }
				
				CurrentUser.set(null);
				sendToLogin();
//				if (request.getMethod().toUpperCase().equals("GET")) {
//					if (log.isDebugEnabled()) { log.debug("Request is a GET. Setting the post-login redirection URL to this request URI: " + request.getRequestURI()); }
//					session.setAttribute(AuthController.SESSION_LOGIN_REDIRECT, request.getRequestURI());
//				}
//				result.redirectTo(AuthController.class).login();
			}
		}
		else {
			if (log.isDebugEnabled()) { log.debug("@Auth stated that this request does not need authentication (value = false). Please, remove this annotation to optimize the system since the AuthenticationInterceptor is instantiated and initialised only if the annotation is present."); }
			stack.next(method, resourceInstance);
		}
	}
}
