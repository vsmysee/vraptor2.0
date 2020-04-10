package org.vraptor.component;

import java.util.Collection;
import java.util.List;

import org.vraptor.annotations.In;
import org.vraptor.annotations.Out;
import org.vraptor.interceptor.InterceptorClass;
import org.vraptor.introspector.FieldReadParameter;

public interface ComponentType {

	/**
	 * Instantiates this component.
	 * 
	 * @return the new component
	 * @throws ComponentInstantiationException
	 *             something wrong happened during instantiation
	 */
	Object newInstance() throws ComponentInstantiationException;

	/**
	 * Returns the component name
	 * 
	 * @return the name
	 */
	String getName();

	/**
	 * Returns a specifig logic from this component
	 * 
	 * @param key
	 *            the logic name
	 * @return the logic itself
	 * @throws LogicNotFoundException
	 *             you are asking for something that doesn't belong to me
	 */
	LogicMethod getLogic(String key)
			throws LogicNotFoundException;

	/**
	 * Returns all interceptors for this component class
	 * 
	 * @return the interceptor's list
	 */
	List<InterceptorClass> getInterceptors();

	/**
	 * @return Returns the inAnnotations.
	 */
	List<FieldAnnotation<In>> getInAnnotations();

	/**
	 * @return Returns the outAnnotations.
	 */
	List<FieldAnnotation<Out>> getOutAnnotations();

	/**
	 * @return Returns the read parameters for this class.
	 */
	List<FieldReadParameter> getReadParameters();

	/**
	 * Returns all actions.
	 * 
	 * @return all logics
	 */
	Collection<LogicMethod> getLogics();
}