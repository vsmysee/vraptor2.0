package org.vraptor.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A vraptor's component
 * 
 * @author Guilherme Silveira
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {

	/**
	 * Its name
	 * 
	 * @return name
	 */
	public String value();

}
