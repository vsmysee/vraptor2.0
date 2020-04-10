package org.vraptor.converter.basic;

import org.vraptor.LogicRequest;
import org.vraptor.converter.ConversionException;
import org.vraptor.converter.Converter;

/**
 * Primitive byte converter
 * 
 * @author Guilherme Silveira
 */
public class PrimitiveByteConverter implements Converter {

	public Object convert(String value, Class<?> type, LogicRequest context)
			throws ConversionException {
		try {
			return Byte.parseByte(value);
		} catch (NumberFormatException e) {
			throw new ConversionException("invalid_byte", e.getMessage(), e);
		}
	}

	/**
	 * Returns the list of supported types
	 * 
	 * @see org.vraptor.converter.Converter#getSupportedTypes()
	 */
	public Class<?>[] getSupportedTypes() {
		return new Class[] { byte.class };
	}

}
