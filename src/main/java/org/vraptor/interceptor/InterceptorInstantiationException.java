package org.vraptor.interceptor;

import org.vraptor.VRaptorException;

/**
 * Unable to instantiate an interceptor
 * 
 * @author Guilherme Silveira
 */
public class InterceptorInstantiationException extends VRaptorException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -628037460615779846L;

	public InterceptorInstantiationException() {
		super();
	}

	public InterceptorInstantiationException(String message, Throwable cause) {
		super(message, cause);
	}

	public InterceptorInstantiationException(String message) {
		super(message);
	}

	public InterceptorInstantiationException(Throwable cause) {
		super(cause);
	}

}
