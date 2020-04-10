package org.vraptor.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import org.vraptor.component.ComponentInstantiationException;
import org.vraptor.component.FieldAnnotation;

/**
 * Wrapper class to deal with some reflection.
 * TODO: this is too much procedural work, should change to an instance. Take care of synchronization issues.
 * 
 * @author Guilherme Silveira
 */
public class ReflectionUtil {

	private static final Logger logger = Logger.getLogger(ReflectionUtil.class);
	
	/**
	 * Instantiates a class.
	 * 
	 * @return the new object
	 * @throws ComponentInstantiationException
	 *             if something wrong occurs
	 */
	public static <T> T instantiate(Class<T> clazz)
			throws ComponentInstantiationException {
		try {
			return clazz.getConstructor().newInstance();
		} catch (InvocationTargetException e) {
			logger.error("Unable to instantiate class " + clazz.getName(), e);
			throw new ComponentInstantiationException(e.getMessage(), e
					.getCause());
		} catch (Exception e) {
			logger.error("Unable to instantiate class " + clazz.getName(), e);
			throw new ComponentInstantiationException(e.getMessage(), e);
		}
	}

	/**
	 * Wrapper for method invocation
	 * 
	 * @param object
	 *            object
	 * @param method
	 *            method
	 * @param parameters
	 *            parameters
	 * @return the method's return
	 * @throws MethodInvocationException
	 */
	public static Object invoke(Object object, Method method,
			Object... parameters) throws MethodInvocationException {
		try {
			return method.invoke(object, parameters);
		} catch (InvocationTargetException e) {
			logger.error(e.getMessage(), e);
			throw new MethodInvocationException(e.getMessage(), e.getCause());
		} catch (Exception e) {
			logger.error("Unable to execute " + method.getName() + ": "
					+ e.getMessage(), e);
			throw new MethodInvocationException("Unable to execute "
					+ method.getName() + ": " + e.getMessage(), e);
		}
	}

	/**
	 * Sets a field (must be accessible)
	 * 
	 * @param component
	 *            component
	 * @param field
	 *            field
	 * @param value
	 *            new value
	 * @throws SettingException
	 *             unable to call setter
	 */
	public static void set(Object component, Field field, Object value)
			throws SettingException {
		try {
			field.set(component, value);
		} catch (IllegalArgumentException e) {
			logger.error("Unable to set field "
					+ field.getName() + ": " + e.getMessage(), e);
			throw new SettingException("Unable to set field " + field.getName()
					+ ": " + e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.error("Unable to set field "
					+ field.getName() + ": " + e.getMessage(), e);
			throw new SettingException("Unable to set field " + field.getName()
					+ ": " + e.getMessage(), e);
		}
	}

	public static Object get(Object component, Field field)
			throws GettingException {
		try {
			return field.get(component);
		} catch (IllegalAccessException e) {
			logger.error("Unable to get field "
					+ field.getName() + ": " + e.getMessage(), e);
			throw new GettingException("Unable to get field " + field.getName()
					+ ": " + e.getMessage(), e);
		}
	}

	public static Method findGetter(Class type, String property)
			throws MethodInvocationException {
		String methodName = "get" + Character.toUpperCase(property.charAt(0)) + property.substring(1);
		try {
			return type.getMethod(
					methodName);
		} catch (SecurityException e) {
			logger.error("Unable to get getter method " + property, e);
			throw new MethodInvocationException("Unable to get getter method "
					+ property, e);
		} catch (NoSuchMethodException e) {
			return null;
		}
	}

	/**
	 * Retrieves the setter method in an object for the specified property
	 * 
	 * @param current
	 *            the current object
	 * @param property
	 *            the setter to find
	 * @return the setter or null if not found
	 */
	public static Method findSetter(Object current, String property) {
		String methodName = "set" + Character.toUpperCase(property.charAt(0))
				+ property.substring(1);
		for (Method m : current.getClass().getMethods()) {
			if (m.getName().equals(methodName)) {
				return m;
			}
		}
		return null;
	}

	/**
	 * Read all field annotations from one type
	 */
	public static <T extends Annotation> List<FieldAnnotation<T>> readAnnotations(
			Class clazz, Class<T> annot) {
		List<FieldAnnotation<T>> list = new ArrayList<FieldAnnotation<T>>();
		for (Field f : clazz.getDeclaredFields()) {
			if (!f.isAnnotationPresent(annot)) {
				continue;
			}
			T annotation = f.getAnnotation(annot);
			logger.info("Adding field annotation on field "
					+ f.getName() + "::" + annotation);
			list.add(new FieldAnnotation<T>(annotation, f));
			f.setAccessible(true);
		}
		return list;
	}

	public static Object[] instantiateArray(Class<?> componentType, int length)
			throws ComponentInstantiationException {
		try {
			return (Object[]) Array.newInstance(componentType, length);
		} catch (Exception e) {
			logger.error("Unable to instantiate class "
					+ componentType.getName(), e);
			throw new ComponentInstantiationException(e.getMessage(), e);
		}
	}

	/**
	 * Tries to instantiate an array of size 0 or type
	 * 
	 * @param type
	 *            type
	 * @return instantiates
	 * @throws ComponentInstantiationException
	 *             problem instantiating it
	 */
	public static Object genericInstantiate(Class<?> type)
			throws ComponentInstantiationException {
		if (type.isArray())
			return instantiateArray(type.getComponentType(), 0);
		return instantiate(type);
	}

	public static Object instantiateCollection(Type type)
			throws ComponentInstantiationException {
		Class clazz = (Class) type;
		if (List.class.isAssignableFrom(clazz))
			return new ArrayList();
		if (Set.class.isAssignableFrom(clazz))
			return new LinkedHashSet();
		throw new ComponentInstantiationException(
				"Unable to instantiate the desired collection");
	}

	/**
	 * Tries to find an specific annotation inside an array
	 * 
	 * @param <T>
	 *            the annotation type
	 * @param annotations
	 *            the array
	 * @param clazz
	 *            the annotation class to be found
	 * @return the annotation found or null if not found
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Annotation> T findAnnotation(
			Annotation[] annotations, Class<T> clazz) {
		for (Annotation a : annotations) {
			if (a.annotationType().equals(clazz)) {
				return (T) a;
			}
		}
		return null;
	}

	/**
	 * Tries to instantiate this class
	 * 
	 * @param class
	 *            the class to instantiate
	 * @return the instance
	 * @throws ComponentInstantiationException
	 */
	public static Object instantiate(String clazz)
			throws ComponentInstantiationException {
		try {
			return instantiate(Class.forName(clazz));
		} catch (ClassNotFoundException e) {
			throw new ComponentInstantiationException("Unable to instantiate "
					+ clazz, e);
		}
	}

	/**
	 * Returns a prefixed method.
	 * 
	 * @param clazz
	 *            the clazz where we are looking for a method
	 * @param prefix
	 *            the method prefix
	 * @param name
	 *            the suffix
	 * @return the found prefixed method
	 * @throws MethodInvocationException
	 *             unsufficient rights to look for such method
	 */
	public static Method getPrefixedMethod(Class<?> clazz, String prefix,
			String name, Class... parameterTypes)
			throws MethodInvocationException {
		String methodName = prefix + Character.toUpperCase(name.charAt(0))
				+ name.substring(1);
		for (Method m : clazz.getMethods()) {
			if (m.getName().equals(methodName)) {
				if (Arrays.equals(m.getParameterTypes(), parameterTypes)) {
					return m;
				}
			}
		}
		return null;
	}

}
