package org.vraptor.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Describes a method as a logic
 * 
 * @author Guilherme Silveira
 */
@Target(ElementType.METHOD)
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Logic {
	/**
	 * Logic names
	 * 
	 * @return names
	 */
	public String[] value();
}
