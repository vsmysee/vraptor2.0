package org.vraptor.plugin;

import org.vraptor.LogicException;
import org.vraptor.LogicRequest;
import org.vraptor.component.LogicMethod;

/**
 * A test based proxy implementation.
 */
class ProxyTestLogicMethod implements LogicMethod {

	private LogicMethod logic;

	private TestCreator creator;

	public ProxyTestLogicMethod(LogicMethod logic, TestCreator creator) {
		this.logic = logic;
		this.creator = creator;
	}

	public String execute(Object component, LogicRequest request)
			throws LogicException {
		String result = logic.execute(component, request);
		if (result == null) {
			result = "ok";
		}
		creator.execute(component, request, result);
		return result;
	}

	public String getName() {
		return logic.getName();
	}

	public boolean shouldRedirect() {
		return this.creator.shouldRedirect() && logic.shouldRedirect();
	}

}
