package cc.labiras.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Message;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.caelum.vraptor.view.Results;
import cc.labiras.web.model.Etapa;
import cc.labiras.web.model.Inscrito;
import cc.labiras.web.util.StringUtils;
import cc.labiras.web.util.controller.helper.ControllerHelper;
import cc.labiras.web.util.sync.ControladorCadastrosSincronizados;
import cc.labiras.web.util.vraptor.Internationalization;
import cc.labiras.web.util.vraptor.component.Config;

@Controller
public class InscricaoController extends ControllerHelper {
	private final Validator validator;
	private final ControladorCadastrosSincronizados controladorInscricoes;
	
	/** CDI @deprecated */ @Deprecated
	InscricaoController() { this(null, null, null, null, null, null, null, null); }
	
	@Inject
	public InscricaoController(final Result result, final HttpServletRequest request, final HttpServletResponse response, final Session session, final Internationalization i18n, final Config config, final Validator validator, final ControladorCadastrosSincronizados controladorInscricoes) {
		super(result, request, response, session, i18n, config);
		this.validator = validator;
		this.controladorInscricoes = controladorInscricoes;
	}
	
	@Path("/")
	public void index() { }
	
	@Path("/profile/{id:\\d+}")
	public void profile(final Long id) {
		final Inscrito inscrito = (Inscrito) session.get(Inscrito.class, id);
		
		if (inscrito != null) {
			result.include("inscrito", inscrito);
		}
		else {
			result.redirectTo(this).index();
		}
	}
	
	@Post
	@Path("/cadastrar")
	public void cadastrar(final Inscrito i) {
		final Etapa etapa = etapaAtual();
		
		if (etapa != null) {
			if (i != null) {
				if (controladorInscricoes.etapa(etapa).adicionarInscrito()) {
					i.setDataInscricao(new Date());
					i.setPago(etapa.getPreco() <= 0);
					if (i.isPago()) { i.setDataPagamento(new Date()); }
					i.setEtapa(etapa);
					
					validateInscrito(i, null, null);
					
					if (!validator.hasErrors()) {
						final Transaction t = session.beginTransaction();
						
						try {
							session.save(i);
							t.commit();
							
							if (isAjaxRequest()) {
								jsonResponse(i);
							}
							else {
								result.redirectTo(this).profile(i.getId());
							}
						} catch (final Exception e) {
							e.printStackTrace();
							controladorInscricoes.etapa(etapa).removerInscrito();
							
							try {
								t.rollback();
							} catch (final Exception e2) {
								sendErrors("Erro ao salvar informações no banco de dados!!");
								return;
							}
							
							sendErrors("Erro ao salvar informações no banco de dados");
						}
					}
					else {
						sendErrors();
					}
				}
				else {
					sendErrors("Infelizmente não existem mais vagas disponíveis no momento :(");
				}
			}
			else {
				result.use(Results.http()).sendError(HttpServletResponse.SC_BAD_REQUEST, "Por favor, especifique os parâmetros necessários para a inscrição.");
			}
		}
		else {
			result.use(Results.http()).sendError(HttpServletResponse.SC_EXPECTATION_FAILED, "Não há nenhuma etapa de inscrição aberta no momento.");
		}
	}
	
	@Post
	@Path("/alterar")
	public void alterar(final Inscrito i) {
		if (i != null && i.getId() != null && i.getId() > 0) {
			final Inscrito inscritoDB = (Inscrito) session.get(Inscrito.class, i.getId());
			
			if (inscritoDB != null) {
				i.setDataInscricao(inscritoDB.getDataInscricao());
				i.setPago(inscritoDB.isPago());
				i.setDataPagamento(inscritoDB.getDataPagamento());
				i.setEtapa(inscritoDB.getEtapa());
				
				validateInscrito(i, inscritoDB.getCpf(), inscritoDB.getEmail());
				
				if (!validator.hasErrors()) {
					final Transaction t = session.beginTransaction();
					
					try {
						final Inscrito salvo = (Inscrito) session.merge(i);
						t.commit();
						
						if (isAjaxRequest()) {
							jsonResponse(salvo);
						}
						else {
							result.redirectTo(this).profile(salvo.getId());
						}
					} catch (final Exception e) {
						e.printStackTrace();
						
						try {
							t.rollback();
						} catch (final Exception e2) {
							sendErrors("Erro ao salvar informações no banco de dados!!");
							return;
						}
						
						sendErrors("Erro ao salvar informações no banco de dados");
					}
				}
				else {
					sendErrors();
				}
			}
			else {
				sendErrors("Sinto muito, mas você não existe em nosso banco de dados :(");
			}
		}
		else {
			sendErrors("Parâmetros inválidos");
		}
	}
	
