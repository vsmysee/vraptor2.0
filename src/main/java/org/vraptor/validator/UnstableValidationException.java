package org.vraptor.validator;

import org.vraptor.LogicException;

/**
 * A validation method has thrown an exception. No validation method should
 * throw an exception instead, they should add errors to the result
 * 
 * @author Guilherme Silveira
 */
public class UnstableValidationException extends LogicException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5901912454471441100L;

	/**
	 * 
	 */
	protected UnstableValidationException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public UnstableValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	protected UnstableValidationException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	protected UnstableValidationException(Throwable cause) {
		super(cause);
	}

}
