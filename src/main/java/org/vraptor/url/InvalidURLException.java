package org.vraptor.url;

import org.vraptor.VRaptorException;

/**
 * Invalid url was requested. Unable to parse it.
 * 
 * @author Guilherme Silveira
 */
public class InvalidURLException extends VRaptorException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2120005561102791593L;

	public InvalidURLException() {
		super();
	}

	public InvalidURLException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidURLException(String message) {
		super(message);
	}

	public InvalidURLException(Throwable cause) {
		super(cause);
	}

}
