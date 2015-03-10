package cc.labiras.web.model.enumerations;

public enum ComoFicouSabendoEnum {
	NENHUM("Nenhum"),
	SITE_EVENTO("Site do Evento"),
	FACEBOOK("Post no Facebook"),
	TWITTER("Twitter"),
	OUTDOOR("Outdoor"),
	AMIGO("Fiquei sabendo por Amigos"),
	MATERIA_INTERNET("Matéria em Portal de Notícias"),
	MATERIA_JORNAL("Matéria em Jornal Impresso"),
	MATERIA_TV("TV"),
	OUTROS("Outras");
	
	public final String descricao;
	
	private ComoFicouSabendoEnum(final String descricao) {
		this.descricao = descricao;
	}
}
