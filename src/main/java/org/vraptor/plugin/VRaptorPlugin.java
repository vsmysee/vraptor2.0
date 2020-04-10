package org.vraptor.plugin;

import org.vraptor.webapp.DefaultWebApplication;

/**
 * A vraptor plugin.
 * 
 * @author Guilherme Silveira
 * 
 */
public interface VRaptorPlugin {

	/**
	 * Called after vraptor.xml has been completely parsed to give a chance for
	 * this plugin to do something in the web application
	 * 
	 * @param application
	 *            the web application
	 */
	void init(DefaultWebApplication application);

}
