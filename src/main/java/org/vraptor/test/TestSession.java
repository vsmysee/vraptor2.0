package org.vraptor.test;

import org.vraptor.LogicException;
import org.vraptor.Result;
import org.vraptor.component.ComponentNotFoundException;
import org.vraptor.component.InvalidComponentException;
import org.vraptor.component.LogicNotFoundException;
import org.vraptor.interceptor.InterceptorInstantiationException;
import org.vraptor.url.InvalidURLException;
import org.vraptor.view.ViewException;

/**
 * A test context emulates an user session in
 * 
 * @author Guilherme Silveira
 * 
 */
public interface TestSession {

	/**
	 * Executes a url call based on some url. The parameters are strings like
	 * "a=b", "c=d".
	 * 
	 * @param relativeUrl
	 *            relative url to server
	 * @param parameters
	 *            the parameter list to be passed
	 * @return the logic result
	 */
	public Result execute(String relativeUrl, String... parameters)throws InvalidComponentException,
		InvalidURLException, ComponentNotFoundException,
		LogicNotFoundException, ViewException,
		InterceptorInstantiationException, LogicException;

}
