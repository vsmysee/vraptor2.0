package org.vraptor.scope;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.vraptor.url.InternalLogicRequest;

/**
 * Basic vraptor logic request.
 * 
 * @author Guilherme Silveira
 */
public class DefaultLogicRequest implements InternalLogicRequest {

	private static final Logger logger = Logger
			.getLogger(DefaultLogicRequest.class);

	private String logicName;

	private String componentName;

	private HttpServletRequest request;

	private Context appContext;

	private SessionContext sessionContext;

	private RequestContext requestContext;

	private LogicContext logicContext;

	private ServletContext application;

	private HttpServletResponse response;

	/**
	 * Creates the logic context
	 * 
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @param application
	 *            application scope
	 */
	public DefaultLogicRequest(String componentName, String logicName,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext application) {
		logger.debug("logic request created: " + componentName + " "
				+ logicName);
		this.componentName = componentName;
		this.logicName = logicName;
		this.response = response;
		this.request = request;
		this.application = application;
		this.logicContext = new DefaultLogicContext(request, response);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vraptor.scope.LogicRequest#getApplicationContext()
	 */
	public Context getApplicationContext() {
		if (this.appContext == null) {
			this.appContext = new Context() {
				public boolean hasAttribute(String name) {
					return application.getAttribute(name) != null;
				}

				public void setAttribute(String name, Object value) {
					application.setAttribute(name, value);
				}

				public Object getAttribute(String name) {
					return application.getAttribute(name);
				}

				public Object removeAttribute(String name) {
					Object value = application.getAttribute(name);
					application.removeAttribute(name);
					return value;
				}
			};
		}
		return this.appContext;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vraptor.scope.LogicRequest#getSessionContext()
	 */
	public SessionContext getSessionContext() {
		if (this.sessionContext == null) {
			this.sessionContext = new SessionContext(this.request.getSession());
		}
		return this.sessionContext;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vraptor.scope.LogicRequest#getRequestContext()
	 */
	public RequestContext getRequestContext() {
		if (this.requestContext == null) {
			this.requestContext = new DefaultRequestContext(this.request);
		}
		return this.requestContext;
	}

	/**
	 * @see org.vraptor.LogicRequest#getComponentName()
	 */
	public String getComponentName() {
		return this.componentName;
	}

	/**
	 * @see org.vraptor.LogicRequest#getLogicName()
	 */
	public String getLogicName() {
		return this.logicName;
	}

	public HttpServletRequest getRequest() {
		return this.request;
	}

	public HttpServletResponse getResponse() {
		return this.response;
	}

	public LogicContext getLogicContext() {
		return this.logicContext;
	}

}
