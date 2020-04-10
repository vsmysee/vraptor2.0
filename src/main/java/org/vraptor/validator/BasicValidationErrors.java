package org.vraptor.validator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.vraptor.i18n.Message;

/**
 * A basic list implementation of validation errors
 * 
 * @author Guilherme Silveira
 */
public class BasicValidationErrors implements ValidationErrors {

	private List<Message> errors = new ArrayList<Message>();

	/**
	 * 
	 * @see org.vraptor.validator.ValidationErrors#add(org.vraptor.i18n.Message)
	 */
	public void add(Message error) {
		errors.add(error);
	}

	/**
	 * 
	 * @see org.vraptor.validator.ValidationErrors#size()
	 */
	public int size() {
		return errors.size();
	}

	public Iterator<Message> getIterator() {
		return errors.iterator();
	}

}
