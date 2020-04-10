package org.vraptor.factory;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.vraptor.LogicRequest;
import org.vraptor.introspector.Introspector;
import org.vraptor.util.MethodInvocationException;

/**
 * Deals with factories in order to instantiate objects
 * 
 * @author Guilherme Silveira
 */
public class SimpleFactoryManager implements FactoryManager {

	private static final Logger logger = Logger
			.getLogger(SimpleFactoryManager.class);

	private Map<String, FactoryClass> factories = new HashMap<String, FactoryClass>();

	/**
	 * 
	 * @see org.vraptor.factory.FactoryManager#instantiate(java.lang.String,
	 *      org.vraptor.scope.LogicRequest)
	 */
	public Object instantiate(String key, LogicRequest context,
			Introspector introspector) throws FactoryException {
		logger.debug("Will try to use factory for key " + key);
		FactoryClass clazz = getFactory(key);
		if (clazz == null) {
			throw new FactoryException("No factory found for key " + key);
		}
		try {
			Object instance = clazz.newInstance(introspector, context);
			return clazz.executeFactory(key, instance);
		} catch (MethodInvocationException e) {
			logger.error("Unable to create " + key, e);
			throw new FactoryException("Unable to create " + key, e);
		}
	}

	/**
	 * 
	 * @see org.vraptor.factory.FactoryManager#canInstantiate(java.lang.String)
	 */
	public boolean canInstantiate(String key) {
		return this.factories.containsKey(key);
	}

	/**
	 * Registers a factory
	 * 
	 * @see org.vraptor.factory.FactoryManager#register(java.lang.Class)
	 */
	public void register(Class clazz) throws InvalidFactoryException {
		FactoryClass factory = new FactoryClass(clazz);
		for (String key : factory.getKeys()) {
			this.factories.put(key, factory);
		}
	}

	/**
	 * Returns the specified factory
	 * 
	 * @param key
	 *            the key
	 * @return the factory
	 */
	private FactoryClass getFactory(String key) {
		return this.factories.get(key);
	}

}
