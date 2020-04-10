package org.vraptor.core;

import org.apache.log4j.Logger;
import org.vraptor.LogicException;
import org.vraptor.component.ComponentNotFoundException;
import org.vraptor.component.ComponentType;
import org.vraptor.component.LogicMethod;
import org.vraptor.component.LogicNotFoundException;
import org.vraptor.interceptor.InterceptorInstantiationException;
import org.vraptor.url.InternalLogicRequest;
import org.vraptor.view.ViewException;

/**
 * VRaptor's engine.
 * 
 * @author Guilherme Silveira
 */
public class VRaptorExecution {

	private static final Logger logger = Logger
			.getLogger(VRaptorExecution.class);

	private VRaptorController controller;

	private InternalLogicRequest request;

	private String result;

	public VRaptorExecution(InternalLogicRequest logicRequest,
			VRaptorController controller) {
		this.controller = controller;
		this.request = logicRequest;
	}

	public String execute() throws ComponentNotFoundException,
			LogicNotFoundException, ViewException,
			InterceptorInstantiationException, LogicException {

		logger.debug("Calling execute on " + request.toString());

		ComponentType componentClass = this.controller.getWebApplication()
				.getComponentManager().getComponent(request);

		// gets the logic method
		LogicMethod logicMethod = componentClass.getLogic(request
				.getLogicName());

		// gets the flow
		ExecuteLogicInterceptor interceptor = new ExecuteLogicInterceptor(this,
				componentClass, logicMethod);
		BasicLogicFlow logicFlow = new BasicLogicFlow(componentClass, this,
				interceptor);

		// executes it
		logicFlow.execute();

		// returns its result, if any
		return this.result;

	}

	public void redirect(String result) throws ViewException {

		// forward
		logger.debug("Logic returned redirect to: " + result);

		this.controller.getWebApplication().getViewManager().forward(request,
				result, request.getRequest(), request.getResponse());

		// everything went fine...
		this.result = result;

	}

	/**
	 * Returns its request object
	 * 
	 * @return the request
	 */
	InternalLogicRequest getRequest() {
		return request;
	}

	/**
	 * Returns vraptor's controller
	 * 
	 * @return the controller
	 */
	VRaptorController getController() {
		return controller;
	}

}
