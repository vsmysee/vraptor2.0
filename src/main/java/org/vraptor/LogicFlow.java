package org.vraptor;

import org.vraptor.view.ViewException;

/**
 * Interface for logic flow. Either the method execute or forward should be
 * called.
 * 
 * @author Guilherme Silveira
 */
public interface LogicFlow {

	/**
	 * Executes the next interceptor or logic
	 * 
	 * @throws LogicException
	 *             some logic or interceptor problem
	 * @throws ViewException
	 *             some view problem
	 * 
	 */
	void execute() throws ViewException, LogicException;

	/**
	 * Executes a server side redirect to this url. If a ViewException occurs it
	 * might be dangerous to try a server side forward (the end-user might get
	 * an unexpected combination of both pages).
	 * 
	 * @param component
	 *            target component
	 * @param logic
	 *            target logic
	 * @throws RedirectException
	 */
	void redirect(String url) throws RedirectException;

}
