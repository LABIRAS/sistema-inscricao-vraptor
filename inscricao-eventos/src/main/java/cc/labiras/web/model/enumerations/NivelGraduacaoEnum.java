package cc.labiras.web.model.enumerations;

public enum NivelGraduacaoEnum {
	FUNDAMENTAL_INCOMPLETO("Ensino Fundamental Incompleto"),
	FUNDAMENTAL_COMPLETO("Ensino Fundamental Completo"),
	MEDIO_INCOMPLETO("Ensino Médio Incompleto"),
	MEDIO_COMPLETO("Ensino Médio Completo"),
	TECNICO("Técnico"),
	LICENCIATURA("Licenciatura"),
	TECNOLOGO("Tecnólogo"),
	BACHAREL("Bacharel"),
	ESPECIALIZACAO("Especialização"),
	MESTRADO("Mestrado"),
	DOUTORADO("Doutorado"),
	POS_DOUTORADO("Pós-Doutorado");
	
	public final String descricao;
	
	private NivelGraduacaoEnum(final String descricao) {
		this.descricao = descricao;
	}
}
