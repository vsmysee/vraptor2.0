package org.vraptor.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;
import org.vraptor.LogicRequest;

/**
 * Fixed Locale based calendar converter
 * 
 * @author Guilherme Silveira
 */
public class FixedPatternCalendarConverter implements Converter {

	private static final Logger logger = Logger
			.getLogger(FixedPatternCalendarConverter.class);

	private String pattern;

	/**
	 * Simple constructor with pattern
	 * 
	 * @param pattern
	 *            pattern
	 */
	public FixedPatternCalendarConverter(String pattern) {
		super();
		this.pattern = pattern;
	}

	/**
	 * 
	 * @see org.vraptor.converter.Converter#convert(java.lang.String,
	 *      java.lang.Class, org.vraptor.scope.LogicRequest)
	 */
	public Object convert(String value, Class<?> type, LogicRequest context)
			throws ConversionException {
		DateFormat df = new SimpleDateFormat(this.pattern);
		Calendar calendar = new GregorianCalendar();
		try {
			calendar.setTime(df.parse(value));
		} catch (ParseException e) {
			FixedPatternCalendarConverter.logger.error(
					"Unable to parse string " + value, e);
			throw new ConversionException("invalid_value",
					"Unable to parse string " + value, e);
		}
		return calendar;
	}

	/**
	 * Returns the list of supported types
	 * 
	 * @see org.vraptor.converter.Converter#getSupportedTypes()
	 */
	public Class<?>[] getSupportedTypes() {
		return new Class[] { Calendar.class };
	}

}
