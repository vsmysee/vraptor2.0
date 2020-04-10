package org.vraptor.util;

import org.vraptor.VRaptorException;

/**
 * Method invocation problem
 * 
 * @author Guilherme Silveira
 */
public class MethodInvocationException extends VRaptorException {

	private static final long serialVersionUID = -5620431708629544816L;

	public MethodInvocationException() {
		super();
	}

	public MethodInvocationException(String message, Throwable cause) {
		super(message, cause);
	}

	public MethodInvocationException(String message) {
		super(message);
	}

	public MethodInvocationException(Throwable cause) {
		super(cause);
	}

}
