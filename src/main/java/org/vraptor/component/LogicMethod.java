package org.vraptor.component;

import org.vraptor.LogicException;
import org.vraptor.LogicRequest;

/**
 * A business logic method..
 * 
 * @author Guilherme Silveira
 */
public interface LogicMethod {

	/**
	 * Returns the logic's name
	 * 
	 * @return its name
	 */
	String getName();

	/**
	 * Executes the logic by invoking the method
	 * 
	 * @param component
	 *            the component to invoke the method on
	 * @param context
	 * @return the method's result
	 * @throws LogicException
	 *             something wrong happenned
	 */
	String execute(Object component, LogicRequest context)
			throws LogicException;

	/**
	 * Should execute a redirect after business logic execution.
	 * 
	 * @return true if redirection should be done
	 */
	boolean shouldRedirect();

}