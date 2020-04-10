package org.vraptor.interceptor;

import org.vraptor.Interceptor;
import org.vraptor.LogicRequest;
import org.vraptor.component.ComponentInstantiationException;
import org.vraptor.introspector.Introspector;
import org.vraptor.util.GettingException;
import org.vraptor.util.SettingException;

/**
 * Deals with injecting and outjecting to and from interceptors
 * 
 * @author Guilherme Silveira
 */
public class InterceptorDealer {

	private Introspector introspector;

	public InterceptorDealer(Introspector introspector) {
		this.introspector = introspector;
	}

	public void outject(Interceptor interceptor, InterceptorClass clazz,
			LogicRequest context) throws GettingException {
		introspector.outject(clazz.getOutAnnotations(), interceptor, context);
	}

	public void inject(Interceptor interceptor, InterceptorClass clazz,
			LogicRequest context) throws ComponentInstantiationException,
			SettingException {
		this.introspector
				.inject(clazz.getInAnnotations(), interceptor, context);
	}

}
