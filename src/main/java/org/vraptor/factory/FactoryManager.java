package org.vraptor.factory;

import org.vraptor.LogicRequest;
import org.vraptor.component.InvalidComponentException;
import org.vraptor.introspector.Introspector;

/**
 * Responsible for dealing with factories
 * 
 * @author Guilherme Silveira
 */
public interface FactoryManager {

	/**
	 * Instatiates an object based on a key by first instantiating the factory,
	 * injecting what is needed and then calling the create method
	 * 
	 * @param key
	 *            factory key
	 * @param context
	 *            logic context
	 * @return the new object
	 * @exception FactoryException
	 *                if unable to instantiate such key (key does not exist or
	 *                some internal exception)
	 */
	public Object instantiate(String key, LogicRequest context,
			Introspector introspector) throws FactoryException;

	/**
	 * Whether this factory can deal with this key
	 * 
	 * @param key
	 *            key
	 * @return true or false
	 */
	public boolean canInstantiate(String key);

	/**
	 * Registers a factory
	 * 
	 * @param clazz
	 *            the class
	 * @throws InvalidComponentException
	 *             invalid factory if some of it's methods are invalid
	 */
	public void register(Class clazz) throws InvalidFactoryException;

}
