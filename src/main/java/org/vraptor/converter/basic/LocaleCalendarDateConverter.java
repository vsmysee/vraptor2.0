package org.vraptor.converter.basic;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.vraptor.LogicRequest;
import org.vraptor.converter.ConversionException;
import org.vraptor.converter.Converter;

/**
 * Locale based calendar converter.
 * 
 * @author Guilherme Silveira
 */
public class LocaleCalendarDateConverter implements Converter {

	private static final Logger logger = Logger
			.getLogger(LocaleCalendarDateConverter.class);

	private String sessionKey = "javax.servlet.jsp.jstl.fmt.locale.session";

	public LocaleCalendarDateConverter() {
	}

	public LocaleCalendarDateConverter(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public Object convert(String value, Class<?> type, LogicRequest context)
			throws ConversionException {
		Locale locale = (Locale) context.getSessionContext().getAttribute(
				this.sessionKey);
		if (locale == null) {
			locale = Locale.getDefault();
		}
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, locale);
		Calendar calendar = new GregorianCalendar();
		try {
			calendar.setTime(df.parse(value));
		} catch (ParseException e) {
			LocaleCalendarDateConverter.logger.error("Unable to parse string "
					+ value, e);
			throw new ConversionException("invalid_date",
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
