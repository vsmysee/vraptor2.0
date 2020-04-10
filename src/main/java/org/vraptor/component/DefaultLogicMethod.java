package org.vraptor.component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.vraptor.LogicException;
import org.vraptor.LogicRequest;
import org.vraptor.annotations.Read;
import org.vraptor.annotations.Viewless;
import org.vraptor.util.JavaMethod;
import org.vraptor.util.MethodInvocationException;
import org.vraptor.util.ReflectionUtil;
import org.vraptor.validator.BasicValidationErrors;
import org.vraptor.validator.UnstableValidationException;
import org.vraptor.validator.ValidationErrors;

/**
 * Represents a logic method.
 * 
 * @author Guilherme Silveira
 */
public class DefaultLogicMethod implements LogicMethod {

	private static final Logger logger = Logger
			.getLogger(DefaultLogicMethod.class);

	private String name;

	private JavaMethod method;

	private boolean shouldRedirect;

	private JavaMethod validateMethod;

	/**
	 * Constructs it based on the logics name and method
	 * 
	 * @param method
	 *            method
	 */
	public DefaultLogicMethod(String name, Method method, Method validateMethod) {
		this.name = name;
		this.method = new JavaMethod(method);
		this.shouldRedirect = !this.method.containsAnnotation(Viewless.class);
		// reads all method parameters
		Annotation[][] annotations = method.getParameterAnnotations();
		Class<?>[] parameters = method.getParameterTypes();
		for (int i = 0; i < parameters.length; i++) {
			Class<?> parameter = parameters[i];
			// for each parameter, creates a ParameterRead
			Read annotation = ReflectionUtil.findAnnotation(annotations[i],
					Read.class);
			// ParameterReadParameter read = new ParameterReadParameter();
		}
		if (validateMethod != null) {
			this.validateMethod = new JavaMethod(validateMethod);
		}
		logger.debug("Logic method " + method.getName()
				+ " was read with validation " + this.validateMethod);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vraptor.component.LogicMethod#getName()
	 */
	public String getName() {
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vraptor.component.LogicMethod#execute(java.lang.Object)
	 */
	public String execute(Object component, LogicRequest context)
			throws LogicException {
		if (validateMethod != null) {
			ValidationErrors errors = new BasicValidationErrors();
			try {
				validateMethod.invoke(component, errors);
			} catch (MethodInvocationException e) {
				logger.error(
						"Nasty validation method has thrown an exception!", e);
				throw new UnstableValidationException(
						"Error during validation process", e);
			}
			if (errors.size() != 0) {
				context.getRequestContext().setAttribute("errors", errors);
				return "invalid";
			}
		}
		Object result;
		try {
			result = method.invoke(component);
		} catch (MethodInvocationException e) {
			logger.error("Unable to execute the action " + this.name, e
					.getCause());
			throw new LogicException(e.getCause().getMessage(), e.getCause());
		}
		return result == null ? "ok" : result.toString();
	}

	public boolean shouldRedirect() {
		return this.shouldRedirect;
	}

}
