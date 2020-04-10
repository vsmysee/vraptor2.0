package org.vraptor.introspector;

import java.util.List;

import org.vraptor.LogicRequest;
import org.vraptor.annotations.In;
import org.vraptor.annotations.Out;
import org.vraptor.component.ComponentInstantiationException;
import org.vraptor.component.ComponentType;
import org.vraptor.component.FieldAnnotation;
import org.vraptor.converter.ConverterManager;
import org.vraptor.i18n.Message;
import org.vraptor.util.GettingException;
import org.vraptor.util.SettingException;

/**
 * The instrospector is responsible for doing injection/outjection and pushing
 * parameters in the business logic.
 * 
 * @author Guilherme Silveira
 */
public interface Introspector {

	/**
	 * Reads all parameters from the request and tries to fill the read
	 * annotation of a class.
	 * 
	 */
	public List<Message> readParameters(ComponentType clazz, Object component,
			LogicRequest logicRequest, ConverterManager converterManager)
			throws SettingException;

	/**
	 * Call for injection: tries to inject each field listed on the
	 * inAnnotations on object component using the logic context passed as
	 * argument
	 * 
	 * @param inAnnotations
	 *            annotations
	 * @param component
	 *            current object
	 * @param context
	 *            logic context
	 * @throws ComponentInstantiationException
	 *             unable to instantiate some component for the injection
	 * @throws SettingException
	 *             unable to set some field for the injection
	 */
	public abstract void inject(List<FieldAnnotation<In>> inAnnotations,
			Object component, LogicRequest context)
			throws ComponentInstantiationException, SettingException;

	/**
	 * Oujection
	 * 
	 * @param outAnnotations
	 * @param component
	 * @param context
	 * @throws GettingException
	 */
	public abstract void outject(List<FieldAnnotation<Out>> outAnnotations,
			Object component, LogicRequest context) throws GettingException;

}