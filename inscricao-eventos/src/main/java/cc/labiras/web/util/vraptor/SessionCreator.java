package cc.labiras.web.util.vraptor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Weld factory for {@link Session Hibernate Sessions}.
 * 
 * @author Rafael Lins
 *
 */
@RequestScoped
public class SessionCreator {
	private static final Logger log = LoggerFactory.getLogger(SessionCreator.class);
	
	private final SessionFactory factory;
	private Session session;
	
	private boolean created = false;
	private boolean destroyed = false;
	
	protected SessionCreator() {
		factory = null;
	}
	
	@Inject
	public SessionCreator(final SessionFactory factory) {
		this.factory = factory;
	}

	/**
	 * Executed once right before invoking {@link #getInstance()}.
	 */
	@PostConstruct
	public void create() {
		if (log.isDebugEnabled()) { log.debug("Opening Session..."); }
//		session = factory.getCurrentSession();
		session = factory.openSession();
		session.setFlushMode(FlushMode.COMMIT);
		if (log.isDebugEnabled()) { log.debug("Session Opened: {}", session); }
		created = true;
	}
	
	@Produces @Dependent /*@RequestScoped*/
	public Session getInstance() {
		return session;
	}
	
	/**
	 * Executed once right before the object's destruction (after the request is finished)
	 */
	@PreDestroy
	public void destroy(@Disposes final Session session) {
		if (session != null && session.isOpen()) {
			session.close();
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
