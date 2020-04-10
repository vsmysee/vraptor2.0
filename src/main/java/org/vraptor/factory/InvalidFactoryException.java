package org.vraptor.factory;

import org.vraptor.VRaptorException;

/**
 * Invalid factory component.
 * 
 * @author Guilherme Silveira
 */
public class InvalidFactoryException extends VRaptorException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3516627684184824760L;

	public InvalidFactoryException() {
		super();
	}

	public InvalidFactoryException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidFactoryException(String message) {
		super(message);
	}

	public InvalidFactoryException(Throwable cause) {
		super(cause);
	}

}
