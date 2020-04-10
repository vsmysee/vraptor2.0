package org.vraptor.core;

import javax.servlet.ServletContext;

import org.vraptor.config.ConfigException;
import org.vraptor.webapp.DefaultWebApplication;

/**
 * VRaptor's controller factory
 * 
 * @author Guilherme Silveira
 * 
 */
public class ControllerFactory {

	/**
	 * VRaptor's controller factory.
	 * 
	 * @param servletContext
	 *            the servlet context
	 * @return the new vraptor controller
	 * @throws ConfigException
	 *             configuration exception
	 */
	public static VRaptorController configure(ServletContext servletContext)
			throws ConfigException {
		DefaultWebApplication webApplication = new DefaultWebApplication();
		webApplication.init();
		VRaptorController controller = new VRaptorController(webApplication,
				servletContext);
		controller.getServletContext().setAttribute("vraptorApplication",
				webApplication);
		webApplication.getPluginManager().init(webApplication);
		return controller;
	}

}
