package org.vraptor.interceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.vraptor.Interceptor;
import org.vraptor.annotations.InterceptedBy;

/**
 * Responsible for extracting the interceptor list from a class.
 * 
 * @author Guilherme Silveira
 */
public class InterceptorListFactory {

	private static final Logger logger = Logger
			.getLogger(InterceptorListFactory.class);

	private Map<Class, InterceptorClass> cache = new HashMap<Class, InterceptorClass>();

	/**
	 * Returns a list of interceptors for the specified class
	 * 
	 * @param clazz
	 *            class
	 * @return interceptor's list
	 */
	public List<InterceptorClass> getInterceptors(Class<?> clazz) {
		// read interceptors
		logger.debug("Loading interceptor list for class " + clazz.getName());
		List<InterceptorClass> interceptors = new ArrayList<InterceptorClass>();
		if (clazz.isAnnotationPresent(InterceptedBy.class)) {
			@SuppressWarnings("unchecked")
			InterceptedBy annotation = (InterceptedBy) clazz
					.getAnnotation(InterceptedBy.class);
			for (Class<? extends Interceptor> c : annotation.value()) {
				InterceptorClass interceptor = getInterceptorClass(c);
				logger.info("Adding interceptor " + c.getName());
				interceptors.add(interceptor);
			}
		}
		return interceptors;
	}

	public InterceptorClass getInterceptorClass(Class<? extends Interceptor> c) {
		synchronized (cache) {
			if (!cache.containsKey(c)) {
				cache.put(c, new InterceptorClass(c));
			}
			return cache.get(c);
		}
	}

}
