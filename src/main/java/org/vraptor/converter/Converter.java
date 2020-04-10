package org.vraptor.converter;

import org.vraptor.LogicRequest;

/**
 * Basic converter
 * 
 * @author Guilherme Silveira
 */
public interface Converter {

	/**
	 * Converts a value to an specific type
	 * 
	 * @param value
	 *            current value
	 * @param type
	 *            desired type
	 * @param context
	 *            logic context
	 * @return the converted value
	 * @throws ConversionException
	 *             some convertion problem hapenned
	 */
	public Object convert(String value, Class<?> type, LogicRequest context)
			throws ConversionException;

	/**
	 * Returns the list of supported types
	 * 
	 * @return array of supported types
	 */
	public Class<?>[] getSupportedTypes();

}
