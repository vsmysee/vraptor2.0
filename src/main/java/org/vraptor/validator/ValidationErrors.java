package org.vraptor.validator;

import java.util.Iterator;

import org.vraptor.i18n.Message;

/**
 * Collection of validation errors. List is not used as there should be limited
 * access to some methods.
 * 
 * @author Guilherme Silveira
 */
public interface ValidationErrors {

	/**
	 * Adds a new validation error
	 * 
	 * @param error
	 *            the validation error
	 */
	void add(Message error);

	/**
	 * Returns the number of validation errors registered
	 * 
	 * @return the number of validation errors
	 */
	int size();

	/**
	 * Retrieves an iterator
	 * 
	 * @return the iterator
	 */
	Iterator<Message> getIterator();

}
