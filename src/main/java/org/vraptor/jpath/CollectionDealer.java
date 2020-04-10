package org.vraptor.jpath;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;
import org.vraptor.component.ComponentInstantiationException;
import org.vraptor.util.ReflectionUtil;
import org.vraptor.util.SettingException;

/**
 * Collection implementation
 * 
 * @author Guilherme Silveira
 */
public class CollectionDealer implements SetDealer {

	public Object resize(Object object, int length, boolean mayCreate,
			Type type) throws SettingException {
		try {
			return resizeCollection((Collection) object, length, mayCreate,
					type);
		} catch (ComponentInstantiationException e) {
			throw new SettingException(e.getMessage(), e);
		}
	}
	
	/**
	 * Resizes a collection
	 * 
	 * @param collection
	 *            collection
	 * @param length
	 *            desired length
	 * @param mayCreate
	 *            maycreate it?
	 * @param type
	 *            the type to be used
	 * @return the collection with enough spaces
	 * @throws SettingException
	 *             some problem occurred during resizing
	 * @throws ComponentInstantiationException
	 */
	@SuppressWarnings("unchecked")
	private Object resizeCollection(Collection collection, int length,
			boolean mayCreate, Type type) throws SettingException,
			ComponentInstantiationException {
		if (!mayCreate && collection.size() < length) {
			throw new SettingException("Invalid collection index: "
					+ length);
		}
		Class desiredClass = (type instanceof ParameterizedType) ? (Class) ((ParameterizedType) type)
				.getActualTypeArguments()[0]
				: Object.class;
		while (collection.size() < length) {
			collection.add(ReflectionUtil.instantiate(desiredClass));
		}
		return collection;
	}
	
	public Object getPosition(Object object, int arrayPosition,
			boolean mayCreate) throws SettingException {
		Collection collection = (Collection) object;
		if (arrayPosition >= collection.size()) {
			throw new SettingException("Invalid collection index: "
					+ arrayPosition);
		}
		return iterate((Collection) object, arrayPosition);
	}
	
	/**
	 * Iterates over a collection n times
	 * 
	 * @param collection
	 *            the collection
	 * @param index
	 *            n
	 * @return the object at this position
	 */
	Object iterate(Collection set, int index) {
		Iterator it = set.iterator();
		for (int i = 0; i < index; i++, it.next()) {
			;
		}
		return it.next();
	}

}
