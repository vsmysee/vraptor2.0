package org.vraptor.component;

import org.vraptor.VRaptorException;

/**
 * Logic not found or somehow invalid
 * 
 * @author Guilherme Silveira
 */
public class LogicNotFoundException extends VRaptorException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8107019768951502159L;

	/**
	 * 
	 */
	public LogicNotFoundException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public LogicNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public LogicNotFoundException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public LogicNotFoundException(Throwable cause) {
		super(cause);
	}

}
