package cc.labiras.web.util.vraptor.interceptor;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cc.labiras.web.util.vraptor.annotation.AutoTransaction;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.validator.Validator;

/**
 * Interceptor para abrir/fechar {@link Transaction Transações do Hibernate} automaticamente.
 * 
 * @author g0dkar
 *
 */
@Intercepts/*(after = AuthenticationInterceptor.class)*/
public class HibernateTransactionInterceptor implements Interceptor {
	public static final String RESULT_TRANSACTION_INCLUDE_NAME = "CURRENT_TRANSACTION";
	private static final Logger log = LoggerFactory.getLogger(HibernateTransactionInterceptor.class);
	
	private final Result result;
	private final Session session;
	private final Validator validator;
	
	/** CDI @deprecated */
	@Deprecated HibernateTransactionInterceptor() { this(null, null, null); }
	
	@Inject
	public HibernateTransactionInterceptor(final Result result, final Session session, final Validator validator) {
		this.result = result;
		this.session = session;
		this.validator = validator;
	}
	
	public boolean accepts(final ControllerMethod method) {
		final boolean result = method.getMethod().isAnnotationPresent(AutoTransaction.class) || method.getController().getType().isAnnotationPresent(AutoTransaction.class);
		if (log.isDebugEnabled()) { log.debug(result ? "@AutoTransaction annotation detected. Continuing..." : "@AutoTransaction not present. Ignoring."); }
		return result;
	}
	
	public static Transaction currentTransaction(final Result result, final Session session) {
		Transaction transaction = (Transaction) result.included().get(RESULT_TRANSACTION_INCLUDE_NAME);
		
		if (transaction == null) {
			transaction = session.beginTransaction();
			result.include(RESULT_TRANSACTION_INCLUDE_NAME, transaction);
		}
		
		return transaction;
	}

	public void intercept(final InterceptorStack stack, final ControllerMethod method, final Object instance) {
		AutoTransaction annotation = method.getMethod().getAnnotation(AutoTransaction.class);
		
		if (annotation == null) {
			annotation = method.getController().getType().getAnnotation(AutoTransaction.class);
			if (log.isDebugEnabled()) { log.debug("@AutoTransaction annotation found on Class."); }
		}
		else if (log.isDebugEnabled()) { log.debug("@AutoTransaction annotation found on Method."); }
		
		if (annotation.value()) {
			Transaction transaction = (Transaction) result.included().get(RESULT_TRANSACTION_INCLUDE_NAME);
			
			try {
				if (transaction == null) {
					if (log.isDebugEnabled()) { log.debug("Transaction began: " + transaction); }
					transaction = session.beginTransaction();
					result.include(RESULT_TRANSACTION_INCLUDE_NAME, transaction);
				}
				else if (log.isDebugEnabled()) { log.debug("Transaction already in progress: " + transaction); }
				
				if (log.isDebugEnabled()) { log.debug("Continuing with request execution..."); }
				stack.next(method, instance);
				if (log.isDebugEnabled()) { log.debug("Request execution done. Checking for validation errors..."); }
				
				if (!validator.hasErrors()) {
					if (log.isDebugEnabled()) { log.debug("No validation errors found. Commiting transaction..."); }
					if (transaction.isActive()) {
						transaction.commit();
					}
					if (log.isDebugEnabled()) { log.debug("Commit done."); }
				}
			} catch (final Exception e) {
				if (log.isErrorEnabled()) { log.error("Something went wrong. Logging and passing exception along the interceptor stack.", e); }
				else {
					throw new RuntimeException(e);
				}
			}
			finally {
				if (transaction != null && transaction.isActive()) {
					if (log.isDebugEnabled()) { log.debug("Transaction is still active. Either nothing happened or an unknown error happened. Executing rollback..."); }
					try {
						transaction.rollback();
						if (log.isDebugEnabled()) { log.debug("Rollback done."); }
					}
					catch (final Exception e) {
						if (log.isDebugEnabled()) { log.debug("Error while executing Rollback.", e); }
					}
				}
			}
		}
		else {
			if (log.isDebugEnabled()) { log.debug("@AutoTransaction with value = false, doing nothing and continuing request. Please, consider removing this annotation since this Interceptor will only be instantiated and initialized if the annotation is found, but it won't do anything if value = false."); }
			stack.next(method, instance);
		}
	}
}
