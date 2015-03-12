package cc.labiras.web.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import cc.labiras.web.model.Etapa;
import cc.labiras.web.model.Inscrito;
import cc.labiras.web.model.User;
import cc.labiras.web.util.StringUtils;
import cc.labiras.web.util.controller.helper.ControllerHelper;
import cc.labiras.web.util.vraptor.Internationalization;
import cc.labiras.web.util.vraptor.component.Config;

@Controller
@Path("/dados")
public class DadosController extends ControllerHelper {
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
	
	/** CDI @deprecated */ @Deprecated
	DadosController() { this(null, null, null, null, null, null); }
	
	@Inject
	public DadosController(final Result result, final HttpServletRequest request, final HttpServletResponse response, final Session session, final Internationalization i18n, final Config config) {
		super(result, request, response, session, i18n, config);
	}
	
	/**
	 * Verifica se há um usuário logado (faz login automático, se o usuário marcou "Lembrar de mim")
	 */
	private boolean logado() {
		if (request.getSession().getAttribute(AdminController.USUARIO) == null) {
			result.use(Results.http()).sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return false;
		}
		
		return true;
	}
	
	@Path("/etapa/list")
	public void etapas(final String offset, final String max) {
		if (logado()) {
			final int resultOffset = Math.max(0, StringUtils.parseInteger(offset, 0));
			final int resultMax = Math.min(100, Math.max(0, StringUtils.parseInteger(offset, 0)));
			jsonResponse(query("FROM Etapa").setFirstResult(resultOffset).setMaxResults(resultMax).list());
		}
	}
	
	@Path("/etapa/{id:\\d+}")
	public void etapa(final long id) {
		if (logado()) {
			final Etapa etapa = (Etapa) session.get(Etapa.class, id);
			
			if (etapa != null) {
				jsonResponse(etapa);
			}
			else {
				result.notFound();
			}
		}
	}
	
	@Path("/inscrito/list")
	public void inscritos(final String etapa, final String offset, final String max) {
		if (logado()) {
			final int resultOffset = Math.max(0, StringUtils.parseInteger(offset, 0));
			final int resultMax = Math.min(100, Math.max(0, StringUtils.parseInteger(offset, 0)));
			
			final long etapaId = StringUtils.parseLong(etapa, 0);
			if (etapaId > 0) {
				jsonResponse(query("FROM Inscrito WHERE etapa.id = :etapa").setLong("etapa", etapaId).setFirstResult(resultOffset).setMaxResults(resultMax).list());
			}
			else {
				jsonResponse(query("FROM Inscrito").setFirstResult(resultOffset).setMaxResults(resultMax).list());
			}
		}
	}
	
	@Path("/inscrito/{id:\\d+}")
	public void inscrito(final long id) {
		if (logado()) {
			final Inscrito inscrito = (Inscrito) session.get(Inscrito.class, id);
			
			if (inscrito != null) {
				jsonResponse(inscrito);
			}
			else {
				result.notFound();
			}
		}
	}
	
	@Path("/inscrito/por-dia")
	public void inscritosPorDia(final String etapa, final String dataInicio, final String dataFim) {
		if (logado()) {
			final Calendar inicio = Calendar.getInstance();
			final Calendar fim = Calendar.getInstance();
			
			if (dataInicio != null && dataInicio.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
				try { inicio.setTime(DATE_FORMAT.parse(dataInicio)); } catch (final Exception e) { }
			}
			else {
				inicio.add(Calendar.MONTH, -1);
			}
			
			if (dataFim != null && dataFim.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
				try { fim.setTime(DATE_FORMAT.parse(dataFim)); } catch (final Exception e) { }
			}
			
			inicio.set(Calendar.HOUR, 0);
			inicio.set(Calendar.MINUTE, 0);
			inicio.set(Calendar.SECOND, 0);
			inicio.set(Calendar.MILLISECOND, 0);
			fim.set(Calendar.HOUR, 0);
			fim.set(Calendar.MINUTE, 0);
			fim.set(Calendar.SECOND, 0);
			fim.set(Calendar.MILLISECOND, 0);
			
			final long etapaId = StringUtils.parseLong(etapa, 0);
			if (etapaId > 0) {
				jsonResponse(query("SELECT new Map(i.dataInscricao, count(i.id)) FROM Inscrito i WHERE etapa.id = :etapa AND dataInscricao BETWEEN :inicio AND :fim GROUP BY i.dataInscricao ORDER BY i.dataInscricao ASC").setLong("etapa", etapaId).list());
			}
			else {
				jsonResponse(query("SELECT new Map(i.dataInscricao, count(i.id)) FROM Inscrito i WHERE dataInscricao BETWEEN :inicio AND :fim GROUP BY i.dataInscricao ORDER BY i.dataInscricao ASC").list());
			}
		}
	}
	
	@Path("/user/list")
	public void users(final String offset, final String max) {
		if (logado()) {
			final int resultOffset = Math.max(0, StringUtils.parseInteger(offset, 0));
			final int resultMax = Math.min(100, Math.max(0, StringUtils.parseInteger(offset, 0)));
			jsonResponse(query("FROM User").setFirstResult(resultOffset).setMaxResults(resultMax).list());
		}
	}
	
	@Path("/user/{id:\\d+}")
	public void user(final long id) {
		if (logado()) {
			final User user = (User) session.get(User.class, id);
			
			if (user != null) {
				jsonResponse(user);
			}
			else {
				result.notFound();
			}
		}
	}
}
