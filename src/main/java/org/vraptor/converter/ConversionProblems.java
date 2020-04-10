package org.vraptor.converter;

import java.util.List;

import org.vraptor.i18n.Message;

/**
 * Conversion problems.
 * 
 * @author Guilherme Silveira
 */
public class ConversionProblems extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 191181425881437770L;

	private final List<Message> problems;

	public ConversionProblems(List<Message> problems) {
		this.problems = problems;
	}

	public Object getProblems() {
		return this.problems;
	}

}
