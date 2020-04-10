package org.vraptor.util;

import org.vraptor.VRaptorException;

/**
 * Exception caused while trying to get a field's value
 * 
 * @author Guilherme Silveira
 */
public class GettingException extends VRaptorException {

	private static final long serialVersionUID = -5620431708629544816L;

	public GettingException() {
		super();
	}

	public GettingException(String message, Throwable cause) {
		super(message, cause);
	}

	public GettingException(String message) {
		super(message);
	}

	public GettingException(Throwable cause) {
		super(cause);
	}

}
