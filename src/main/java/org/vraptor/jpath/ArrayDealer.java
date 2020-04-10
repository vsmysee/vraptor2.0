package org.vraptor.jpath;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import org.vraptor.component.ComponentInstantiationException;
import org.vraptor.util.ReflectionUtil;
import org.vraptor.util.SettingException;

/**
 * An array implementation.
 * 
 * @author Guilherme Silveira
 */
public class ArrayDealer implements SetDealer {

	public Object resize(Object object, int length, boolean mayCreate,
			Type type) throws SettingException {
		try {
			return resizeArray(object, length, mayCreate);
		} catch (ComponentInstantiationException e) {
			throw new SettingException(e.getMessage(), e);
		}
	}

	private Object resizeArray(Object object, int length, boolean mayCreate)
			throws SettingException, ComponentInstantiationException {

		// if unable to get bigger, stop
		if (!mayCreate && length > Array.getLength(object)) {
			throw new SettingException("Invalid array index: " + length);
		}

		if (length > Array.getLength(object)) {
			// reinstantiates the array
			Object[] newArray = ReflectionUtil.instantiateArray(object
					.getClass().getComponentType(), length);
			for (int j = 0; j < Array.getLength(object); j++) {
				newArray[j] = Array.get(object, j);
			}
			for (int j = Array.getLength(object); j < length; j++) {
				newArray[j] = ReflectionUtil.instantiate(object.getClass()
						.getComponentType());
			}
			return newArray;
		}
		return object;
	}

	public Object getPosition(Object object, int arrayPosition,
			boolean mayCreate) {
		return Array.get(object, arrayPosition);
	}

}
