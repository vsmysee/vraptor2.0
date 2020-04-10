package org.vraptor.plugin;

import org.vraptor.LogicRequest;

/**
 * Creates a test based in a request and
 * 
 * @author Guilherme Silveira
 */
public interface TestCreator {

	void execute(Object component, LogicRequest request, String result);

	/**
	 * Should execute redirection or not.
	 * 
	 * @return false if the creator already did the redirection
	 */
	boolean shouldRedirect();

}
