package org.vraptor.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.vraptor.LogicRequest;

/**
 * Locale based calendar converter.
 * 
 * @author Guilherme Silveira
 */
public class LocaleCalendarTimeConverter implements Converter {

	private String sessionKey = "javax.servlet.jsp.jstl.fmt.locale.session";

	public LocaleCalendarTimeConverter() {
	}

	public LocaleCalendarTimeConverter(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public Object convert(String value, Class<?> type, LogicRequest context)
			throws ConversionException {
		Locale locale = (Locale) context.getSessionContext().getAttribute(
				this.sessionKey);
		if (locale == null) {
			locale = Locale.getDefault();
		}
		DateFormat df = DateFormat.getTimeInstance(DateFormat.SHORT, locale);
		Calendar calendar = new GregorianCalendar();
		try {
			calendar.setTime(df.parse(value));
		} catch (ParseException e) {
			throw new ConversionException("invalid_time",
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
