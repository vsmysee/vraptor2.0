package org.vraptor.converter.basic;

import org.vraptor.LogicRequest;
import org.vraptor.converter.ConversionException;
import org.vraptor.converter.Converter;

/**
 * Primitive boolean converter
 * 
 * @author Guilherme Silveira
 */
public class PrimitiveBooleanConverter implements Converter {

	public Object convert(String value, Class<?> type, LogicRequest context)
			throws ConversionException {
		return Boolean.parseBoolean(value);
	}

	/**
	 * Returns the list of supported types
	 * 
	 * @see org.vraptor.converter.Converter#getSupportedTypes()
	 */
	public Class<?>[] getSupportedTypes() {
		return new Class[] { boolean.class };
	}

}
