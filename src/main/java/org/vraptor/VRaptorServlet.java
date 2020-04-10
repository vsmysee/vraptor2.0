package org.vraptor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.vraptor.component.ComponentNotFoundException;
import org.vraptor.component.LogicNotFoundException;
import org.vraptor.config.ConfigException;
import org.vraptor.core.ControllerFactory;
import org.vraptor.core.VRaptorController;
import org.vraptor.interceptor.InterceptorInstantiationException;
import org.vraptor.url.InvalidURLException;
import org.vraptor.view.ViewException;

/**
 * VRaptor servlet contains a vraptor controller capable of answering web requests.
 * 
 * @author Guilherme Silveira
 */
public class VRaptorServlet extends HttpServlet {

	private static final Logger logger = Logger.getLogger(VRaptorServlet.class);

	/**
	 * serialversion uid
	 */
	private static final long serialVersionUID = 1084551538406918486L;

	private VRaptorController controller;

	/**
	 * Initializes the servlet
	 */
	@Override
	public void init() throws ServletException {
		super.init();
		try {
			this.controller = ControllerFactory.configure(getServletContext());
		} catch (ConfigException e) {
			throw new ServletException("Unable to configure vraptor2", e);
		}
	}

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		logger.debug("new request");
		try {
			controller.execute(request, response);
		} catch (LogicNotFoundException exception) {
			logger.error("Logic not found", exception);
			throw new ServletException("Logic not found", exception);
		} catch (InvalidURLException ex) {
			throw new ServletException("Unable to deal with selected url", ex);
		} catch (ComponentNotFoundException e) {
			logger.error("Component not found", e);
			throw new ServletException(e.getMessage(), e);
		} catch (InterceptorInstantiationException e) {
			logger.error("Unable to instantiate interceptors", e);
			throw new ServletException(e.getMessage(), e);
		} catch (ViewException ex) {
			logger.error("View exception", ex);
			if (ex.getCause() != null) {
				throw new ServletException(ex.getCause().getMessage(), ex
						.getCause());
			} else {
				throw new ServletException(ex.getMessage(), ex);
			}
		} catch (LogicException ex) {
			logger.error("Logic exception", ex);
			if (ex.getCause() != null) {
				throw new ServletException(ex.getCause().getMessage(), ex
						.getCause());
			} else {
				throw new ServletException(ex.getMessage(), ex);
			}
		}

	}
}
