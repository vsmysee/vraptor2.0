package org.vraptor.plugin.login;

/**
 * User is not authenticated
 * 
 * @author Guilherme Silveira
 */
public class AuthorizationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8495952558523453730L;

	/**
	 * 
	 */
	protected AuthorizationException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	protected AuthorizationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	protected AuthorizationException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	protected AuthorizationException(Throwable cause) {
		super(cause);
	}

}
