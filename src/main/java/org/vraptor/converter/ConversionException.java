package org.vraptor.converter;

import org.vraptor.VRaptorException;
import org.vraptor.i18n.Message;

/**
 * Basic convertion exception. Thrown when some conversion problem occurs i.e.
 * incompatibility or no converter found.
 * 
 * @author Guilherme Silveira
 */
public class ConversionException extends VRaptorException {

	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 8239876883373338343L;

	private String category;

	private String key;

	public ConversionException(String key, String message, Throwable cause) {
		super(message, cause);
		this.key = key;
	}

	public ConversionException(String key, String message) {
		super(message);
		this.key = key;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Message getI18NMessage() {
		return new Message(this.category, this.key);
	}

}
