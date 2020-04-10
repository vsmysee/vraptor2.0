package org.vraptor.webapp;

import org.vraptor.component.ComponentManager;
import org.vraptor.config.ConfigException;
import org.vraptor.converter.ConverterManager;
import org.vraptor.factory.FactoryManager;
import org.vraptor.url.URLManager;
import org.vraptor.view.ViewManager;

/**
 * A web application
 * 
 * @author Guilherme Silveira
 */
public interface WebApplication {

	/**
	 * Returns the url manager
	 * 
	 * @return the url manager
	 */
	public URLManager getURLManager();

	/**
	 * Initializes the web application
	 * 
	 * @throws ConfigException
	 * 
	 */
	public void init() throws ConfigException;

	/**
	 * Returns the factory manager
	 * 
	 * @return the factory manager
	 */
	public FactoryManager getFactoryManager();

	/**
	 * Returns the component manager
	 * 
	 * @return the component manager
	 */
	public ComponentManager getComponentManager();

	/**
	 * Returns the view manager
	 * 
	 * @return the view manager
	 */
	public ViewManager getViewManager();

	/**
	 * Returns the converter manager
	 * 
	 * @return the converter manager
	 */
	public ConverterManager getConverterManager();

	/**
	 * Returns the plugin manager
	 * 
	 * @return the plugin manager
	 */
	public PluginManager getPluginManager();

}
