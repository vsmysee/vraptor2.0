package org.vraptor.component;

import java.util.Set;

import org.vraptor.LogicRequest;

/**
 * Component managers are capable of finding and dealing with business
 * components/logics.
 * 
 * @author Guilherme Silveira
 */
public interface ComponentManager {

	/**
	 * Registers a new component
	 * 
	 * @param clazz
	 *            component class
	 * @throws InvalidComponentException
	 *             invalid component
	 */
	public void register(Class clazz) throws InvalidComponentException;

	/**
	 * Returns the business component
	 * 
	 * @param logicRequest
	 *            business name
	 * @throws InvalidComponentException
	 *             if there is no component with this name
	 */
	public ComponentType getComponent(LogicRequest logicRequest)
			throws ComponentNotFoundException;

	/**
	 * Returns a list with all component classes.
	 * 
	 * @return all components registered
	 */
	public Set<ComponentType> getComponents();

	/**
	 * Directly registers a component type.
	 * 
	 * @param type
	 *            the component type
	 */
	public void register(ComponentType type);

}
