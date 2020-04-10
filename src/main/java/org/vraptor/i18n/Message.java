package org.vraptor.i18n;

/**
 * A i18n message.
 * 
 * @author Guilherme Silveira
 */
public class Message {

	private String message;

	private String[] parameters;

	private String category;

	public Message(String category, String message, String... parameters) {
		this.message = message;
		this.category = category;
		this.parameters = parameters;
	}

	/**
	 * Returns the message to be found
	 * 
	 * @return the message
	 */
	public String getKey() {
		return this.message;
	}

	public String[] getParameters() {
		return parameters;
	}

	public String getCategory() {
		return category;
	}

}
