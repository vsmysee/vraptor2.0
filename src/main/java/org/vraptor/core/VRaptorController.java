package org.vraptor.core;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.vraptor.LogicException;
import org.vraptor.LogicRequest;
import org.vraptor.Result;
import org.vraptor.component.ComponentNotFoundException;
import org.vraptor.component.LogicNotFoundException;
import org.vraptor.http.VRaptorRequest;
import org.vraptor.interceptor.InterceptorInstantiationException;
import org.vraptor.introspector.BasicIntrospector;
import org.vraptor.introspector.Introspector;
import org.vraptor.url.InternalLogicRequest;
import org.vraptor.url.InvalidURLException;
import org.vraptor.view.ViewException;
import org.vraptor.webapp.WebApplication;

/**
 * Main vraptor2 controller.
 * 
 * @author Guilherme Silveira
 */
public class VRaptorController {

	private static final Logger logger = Logger
			.getLogger(VRaptorController.class);

	private Introspector introspector = new BasicIntrospector();

	private WebApplication application;

	private ServletContext servletContext;

	/**
	 * @param application
	 */
	public VRaptorController(WebApplication application, ServletContext context) {
		logger.debug("VRaptor engine controller instantiated");
		this.application = application;
		this.servletContext = context;
	}

	/**
	 * Returns the instrospector
	 * 
	 * @return
	 */
	public Introspector getIntrospector() {
		return this.introspector;
	}

	/**
	 * @return the application
	 */
	public WebApplication getWebApplication() {
		return application;
	}

	/**
	 * @return the context
	 */
	public ServletContext getServletContext() {
		return servletContext;
	}

	public Result execute(HttpServletRequest oldRequest,
			HttpServletResponse response) throws InvalidURLException,
			ComponentNotFoundException, LogicNotFoundException, ViewException,
			InterceptorInstantiationException, LogicException {

		VRaptorRequest request = new VRaptorRequest(oldRequest, this);

		final InternalLogicRequest logicRequest = getWebApplication().getURLManager()
				.getLogicRequest(request, response, getServletContext());
		request.setCurrentRequest(logicRequest);
		request.setAttribute("context", logicRequest);

		final String result = new VRaptorExecution(logicRequest, this)
				.execute();
		return new Result() {

			public String getReturnCode() {
				return result;
			}

			public LogicRequest getLogicRequest() {
				return logicRequest;
			}

		};

	}

}
