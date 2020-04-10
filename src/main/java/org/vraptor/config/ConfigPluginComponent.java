package org.vraptor.config;

import org.vraptor.component.ComponentInstantiationException;
import org.vraptor.plugin.VRaptorPlugin;
import org.vraptor.util.ReflectionUtil;
import org.vraptor.webapp.DefaultWebApplication;

/**
 * Models the xml portion to deal with factory classes
 * 
 * @author Guilherme Silveira
 */
public class ConfigPluginComponent implements ConfigItem {

	private Class<?> pluginClazz;

	public <T> ConfigPluginComponent(Class<T> clazz) {
		super();
		this.pluginClazz = clazz;
	}

	public Class<?> getPluginClass() {
		return pluginClazz;
	}

	/**
	 * Registers itself
	 * 
	 * @see org.vraptor.config.ConfigItem#register(org.vraptor.webapp.DefaultWebApplication)
	 */
	@SuppressWarnings("unchecked")
	public void register(DefaultWebApplication application)
			throws ConfigException {
		try {
			application.getPluginManager().register(
					(VRaptorPlugin) (ReflectionUtil
							.instantiate(this.pluginClazz)));
		} catch (ComponentInstantiationException e) {
			throw new ConfigException(e.getMessage(), e);
		} catch (ClassCastException e) {
			throw new ConfigException(
					"Did you forget to implement the plugin interface?", e);
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
