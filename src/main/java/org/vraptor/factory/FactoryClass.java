package org.vraptor.factory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.vraptor.LogicRequest;
import org.vraptor.annotations.In;
import org.vraptor.component.ComponentInstantiationException;
import org.vraptor.component.FieldAnnotation;
import org.vraptor.component.InvalidComponentException;
import org.vraptor.introspector.Introspector;
import org.vraptor.util.MethodInvocationException;
import org.vraptor.util.ReflectionUtil;
import org.vraptor.util.SettingException;

/**
 * Reads a factory class and sets its information
 * 
 * @author Guilherme Silveira
 */
public class FactoryClass {

	private static final Logger logger = Logger.getLogger(FactoryClass.class);

	private Class<?> clazz;

	/**
	 * Factories contained in this class
	 */
	private Map<String, FactoryMethod> factories = new HashMap<String, FactoryMethod>();

	/**
	 * In annotations for this field
	 */
	private List<FieldAnnotation<In>> inAnnotations = new ArrayList<FieldAnnotation<In>>();

	/**
	 * Creates this factory class based on the class itself
	 * 
	 * @param clazz
	 *            the class
	 * @throws InvalidComponentException
	 *             invalid factory
	 */
	@SuppressWarnings("unchecked")
	public FactoryClass(Class clazz) throws InvalidFactoryException {

		this.clazz = clazz;

		logger.info("Reading factories on  class " + clazz);

		for (Method method : clazz.getMethods()) {
			if (method.getName().startsWith("get")) {
				String factory = method.getName().substring(3);
				if (factory.length() == 0) {
					throw new InvalidFactoryException(
							"Factory name cannot be empty!");
				}
				if (factory.length() == 1) {
					factory = factory.toLowerCase();
				} else {
					factory = factory.substring(0, 1).toLowerCase()
							+ factory.substring(1);
				}
				logger.info("Reading factory for " + factory);
				this.factories.put(factory, new FactoryMethod(factory, method));
				logger.debug("Factory " + factory + " read");
			}
		}

		// read fields
		this.inAnnotations = ReflectionUtil.readAnnotations(this.clazz,
				In.class);

	}

	/**
	 * Instantiates this factory
	 * 
	 * @return the factory
	 * @throws ComponentInstantiationException
	 *             unable to instantiate the factory
	 * @throws SettingException
	 *             unable to
	 */
	public Object newInstance(Introspector introspector, LogicRequest context)
			throws FactoryException {
		try {
			Object instance = ReflectionUtil.instantiate(this.clazz);
			introspector.inject(this.inAnnotations, instance, context);
			return instance;
		} catch (ComponentInstantiationException e) {
			logger.error("Unable to use factory " + this.clazz.getName(), e);
			throw new FactoryException(e.getMessage(), e);
		} catch (SettingException e) {
			logger.error("Unable to use factory " + this.clazz.getName(), e);
			throw new FactoryException(e.getMessage(), e);
		}
	}

	/**
	 * Returs the keys this factory class is capable of dealing
	 * 
	 * @return the keys
	 */
	public Set<String> getKeys() {
		return this.factories.keySet();
	}

	/**
	 * Instantiates
	 * 
	 * @param key
	 *            the key
	 * @param component
	 *            the component
	 * @return the result
	 * @throws InvocationTargetException
	 *             invocation problem
	 * @throws LogicExecutionException
	 *             execution problem
	 */
	public Object executeFactory(String key, Object component)
			throws MethodInvocationException {
		return this.factories.get(key).execute(component);
	}

}
