package org.vraptor.converter.basic;

import org.vraptor.LogicRequest;
import org.vraptor.converter.ConversionException;
import org.vraptor.converter.Converter;

/**
 * Converts to Boolean
 * 
 * @author Guilherme Silveira
 */
public class SimpleBooleanConverter implements Converter {

	/**
	 * 
	 * @see org.vraptor.converter.Converter#convert(java.lang.String, java.lang.Class, org.vraptor.scope.LogicRequest)
	 */
	public Object convert(String value, Class<?> type, LogicRequest context)
			throws ConversionException {
		return Boolean.valueOf(value);
	}


	/**
	 * Returns the list of supported types
	 * 
	 * @see org.vraptor.converter.Converter#getSupportedTypes()
	 */
	public Class<?>[] getSupportedTypes() {
		return new Class[] { Boolean.class };
	}

}
