package org.vraptor.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Wrapper for a java method in order to use reflection.
 * 
 * @author Guilherme Silveira
 */
public class JavaMethod {

	private Method method;

	public JavaMethod(Method method) {
		this.method = method;
	}

	/**
	 * Uses reflection util to invoke its method
	 * 
	 * @param component
	 *            component in which the method should be invoked
	 * @param parameters
	 *            all parameters
	 * @return the method's invocation result
	 * @throws MethodInvocationException
	 */
	public Object invoke(Object component, Object... parameters)
			throws MethodInvocationException {
		return ReflectionUtil.invoke(component, this.method, parameters);
	}

	/**
	 * Returns the method's name
	 * 
	 * @return the name
	 */
	public String getName() {
		return method.getName();
	}

	@Override
	public String toString() {
		return "[JavaMethod " + getName() + "]";
	}

	public <T extends Annotation> boolean containsAnnotation(Class<T> type) {
		return this.method.isAnnotationPresent(type);
	}

}
