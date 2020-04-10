package org.vraptor.webapp;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.vraptor.VRaptorServlet;
import org.vraptor.component.ComponentManager;
import org.vraptor.config.ConfigException;
import org.vraptor.config.XStreamConfiguration;
import org.vraptor.converter.ConverterManager;
import org.vraptor.converter.SimpleConverterManager;
import org.vraptor.factory.FactoryManager;
import org.vraptor.factory.SimpleFactoryManager;
import org.vraptor.plugin.DefaultPluginManager;
import org.vraptor.url.DefaultURLManager;
import org.vraptor.url.URLManager;
import org.vraptor.view.RegexViewManager;
import org.vraptor.view.ViewManager;

/**
 * A simple web application configuration. It uses the default url manager.
 * 
 * @author Guilherme Silveira
 */
public class DefaultWebApplication implements WebApplication {

	private static final Logger logger = Logger
			.getLogger(DefaultWebApplication.class);

	private ConverterManager converterManager;

	private URLManager urlManager;

	private ComponentManager componentManager;

	private FactoryManager factoryManager;

	private ViewManager viewManager;

	private PluginManager pluginManager;

	public DefaultWebApplication() {
		urlManager = new DefaultURLManager();
		componentManager = new DefaultComponentManager();
		factoryManager = new SimpleFactoryManager();
		viewManager = new RegexViewManager("/$1/$2.$3.jsp");
		converterManager = new SimpleConverterManager();
		pluginManager = new DefaultPluginManager();
	}

	/**
	 * 
	 * @see org.vraptor.webapp.WebApplication#getURLManager()
	 */
	public URLManager getURLManager() {
		return urlManager;
	}

	/**
	 * Reads the xml file. Inits this configuration: overrides the viewmanager
	 * with the views.properties file
	 * 
	 * @throws ConfigException
	 * 
	 * @see org.vraptor.webapp.WebApplication#init()
	 */
	public void init() throws ConfigException {
		// reads the xml file
		try {
			InputStream file = getXMLFile();
			new XStreamConfiguration().readManagers(file, this);
			file.close();
			file = getXMLFile();
			new XStreamConfiguration().readComponents(file, this);
			file.close();
		} catch (IOException e) {
			throw new ConfigException(e.getMessage(), e);
		}
		// reads overriden views.properties
		InputStream fis = DefaultWebApplication.class
				.getResourceAsStream("/views.properties");
		if (fis != null) {
			try {
				this.viewManager = new ViewsPropertiesReader().overrideViews(
						viewManager, fis);
			} catch (IOException e) {
				logger.warn("Error reading views.properties", e);
			}
		}
	}

	/**
	 * @return
	 * @throws ServletException
	 * @throws ConfigException
	 */
	private InputStream getXMLFile() throws ConfigException {
		InputStream file = VRaptorServlet.class
				.getResourceAsStream("/vraptor.xml");
		if (file == null) {
			throw new ConfigException(
					"Vraptor was not configured: where is vraptor.xml?");
		}
		return file;
	}

	/**
	 * 
	 * @see org.vraptor.webapp.WebApplication#getFactoryManager()
	 */
	public FactoryManager getFactoryManager() {
		return this.factoryManager;
	}

	/**
	 * 
	 * @see org.vraptor.webapp.WebApplication#getComponentManager()
	 */
	public ComponentManager getComponentManager() {
		return this.componentManager;
	}

	/**
	 * 
	 * @see org.vraptor.webapp.WebApplication#getViewManager()
	 */
	public ViewManager getViewManager() {
		return this.viewManager;
	}

	/**
	 * 
	 * @see org.vraptor.webapp.WebApplication#getConverterManager()
	 */
	public ConverterManager getConverterManager() {
		return this.converterManager;
	}

	/**
	 * @param viewManager
	 *            the viewManager to set
	 */
	public void setViewManager(ViewManager viewManager) {
		this.viewManager = viewManager;
	}

	public PluginManager getPluginManager() {
		return this.pluginManager;
	}

}
