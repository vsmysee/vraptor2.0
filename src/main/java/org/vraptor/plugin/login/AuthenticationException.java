package org.vraptor.plugin.login;

/**
 * User is not authenticated
 * 
 * @author Guilherme Silveira
 */
public class AuthenticationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7184365721820533711L;

	/**
	 * 
	 */
	protected AuthenticationException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	protected AuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	protected AuthenticationException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	protected AuthenticationException(Throwable cause) {
		super(cause);
	}

}
