package org.vraptor.introspector;

import org.vraptor.annotations.In;
import org.vraptor.annotations.Out;
import org.vraptor.annotations.Read;
import org.vraptor.component.FieldAnnotation;

/**
 * Responsible for extracting keys from field annotations
 * 
 * @author Guilherme Silveira
 */
public class KeyExtractor {

	/**
	 * Retrieves the key value for an out annotation
	 * 
	 * @param info
	 *            the annotation
	 * @return the key value
	 */
	String extractOutKey(FieldAnnotation<Out> info) {
		String key = info.getAnnotation().key();
		if (key.equals("")) {
			key = info.getField().getName();
		}
		return key;
	}

	/**
	 * Retrieves the key value for an in annotation
	 * 
	 * @param info
	 *            the annotation
	 * @return the key value
	 */
	String extractInKey(FieldAnnotation<In> info) {
		String key = info.getAnnotation().key();
		if (key.equals("")) {
			key = info.getField().getName();
		}
		return key;
	}

	/**
	 * Retrieves the key value for an in annotation
	 * 
	 * @param info
	 *            the annotation
	 * @return the key value
	 */
	String extractReadKey(FieldAnnotation<Read> info) {
		String key = info.getAnnotation().key();
		if (key.equals("")) {
			key = info.getField().getName();
		}
		return key;
	}

}
