package cc.labiras.web.controller;

import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;
import br.com.caelum.vraptor.view.Results;
import cc.labiras.web.model.Inscrito;
import cc.labiras.web.util.controller.helper.ControllerHelper;
import cc.labiras.web.util.vraptor.Internationalization;
import cc.labiras.web.util.vraptor.component.Config;

@Controller
public class InscricaoController extends ControllerHelper {
	private final Validator validator;
	
	/** CDI @deprecated */ @Deprecated
	InscricaoController() { this(null, null, null, null, null, null, null); }
	
	@Inject
	public InscricaoController(final Result result, final HttpServletRequest request, final HttpServletResponse response, final Session session, final Internationalization i18n, final Config config, final Validator validator) {
		super(result, request, response, session, i18n, config);
		this.validator = validator;
	}
	
	@Path("/")
	public void index() { }
	
	@Post
	@Path("/cadastrar")
	public void cadastrar(final Inscrito i) {
		if (i != null) {
			i.setDataInscricao(new Date());
			i.setPago(config.getBoolean("evento.gratis", true));
			if (i.isPago()) { i.setDataPagamento(new Date()); }
		}
		else {
			result.use(Results.http()).sendError(HttpServletResponse.SC_BAD_REQUEST, "Por favor, especifique os parâmetros necessários para a inscrição.");
		}
	}
	
	private void validateInscrito(final Inscrito inscrito) {
		
	}
}
