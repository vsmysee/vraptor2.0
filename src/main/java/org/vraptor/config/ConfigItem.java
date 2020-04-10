package org.vraptor.config;

import org.vraptor.webapp.DefaultWebApplication;

/**
 * A configuration item
 * 
 * @author Guilherme Silveira
 */
public interface ConfigItem {

	/**
	 * Registers itself in the web application
	 * 
	 * @param application
	 *            the application
	 * @throws ConfigException
	 *             configuration exception
	 */
	public void register(DefaultWebApplication application)
			throws ConfigException;

	/**
	 * Returns true if this is a component, factory or converter
	 * 
	 * @return true or false
	 */
	public boolean isComponent();

	/**
	 * Returns true if this is a manager configuration
	 * 
	 * @return true or false
	 */
	public boolean isManager();

}
