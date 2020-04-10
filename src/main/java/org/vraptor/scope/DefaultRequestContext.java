package org.vraptor.scope;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * A simple request context.
 *
 * @author Guilherme Silveira
 */
public class DefaultRequestContext implements Context, RequestContext {

	private HttpServletRequest request;

	private Map<String, Object> parameters;

	@SuppressWarnings("unchecked")
	public DefaultRequestContext(HttpServletRequest request) {
		this.request = request;
		this.parameters = Collections
				.unmodifiableMap(request.getParameterMap());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vraptor.scope.RequestContext#hasAttribute(java.lang.String)
	 */
	public boolean hasAttribute(String name) {
		return request.getAttribute(name) != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vraptor.scope.RequestContext#setAttribute(java.lang.String,
	 *      java.lang.Object)
	 */
	public void setAttribute(String name, Object value) {
		request.setAttribute(name, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vraptor.scope.RequestContext#getAttribute(java.lang.String)
	 */
	public Object getAttribute(String name) {
		return request.getAttribute(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vraptor.scope.RequestContext#removeAttribute(java.lang.String)
	 */
	public Object removeAttribute(String name) {
		Object value = request.getAttribute(name);
		request.removeAttribute(name);
		return value;
	}

	public Map<String, Object> getParameterMap() {
		return this.parameters;
	}

}
