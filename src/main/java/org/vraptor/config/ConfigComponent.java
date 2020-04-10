package org.vraptor.config;

import org.vraptor.component.InvalidComponentException;
import org.vraptor.webapp.DefaultWebApplication;

/**
 * Models the xml portion to deal with component classes
 * 
 * @author Guilherme Silveira
 */
public class ConfigComponent implements ConfigItem {

	private Class<?> componentClazz;

	public <T> ConfigComponent(Class<T> clazz) {
		super();
		this.componentClazz = clazz;
	}

	public Class<?> getFactoryClass() {
		return componentClazz;
	}

	/**
	 * Registers itself
	 * 
	 * @see org.vraptor.config.ConfigItem#register(org.vraptor.webapp.DefaultWebApplication)
	 */
	public void register(DefaultWebApplication application)
			throws ConfigException {
		try {
			application.getComponentManager().register(
					this.componentClazz);
		} catch (InvalidComponentException e) {
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
