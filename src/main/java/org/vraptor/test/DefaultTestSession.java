package org.vraptor.test;

import org.vraptor.LogicException;
import org.vraptor.Result;
import org.vraptor.component.ComponentNotFoundException;
import org.vraptor.component.InvalidComponentException;
import org.vraptor.component.LogicNotFoundException;
import org.vraptor.core.VRaptorController;
import org.vraptor.interceptor.InterceptorInstantiationException;
import org.vraptor.url.InvalidURLException;
import org.vraptor.view.ViewException;

/**
 * The default implementation for the test session.
 * 
 * @author Guilherme Silveira
 * 
 */
class DefaultTestSession implements TestSession {

	private final VRaptorController controller;

	private final MockedHttpSession session;

	/**
	 * Intantiates this session test.
	 * 
	 * @param controller
	 */
	DefaultTestSession(VRaptorController controller) {
		this.controller = controller;
		this.session = new MockedHttpSession();
	}

	public Result execute(String relativeUrl, String... parameters) throws InvalidComponentException,
			InvalidURLException, ComponentNotFoundException,
			LogicNotFoundException, ViewException,
			InterceptorInstantiationException, LogicException {
		MockedRequest request = new MockedRequest(relativeUrl, session, parameters);
		MockedResponse response = new MockedResponse();
		return controller.execute(request, response);
	}

}
