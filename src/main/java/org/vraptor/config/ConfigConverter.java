package org.vraptor.config;

import org.vraptor.component.ComponentInstantiationException;
import org.vraptor.converter.Converter;
import org.vraptor.util.ReflectionUtil;
import org.vraptor.webapp.DefaultWebApplication;

/**
 * Models the xml portion to deal with component classes
 * 
 * @author Guilherme Silveira
 */
public class ConfigConverter implements ConfigItem {

	private Class<? extends Converter> converterClazz;

	/**
	 * Creates a configuration converter
	 * 
	 * @param <T>
	 *            the converter type
	 * @param clazz
	 *            the class type
	 */
	@SuppressWarnings("unchecked")
	public <T> ConfigConverter(Class<T> clazz) {
		super();
		this.converterClazz = (Class<? extends Converter>) clazz;
	}

	public Class<?> getConverterClass() {
		return converterClazz;
	}

	/**
	 * Registers itself
	 * 
	 * @throws ConfigException
	 * 
	 * @see org.vraptor.config.ConfigItem#register(org.vraptor.webapp.DefaultWebApplication)
	 */
	public void register(DefaultWebApplication application)
			throws ConfigException {
		try {
			application.getConverterManager().register(
					ReflectionUtil.instantiate(this.converterClazz));
		} catch (ComponentInstantiationException e) {
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
