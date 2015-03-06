package cc.labiras.web.util.vraptor;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Weld Factory for the {@link SessionFactory Hibernate Session Factory}.
 * 
 * @author Rafael Lins
 *
 */
@ApplicationScoped
public class SessionFactoryCreator {
	private static final Logger log = LoggerFactory.getLogger(SessionFactoryCreator.class);
	
	private Configuration configuration;
	private SessionFactory sessionFactory;
	private boolean created = false;
	private boolean destroyed = false;
	
	@PostConstruct
	public void create() {
		if (log.isDebugEnabled()) { log.debug("Initialising Hibernate..."); }
		
		// Initialises Hibernate
		configuration = new Configuration();
		configuration.configure();
		
		if (log.isDebugEnabled()) { log.debug("Creating SessionFactory..."); }
		final Properties properties = configuration.getProperties();
		final ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(properties).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		
		// And build the SessionFactory
//		sessionFactory = configuration.buildSessionFactory();
		if (log.isDebugEnabled()) { log.debug("Done."); }
		
		created = true;
	}

	@Produces @ApplicationScoped
	public SessionFactory getInstance() {
		return sessionFactory;
	}
	
	/**
	 * For testing purposes. Returns the {@link Configuration Hibernate Configuration Object} from which the
	 * {@link SessionFactory} was built.
	 * 
	 * @return The {@link #configuration} field.
	 */
	public Configuration getConfiguration() {
		return configuration;
	}

	@PreDestroy
	public void destroy() {
		if (!sessionFactory.isClosed()) {
			if (log.isDebugEnabled()) { log.debug("Closing the SessionFactory..."); }
			sessionFactory.close();
			if (log.isDebugEnabled()) { log.debug("SessionFactory closed."); }
			destroyed = true;
		}
	}

	public boolean isCreated() {
		return created;
	}

	public boolean isDestroyed() {
		return destroyed;
	}
}
