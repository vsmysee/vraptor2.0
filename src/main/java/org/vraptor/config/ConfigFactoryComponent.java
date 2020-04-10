package org.vraptor.config;

import org.vraptor.factory.InvalidFactoryException;
import org.vraptor.webapp.DefaultWebApplication;

/**
 * Models the xml portion to deal with factory classes
 * 
 * @author Guilherme Silveira
 */
public class ConfigFactoryComponent implements ConfigItem {

	private Class<?> factoryClazz;

	public <T> ConfigFactoryComponent(Class<T> clazz) {
		super();
		this.factoryClazz = clazz;
	}

	public Class<?> getFactoryClass() {
		return factoryClazz;
	}

	/**
	 * Registers itself
	 * 
	 * @see org.vraptor.config.ConfigItem#register(org.vraptor.webapp.DefaultWebApplication)
	 */
	public void register(DefaultWebApplication application)
			throws ConfigException {
		try {
			application.getFactoryManager().register(this.factoryClazz);
		} catch (InvalidFactoryException e) {
			throw new ConfigException(e.getMessage(), e);
		}
	}

	/**
	 * 
	 * @see org.vraptor.config.ConfigItem#isComponent()
	 */
	public boolean isComponent() {
		return true;
	}

	/**
	 * 
	 * @see org.vraptor.config.ConfigItem#isManager()
	 */
	public boolean isManager() {
		return false;
	}
}
