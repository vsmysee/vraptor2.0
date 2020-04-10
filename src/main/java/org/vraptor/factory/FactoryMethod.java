package org.vraptor.factory;

import java.lang.reflect.Method;

import org.vraptor.util.JavaMethod;
import org.vraptor.util.MethodInvocationException;

/**
 * A factory method
 * 
 * @author Guilherme Silveira
 */
public class FactoryMethod {

	private String key;

	private JavaMethod method;

	/**
	 * Simple constructor with annotation and method
	 * 
	 * @param annotation
	 *            the annotation
	 * @param method
	 *            the method
	 */
	public FactoryMethod(String key, Method method) {
		super();
		this.key = key;
		this.method = new JavaMethod(method);
	}

	/**
	 * Returns the new instance
	 * 
	 * @return the new instance
	 * @throws MethodInvocationException
	 *             in case something wrong happenned
	 */
	public Object execute(Object component) throws MethodInvocationException {
		return this.method.invoke(component);
	}

	/**
	 * @return Returns the key.
	 */
	public String getKey() {
		return this.key;
	}

}
