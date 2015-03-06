package cc.labiras.web.util.vraptor.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a method or controller showing that it requires authentication to be accessed.
 * 
 * @author Rafael Lins - D00162108
 *
 */
@Documented								// This annotation will show up on the Javadocs
@Inherited								// This annotation will go down the inheritance path (A is annotatated and B extends A, then B will automatically have this annotation)
@Retention(RetentionPolicy.RUNTIME)		// Needed so this annotation can be "read" by the Reflection API at runtime (something.getClass().isAnnotationPresent(Auth.class) will work as expected)
@Target(value = { ElementType.TYPE, ElementType.METHOD })	// This annotation can be used to annotate methods and classes (Java types)
public @interface Auth {
	/**
	 * Is Authentication needed?
	 */
	boolean value() default true;
	
	/**
	 * Which user roles can access this? (none = all user roles)
	 */
	String[] roleRestriction() default {};
}
