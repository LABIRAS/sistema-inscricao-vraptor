package cc.labiras.web.util.vraptor.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.hibernate.Transaction;

import cc.labiras.web.util.vraptor.interceptor.HibernateTransactionInterceptor;

/**
 * Annotation to mark which controllers and methods will have {@link Transaction Hibernate Transactions}
 * automatically managed (open then commited/rolled-back) by the {@link HibernateTransactionInterceptor}.
 * 
 * @author Rafael Lins - D00162108
 *
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.TYPE, ElementType.METHOD })
public @interface AutoTransaction {
	boolean value() default true;
}