	@Path("/api/inscrito")
	public void inscrito(final String email, final String cpf) {
		if (email != null && cpf != null) {
			final Inscrito inscrito = (Inscrito) query("FROM Inscrito WHERE email = :email AND cpf = :cpf").setString("email", email).setString("cpf", cpf).uniqueResult();
			
			if (inscrito != null) {
				if (isAjaxRequest()) {
					jsonResponse(inscrito);
				}
				else {
					result.redirectTo(this).profile(inscrito.getId());
				}
			}
			else {
				result.notFound();
			}
		}
		else {
			result.notFound();
		}
	}
	
	private Etapa etapaAtual() {
		return (Etapa) query("FROM Etapa WHERE dataInicio <= NOW() AND (dataFim IS NULL OR dataFim > NOW()) ORDER BY dataInicio DESC").setMaxResults(1).uniqueResult();
	}
	
	private void validateInscrito(final Inscrito inscrito, final String cpf, final String email) {
		naoPodeSerVazio("Nome", inscrito.getNome(), 256);
		naoPodeSerVazio("CPF", inscrito.getCpf(), 32, "\\d{3}\\.?\\d{3}\\.?\\d{3}\\-?\\d{2}");
		naoPodeSerVazio("E-mail", inscrito.getEmail(), 256, "[^@]+@[^@]+(\\.\\w+){1,3}");	// email@alguma.coisa.com
		if (inscrito.getEmail() != null) {
			final int emailCount;
			
			if (email != null) {
				emailCount = ((Number) query("SELECT count(*) FROM Inscrito WHERE email = :email AND email <> :originalEmail").setString("email", inscrito.getEmail()).setString("originalEmail", email).uniqueResult()).intValue();
			}
			else {
				emailCount = ((Number) query("SELECT count(*) FROM Inscrito WHERE email = :email").setString("email", inscrito.getEmail()).uniqueResult()).intValue();
			}
			
			if (emailCount > 0) { validator.add(new SimpleMessage("validation", "Este E-mail já encontra-se cadastrado!")); }
		}
		
		if (inscrito.getCpf() != null) {
			if (!isCPF(inscrito.getCpf())) {
				validator.add(new SimpleMessage("validation", "CPF inválido"));
			}
			else {
				final int cpfCount;
				
				if (cpf != null) {
					cpfCount = ((Number) query("SELECT count(*) FROM Inscrito WHERE cpf = :cpf AND cpf <> :originalCpf").setString("cpf", inscrito.getCpf()).setString("originalCpf", cpf).uniqueResult()).intValue();
				}
				else {
					cpfCount = ((Number) query("SELECT count(*) FROM Inscrito WHERE cpf = :cpf").setString("cpf", inscrito.getCpf()).uniqueResult()).intValue();
				}
				
				if (cpfCount > 0) { validator.add(new SimpleMessage("validation", "Este CPF já encontra-se cadastrado!")); }
			}
		}
		
		if (inscrito.isEstudante()) {
			if (inscrito.getNivelGraduacao() == null) { validator.add(new SimpleMessage("validation", "Você deve selecionar um Nível de Graduação")); }
//			naoPodeSerVazio("Nível de Graduação", inscrito.getNivelGraduacao(), 256);
			naoPodeSerVazio("Curso", inscrito.getCurso(), 256);
			naoPodeSerVazio("Última Instituição de Ensino", inscrito.getUltimaInstituicao(), 256);
		}
		
		if (inscrito.isProfissional()) {
			naoPodeSerVazio("Empresa", inscrito.getEmpresa(), 256);
		}
		
		naoPodeSerVazio("Cidade", inscrito.getCidade(), 128);
		naoPodeSerVazio("UF", inscrito.getUf(), 2);
		
		if (inscrito.getComoFicouSabendoDoEvento() == null) { validator.add(new SimpleMessage("validation", "Você deve selecionar Como você ficou sabendo do Evento")); }
//		naoPodeSerVazio("Como ficou sabendo do Evento?", inscrito.getComoFicouSabendoDoEvento(), 256);
		
		if (inscrito.getIdade() < 1) { validator.add(new SimpleMessage("validation", "Idade inválida")); }
	}
	
