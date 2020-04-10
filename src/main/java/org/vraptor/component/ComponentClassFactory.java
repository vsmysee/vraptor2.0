package org.vraptor.component;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.vraptor.annotations.Component;
import org.vraptor.annotations.In;
import org.vraptor.annotations.Logic;
import org.vraptor.annotations.Out;
import org.vraptor.annotations.Read;
import org.vraptor.interceptor.InterceptorClass;
import org.vraptor.interceptor.InterceptorListFactory;
import org.vraptor.util.MethodInvocationException;
import org.vraptor.util.ReflectionUtil;
import org.vraptor.validator.ValidationErrors;

/**
 * A component class factory
 * 
 * @author Guilherme Silveira
 */
public class ComponentClassFactory {

	private static final Logger logger = Logger
			.getLogger(ComponentClassFactory.class);

	public static final InterceptorListFactory INTERCEPTOR_LIST_FACTORY = new InterceptorListFactory();

	/**
	 * Creates a component class dealer
	 * 
	 * @param clazz
	 *            the class to load
	 * @return
	 * @throws InvalidComponentException
	 */
	public ComponentType getComponentClass(Class<?> clazz)
			throws InvalidComponentException {

		logger.debug("Reading component clazz " + clazz.getName());

		String componentName = clazz.getSimpleName();

		if (clazz.isAnnotationPresent(Component.class)) {
			componentName = ((Component) clazz.getAnnotation(Component.class))
					.value();
		}

		logger.info("Component found: " + componentName);

		// read logics
		Map<String, DefaultLogicMethod> actions = new HashMap<String, DefaultLogicMethod>();
		for (Method m : clazz.getMethods()) {
			if (!Modifier.isPublic(m.getModifiers())
					|| m.getDeclaringClass().equals(Object.class)
					|| m.getName().startsWith("validate")) {
				continue;
			}
			Logic annotation = m.getAnnotation(Logic.class);
			generateLogics(actions, m, annotation, clazz);
		}

		List<InterceptorClass> interceptors = INTERCEPTOR_LIST_FACTORY
				.getInterceptors(clazz);

		// read fields
		List<FieldAnnotation<In>> inAnnotations = ReflectionUtil
				.readAnnotations(clazz, In.class);
		List<FieldAnnotation<Out>> outAnnotations = ReflectionUtil
				.readAnnotations(clazz, Out.class);
		List<FieldAnnotation<Read>> readAnnotations = ReflectionUtil
				.readAnnotations(clazz, Read.class);

		logger.debug("Component clazz " + clazz.getName() + " read");

		return new DefaultComponentType(clazz, componentName, actions,
				inAnnotations, outAnnotations, readAnnotations, interceptors);

	}

	/**
	 * Generate logics for an specific method and registers them
	 * 
	 * @param actions
	 *            actions
	 * @param m
	 *            method
	 * @param annotation
	 *            logic annotation
	 * @param componentClass
	 * @throws InvalidComponentException
	 */
	private void generateLogics(Map<String, DefaultLogicMethod> actions,
			Method m, Logic annotation, Class<?> componentClass)
			throws InvalidComponentException {
		try {
			if (annotation == null) {
				register(actions, new DefaultLogicMethod(m.getName(), m,
						ReflectionUtil
								.getPrefixedMethod(componentClass, "validate",
										m.getName(), ValidationErrors.class)));
				return;
			}
			for (String name : annotation.value()) {
				register(actions, new DefaultLogicMethod(name, m,
						ReflectionUtil.getPrefixedMethod(componentClass,
								"validate", name, ValidationErrors.class)));
			}
		} catch (MethodInvocationException e) {
			throw new InvalidComponentException("Unable to load some methods",
					e);
		}
	}

	/**
	 * @param actions
	 * @param action
	 */
	private void register(Map<String, DefaultLogicMethod> actions,
			DefaultLogicMethod action) {
		logger.info("Registering logic " + action.getName());
		actions.put(action.getName(), action);
	}

}
