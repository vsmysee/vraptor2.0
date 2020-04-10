package org.vraptor.introspector;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.vraptor.LogicRequest;
import org.vraptor.annotations.In;
import org.vraptor.annotations.Out;
import org.vraptor.component.ComponentInstantiationException;
import org.vraptor.component.ComponentType;
import org.vraptor.component.FieldAnnotation;
import org.vraptor.converter.ConversionException;
import org.vraptor.converter.ConverterManager;
import org.vraptor.i18n.Message;
import org.vraptor.jpath.JPathExecutor;
import org.vraptor.scope.RequestContext;
import org.vraptor.scope.ScopeLocator;
import org.vraptor.util.GettingException;
import org.vraptor.util.ReflectionUtil;
import org.vraptor.util.SettingException;

/**
 * The default introspector implementation.
 * 
 * @author Guilherme Silveira
 */
public class BasicIntrospector implements Introspector {

	private static final Logger logger = Logger
			.getLogger(BasicIntrospector.class);

	private KeyExtractor keyExtractor = new KeyExtractor();
	
	private ScopeLocator scopeLocator = new ScopeLocator();

	@SuppressWarnings("unchecked")
	public List<Message> readParameters(ComponentType clazz, Object component,
			LogicRequest logicRequest, ConverterManager converterManager)
			throws SettingException{

		JPathExecutor executor = new JPathExecutor(converterManager,
				logicRequest);
		RequestContext request = logicRequest.getRequestContext();

		Map<String, Object> parameters = request.getParameterMap();
		List<Message> problems = new ArrayList<Message>();
		out: for (String parameter : parameters.keySet()) {
			String path[] = parameter.split("[\\.\\[\\]]+");
			for (FieldReadParameter read : clazz.getReadParameters()) {
				// tries to fill
				if (path[0].equals(read.getKey())) {
					logger.debug("Parameter " + parameter
							+ " will be used on field " + read.getKey());
					try {
						// dont do this, cache it in request instead (or use the
						// internal request itself)
						executor.set(component, path, singleValue(parameters
								.get(parameter)), arrayValue(parameters
								.get(parameter)), read);
					} catch (ConversionException e) {
						// validation problem...
						logger.debug(e.getMessage(), e);
						problems.add(e.getI18NMessage());
					}
					continue out;
				}
			}
			logger.debug("Parameter not used: " + parameter);
		}
		
		return problems;

	}

	private String[] arrayValue(Object val) {
		if (val.getClass().isArray()) {
			return (String[]) val;
		}
		return new String[] { (String) val };
	}

	private String singleValue(Object val) {
		if (val.getClass().isArray()) {
			return (String) Array.get(val, 0);
		}
		return (String) val;
	}

	/**
	 * @see org.vraptor.introspector.Introspector#inject(java.util.List,
	 *      java.lang.Object, org.vraptor.scope.LogicRequest)
	 */
	public void inject(List<FieldAnnotation<In>> inAnnotations,
			Object component, LogicRequest context)
			throws ComponentInstantiationException, SettingException {

		for (FieldAnnotation<In> info : inAnnotations) {

			// tries to fill
			In in = info.getAnnotation();
			String key = keyExtractor.extractInKey(info);
			Object value = scopeLocator.get(in.scope(), context).getAttribute(key);

			logger.debug("Injecting " + value + " with key " + key + " at "
					+ in.scope());

			if (value == null) {
				if (in.create()) {
					logger.debug("Trying to create "
							+ info.getField().getType().getName());
					value = ReflectionUtil.instantiate(info.getField()
							.getType());
				} else if (in.required()) {
					throw new RuntimeException(
							"Unable to fill inject value for field "
									+ info.getField().getName());
				}
			}

			ReflectionUtil.set(component, info.getField(), value);

		}

	}

	/**
	 * @see org.vraptor.introspector.Introspector#outject(java.util.List,
	 *      java.lang.Object, org.vraptor.scope.LogicRequest)
	 */
	public void outject(List<FieldAnnotation<Out>> outAnnotations,
			Object component, LogicRequest context) throws GettingException {
		for (FieldAnnotation<Out> info : outAnnotations) {
			// tries to fill
			Out out = info.getAnnotation();
			String key = keyExtractor.extractOutKey(info);
			Object value = ReflectionUtil.get(component, info.getField());
			logger.debug("Outjecting " + value + " with key " + key + " at "
					+ out.scope());
			scopeLocator.get(out.scope(), context).setAttribute(key, value);
		}
	}

}