	private void naoPodeSerVazio(final String campo, final String valor, final int tamanho) {
		if (StringUtils.isBlank(valor)) {
			validator.add(new SimpleMessage("validation", "O campo \"" + campo + "\" deve ser preenchido"));
		}
		else if (valor.length() > tamanho) {
			validator.add(new SimpleMessage("validation", "O campo \"" + campo + "\" só pode ter até " + tamanho + " caracteres"));
		}
	}
	
	private void naoPodeSerVazio(final String campo, final String valor, final int tamanho, final String regex) {
		if (StringUtils.isBlank(valor)) {
			validator.add(new SimpleMessage("validation", "O campo \"" + campo + "\" deve ser preenchido"));
		}
		else if (valor.length() > tamanho) {
			validator.add(new SimpleMessage("validation", "O campo \"" + campo + "\" só pode ter até " + tamanho + " caracteres"));
		}
		else if (!valor.matches(regex)) {
			validator.add(new SimpleMessage("validation", "O campo \"" + campo + "\" está inválido"));
		}
	}
	
	private void sendErrors() {
		final List<Message> errorMessages = validator.getErrors();
		final List<String> errorMessagesStrings = new ArrayList<>(errorMessages.size());
		final Map<String, Object> errors = new HashMap<>(1);
		
		for (final Message message : errorMessages) {
			errorMessagesStrings.add(message.getMessage());
		}
		
		errors.put("errors", errorMessagesStrings);
		
		setupJsonValidationResult(validator, errors);
	}
	
	private void sendErrors(final String... messages) {
		if (isAjaxRequest()) {
			final Map<String, Object> errors = new HashMap<>(1);
			errors.put("errors", Arrays.asList(messages));
			setupJsonValidationResult(validator, errors);
		}
		else {
			result.include("errors", Arrays.asList(messages));
			validator.onErrorRedirectTo(this).index();
		}
	}
	
	/**
	 * Faz a validação de um CPF brasileiro
	 * @param CPF CPF sem separador de dígitos
	 * @return {@code true} se o CPF for válido
	 */
	private boolean isCPF(final String CPF) {
		// considera-se erro CPF's formados por uma sequencia de numeros iguais
		if (CPF.equals("00000000000") || CPF.equals("11111111111") || CPF.equals("22222222222") || CPF.equals("33333333333") || CPF.equals("44444444444") || CPF.equals("55555555555") || CPF.equals("66666666666") || CPF.equals("77777777777") || CPF.equals("88888888888") || CPF.equals("99999999999") || (CPF.length() != 11)) {
			return false;
		}
		
		char dig10, dig11;
		int sm, i, r, num, peso;
		
		// Calculo do 1o. Digito Verificador
		sm = 0;
		peso = 10;
		for (i = 0; i < 9; i++) {
			// converte o i-esimo caractere do CPF em um numero:
			// por exemplo, transforma o caractere '0' no inteiro 0
			// (48 eh a posicao de '0' na tabela ASCII)
			num = CPF.charAt(i) - 48;
			sm = sm + (num * peso);
			peso = peso - 1;
		}
		
		r = 11 - (sm % 11);
		if ((r == 10) || (r == 11)) {
			dig10 = '0';
		} else {
			dig10 = (char) (r + 48); // converte no respectivo caractere numerico
		}
		
		// Calculo do 2o. Digito Verificador
		sm = 0;
		peso = 11;
		for (i = 0; i < 10; i++) {
			num = CPF.charAt(i) - 48;
			sm = sm + (num * peso);
			peso = peso - 1;
		}
		
		r = 11 - (sm % 11);
		if ((r == 10) || (r == 11)) {
			dig11 = '0';
		} else {
			dig11 = (char) (r + 48);
		}
		
		// Verifica se os digitos calculados conferem com os digitos informados.
		if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10))) {
			return true;
		} else {
			return false;
		}
	}
}
