package cc.labiras.web.util;
//package responde.ai.util;
//
//import java.nio.file.FileSystems;
//import java.nio.file.Path;
//import java.text.SimpleDateFormat;
//import java.util.List;
//
//import javax.xml.bind.JAXBException;
//
//import org.docx4j.jaxb.Context;
//import org.docx4j.openpackaging.exceptions.Docx4JException;
//import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
//import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
//import org.docx4j.openpackaging.parts.WordprocessingML.StyleDefinitionsPart;
//import org.docx4j.wml.Br;
//import org.docx4j.wml.ObjectFactory;
//import org.docx4j.wml.STBrType;
//import org.hibernate.SQLQuery;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.cfg.Configuration;
//
//public class ApagarIssoUrgente {
//	public static void main(final String[] args) throws Docx4JException, JAXBException {
//		final Configuration config = new Configuration();
//		final SessionFactory sf = config.buildSessionFactory();
//		final Session session = sf.openSession();
//		final SQLQuery query = session.createSQLQuery("SELECT n.id_noticia, n.dt_entrada, u.nome, n.chamada, n.titulo, n.texto, n.keywords, n.fonte FROM old180.mdb_noticias n LEFT JOIN old180.mdb_users u ON n.user_id = u.id_user WHERE secao_id = 145");
//		final List<Object[]> results = query.list();
//		final Path basePath = FileSystems.getDefault().getPath("C:", "temp", "treco180", "materias.docx");
//		final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy 'as' hh:mm");
//		final WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();
//		final MainDocumentPart doc = wordMLPackage.getMainDocumentPart();
//		final StyleDefinitionsPart stylesPart = new StyleDefinitionsPart();
//		stylesPart.unmarshalDefaultStyles();
//		doc.addTargetPart(stylesPart);
//		final ObjectFactory factory = Context.getWmlObjectFactory();
//		final Br br = factory.createBr();
//		br.setType(STBrType.PAGE);
//
//		for (final Object[] result : results) {
//			// DATA por AUTOR
//			doc.addStyledParagraphOfText("Subtitle", dateFormat.format(result[1]) + " por " + result[2]);
//			// Chamada
//			doc.addStyledParagraphOfText("Subtitle", StringUtils.decodeHTML(StringUtils.stripHTML((String) result[3])).replaceAll("&nbsp;", " ").trim());
//			// Titulo
//			doc.addStyledParagraphOfText("Title", StringUtils.decodeHTML(StringUtils.stripHTML((String) result[4])).replaceAll("&nbsp;", " ").trim());
//			// Conteudo
//			doc.addParagraphOfText(StringUtils.decodeHTML(StringUtils.stripHTML((String) result[5])).replaceAll("&nbsp;", " ").trim());
//
//			if (!StringUtils.isBlank((String) result[6])) {
//				final String[] tagsArr = ((String) result[6]).split("\\s*\\|\\s*");
//				final StringBuilder tags = new StringBuilder();
//				for (final String string : tagsArr) {
//					tags.append(StringUtils.decodeHTML(StringUtils.stripHTML(string)).replaceAll("&nbsp;", " ").trim());
//					tags.append(", ");
//				}
//
//				doc.addParagraphOfText("Tags: " + tags.substring(0, tags.length() - 2));
//			}
//
//			if (!StringUtils.isBlank((String) result[7])) {
//				doc.addParagraphOfText("Fonte: " + StringUtils.decodeHTML(StringUtils.stripHTML((String) result[7])).replaceAll("&nbsp;", " ").trim());
//			}
//
//			doc.addObject(br);
//		}
//
//		wordMLPackage.save(basePath.toFile());
//		session.close();
//		sf.close();
//	}
//}
