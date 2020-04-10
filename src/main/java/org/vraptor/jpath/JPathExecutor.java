package org.vraptor.jpath;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.vraptor.LogicRequest;
import org.vraptor.annotations.Conversion;
import org.vraptor.component.ComponentInstantiationException;
import org.vraptor.converter.ConversionException;
import org.vraptor.converter.Converter;
import org.vraptor.converter.ConverterManager;
import org.vraptor.introspector.FieldReadParameter;
import org.vraptor.util.MethodInvocationException;
import org.vraptor.util.ReflectionUtil;
import org.vraptor.util.SettingException;

/**
 * JPath setter execution for a specific logiccontext.
 * 
 * @author Guilherme Silveira
 */
public class JPathExecutor {

	private static final Logger logger = Logger.getLogger(JPathExecutor.class);

	private ConverterManager converters;

	private LogicRequest context;

	public JPathExecutor(ConverterManager converters, LogicRequest logicRequest) {
		this.converters = converters;
		this.context = logicRequest;
	}

	/**
	 * Tries to set some property in the current object. It uses the path array
	 * to walk in the object graph, the matching field is called field and the
	 * value is either completeValue or arrayValue
	 * 
	 * @param object
	 *            the object to set the property
	 * @param path
	 *            the path to walk
	 * @param completeValue
	 *            the completeValue
	 * @param arrayValue
	 *            the array value
	 * @param field
	 *            the field to use
	 * @throws SettingException
	 *             some exception ocurred while trying to set a value
	 * @throws ConversionException
	 *             some convesion exception occurred
	 */
	public void set(Object component, String[] path, String completeValue,
			String[] arrayValue, FieldReadParameter read)
			throws SettingException, ConversionException {

		// if it is a simple path, just set it
		if (path.length == 1) {
			// just call the setter
			try {
				ReflectionUtil.set(component, read.getField(), convert(
						completeValue, arrayValue, read.getField().getType(),
						read.getOverridenConverter()));
			} catch (ConversionException e) {
				e.setCategory(path[0]);
				throw e;
			}
			return;
		}

		// TODO if path==2 and is an array, sets the array value

		// moves to the first field
		Object object = read.guaranteeExistence(component);
		if (object == null) {
			return;
		}

		try {

			int start = 1;

			// if array, moves it
			while (Character.isDigit(path[start].charAt(0))) {
				try {
					SetDealer dealer = SetDealerFactory
							.getDealer(object);
					int arrayPosition = Integer.parseInt(path[start]);
					object = dealer.resize(object, arrayPosition + 1, read
							.mightCreate(), read.getField().getGenericType());
					// calls the setter
					ReflectionUtil.set(component, read.getField(), object);
					// retrieves the specified position
					object = dealer.getPosition(object, arrayPosition, read
							.mightCreate());
					start++;
				} catch (NumberFormatException e) {
					throw new SettingException("Invalid array index: "
							+ path[1]);
				}
			}

			if (object == null) {
				return;
			}

			for (int i = start; i < path.length - 1; i++) {

				Object currentObject = object;

				// for each parameter, calls the getter method
				Method method = ReflectionUtil.findGetter(object.getClass(), path[i]);
				// if no getter found, forget it!
				if (method == null) {
					return;
				}
				object = ReflectionUtil.invoke(object, method);
				Class returnType = method.getReturnType();
				if (object == null) {
					if (read.mightCreate()) {
						try {
							// my getter returned null... i should instantiate
							// it
							if (isCollection(returnType)) {
								object = ReflectionUtil
										.instantiateCollection(returnType);
							} else {
								object = ReflectionUtil
										.genericInstantiate(returnType);
							}
							// calls the setter
							ReflectionUtil
									.invoke(currentObject,
											ReflectionUtil.findSetter(
													currentObject, path[i]),
											object);
						} catch (ComponentInstantiationException e) {
							throw new SettingException(e.getMessage(), e);
						}
					} else {
						return;
					}
				}

				// if the next is an array index, use it
				while (i < path.length
						&& Character.isDigit(path[i + 1].charAt(0))) {
					try {
						int arrayPosition = Integer.parseInt(path[i + 1]);
						SetDealer dealer = SetDealerFactory
								.getDealer(object);
						object = dealer.resize(object, arrayPosition + 1, read
								.mightCreate(), method.getGenericReturnType());
						// calls the setter
						ReflectionUtil.invoke(currentObject, ReflectionUtil
								.findSetter(currentObject, path[i]), object);
						// retrieves the specified position
						object = dealer.getPosition(object, arrayPosition, read
								.mightCreate());
						if (object == null) {
							return;
						}
					} catch (NumberFormatException e) {
						throw new SettingException("Invalid array index: "
								+ path[1]);
					}
					i++;
				}

			}

			// calls the setter for the last one
			Method setter = ReflectionUtil.findSetter(object,
					path[path.length - 1]);
			if (setter == null) {
				return;
			}

			logger.debug("ready to use parameter " + Arrays.toString(path));
			Conversion annotation = setter.getAnnotation(Conversion.class);
			Object result = convert(completeValue, arrayValue, setter
					.getParameterTypes()[0], annotation == null ? null
					: annotation.value());
			ReflectionUtil.invoke(object, setter, result);

		} catch (MethodInvocationException e) {
			throw new SettingException(e.getMessage(), e);
		} catch (IllegalArgumentException e) {
			throw new SettingException(e.getMessage(), e);
		} catch (SecurityException e) {
			throw new SettingException(e.getMessage(), e);
		} catch (ConversionException e) {
			e.setCategory(path[path.length - 1]);
			throw e;
		}
	}

	/**
	 * Converts a value either by its type or overriden converter
	 * 
	 * @param completeValue
	 *            complete value
	 * @param arrayValue
	 *            array value
	 * @param type
	 *            target type
	 * @param annotation
	 *            annotation (may be null)
	 * @return the converted value
	 * @throws ConversionException
	 *             something wrong occurred
	 */
	private Object convert(String completeValue, String[] arrayValue,
			Class type, Class<? extends Converter> converter)
			throws ConversionException {
		return this.converters.convert(arrayValue, completeValue, type,
				context, converter);
	}

	private boolean isCollection(Class<?> type) {
		return Collection.class.isAssignableFrom(type);
	}
}
