package cc.labiras.web.database;

import java.util.Calendar;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import cc.labiras.web.model.Etapa;
import cc.labiras.web.model.User;
import cc.labiras.web.util.PasswordUtils;
import cc.labiras.web.util.vraptor.SessionCreator;
import cc.labiras.web.util.vraptor.SessionFactoryCreator;

public class CreateDatabase {
	private static SessionFactoryCreator sfc;
	private static SessionFactory sf;
	private static SessionCreator sc;
	private static Session session;
	
	@BeforeClass
	public static void init() {
		sfc = new SessionFactoryCreator();
		sfc.create();
		
		sf = sfc.getInstance();
		
		sc = new SessionCreator(sf);
		sc.create();
		
		session = sc.getInstance();
	}
	
	@Test
	public void testCreateDatabase() {
		final Configuration conf = sfc.getConfiguration();
		final SchemaExport export = new SchemaExport(conf);
		export.create(true, true);
		
		final Transaction t = session.beginTransaction();
		try {
			final cc.labiras.web.model.Configuration config = new cc.labiras.web.model.Configuration();
			config.setConfig("evento.gratis");
			config.setValue("true");
			session.save(config);
			
			final User user = new User();
			user.setUsername("admin");
			user.setPassword(PasswordUtils.createHashSafe("javax.admin.Senha"));
			user.setEmail("admin@labiras.cc");
			user.setDateCreated(new Date());
			user.setRole("ADMIN");
			session.save(user);
			
			final Etapa etapa = new Etapa();
			etapa.setInscritos(-1);
			etapa.setNome("Etapa Única");
			etapa.setPreco(0);
			etapa.setDataInicio(Calendar.getInstance());
			
			final Calendar fim = Calendar.getInstance();
			fim.set(Calendar.DAY_OF_MONTH, 28);
			fim.set(Calendar.MONTH, 2);
			fim.set(Calendar.HOUR_OF_DAY, 18);
			fim.set(Calendar.MINUTE, 0);
			fim.set(Calendar.SECOND, 0);
			etapa.setDataFim(fim);
			
			session.save(etapa);
			
			t.commit();
		} catch (final Exception e) {
			e.printStackTrace();
			try { t.rollback(); } catch (final Exception e2) { e2.printStackTrace(); throw e2; }
			throw e;
		}
	}
	
	@AfterClass
	public static void destroy() {
		sfc.destroy();
	}
}
