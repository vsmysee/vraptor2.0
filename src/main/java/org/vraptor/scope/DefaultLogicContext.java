package org.vraptor.scope;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DefaultLogicContext implements LogicContext {

	private Map<String, Object> attributes = new HashMap<String, Object>();

	public DefaultLogicContext(HttpServletRequest request,
			HttpServletResponse response) {
		this.setAttribute("request", request);
		this.setAttribute("response", response);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vraptor.scope.LogicRequest#setAttribute(java.lang.String,
	 *      java.lang.Object)
	 */
	public void setAttribute(String name, Object value) {
		this.attributes.put(name, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vraptor.scope.LogicRequest#getAttribute(java.lang.String)
	 */
	public Object getAttribute(String name) {
		return this.attributes.get(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vraptor.scope.LogicRequest#removeAttribute(java.lang.String)
	 */
	public Object removeAttribute(String name) {
		return this.attributes.remove(name);
	}

	public boolean hasAttribute(String name) {
		return this.hasAttribute(name);
	}

}
