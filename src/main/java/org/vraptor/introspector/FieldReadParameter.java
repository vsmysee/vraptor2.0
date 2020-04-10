package org.vraptor.introspector;

import java.lang.reflect.Field;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.vraptor.annotations.Conversion;
import org.vraptor.annotations.Read;
import org.vraptor.component.ComponentInstantiationException;
import org.vraptor.component.FieldAnnotation;
import org.vraptor.converter.Converter;
import org.vraptor.util.GettingException;
import org.vraptor.util.ReflectionUtil;
import org.vraptor.util.SettingException;

/**
 * A field based read parameter
 * 
 * @author Guilherme Silveira
 */
public class FieldReadParameter implements ReadParameter {
	
	private static final Logger logger = Logger
			.getLogger(FieldReadParameter.class);

	private Field field;

	private Class<? extends Converter> converter;

	private String key;

	private boolean mightCreate;

	public FieldReadParameter(FieldAnnotation<Read> info) {
		Read read = info.getAnnotation();
		this.field = info.getField();
		this.key = new KeyExtractor().extractReadKey(info);
		this.mightCreate = read.create();
		if (field.isAnnotationPresent(Conversion.class)) {
			this.converter = field.getAnnotation(Conversion.class).value();
		}
		logger.debug("Created new FieldReadParameter " + this.key);
	}

	/**
	 * The parameter key
	 * 
	 * @return key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Should it instantiate required fields on the fly?
	 * 
	 * @return true if it should instantiate them
	 */
	public boolean mightCreate() {
		return this.mightCreate;
	}

	/**
	 * Returns the field its working with
	 * 
	 * @return the field the field itself
	 */
	public Field getField() {
		return field;
	}

	/**
	 * Returns the overriden converter class attached to this read parameter
	 * 
	 * @return the converter class
	 */
	public Class<? extends Converter> getOverridenConverter() {
		return this.converter;
	}

	/**
	 * 
	 * @throws SettingException
	 *             setting exception
	 * @see org.vraptor.introspector.ReadParameter#guaranteeExistence(java.lang.Object)
	 */
	public Object guaranteeExistence(Object component) throws SettingException {
		try {
			Object value = ReflectionUtil.get(component, field);
			if (value == null && mightCreate()) {
				if (Collection.class.isAssignableFrom(field.getType())) {
					value = ReflectionUtil.instantiateCollection(field
							.getType());
				} else {
					value = ReflectionUtil.genericInstantiate(field.getType());
				}
				ReflectionUtil.set(component, field, value);
			}
			return value;
		} catch (GettingException e) {
			throw new SettingException(e.getMessage(), e);
		} catch (ComponentInstantiationException e) {
			throw new SettingException(e.getMessage(), e.getCause());
		}

	}

}
