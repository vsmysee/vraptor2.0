package org.vraptor.factory;

import org.vraptor.VRaptorException;

/**
 * Some problem during factory instantiation
 * 
 * @author Guilherme Silveira
 */
public class FactoryException extends VRaptorException {

	private static final long serialVersionUID = -931645337579480495L;

	public FactoryException() {
		super();
	}

	public FactoryException(String message, Throwable cause) {
		super(message, cause);
	}

	public FactoryException(String message) {
		super(message);
	}

	public FactoryException(Throwable cause) {
		super(cause);
	}

}
