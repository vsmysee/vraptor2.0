package org.vraptor.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.vraptor.annotations.In;
import org.vraptor.annotations.Out;
import org.vraptor.annotations.Read;
import org.vraptor.interceptor.InterceptorClass;
import org.vraptor.introspector.FieldReadParameter;
import org.vraptor.util.ReflectionUtil;

/**
 * Metadata information about a component class. This class is used to dealing
 * with component classes.
 * 
 * @author Guilherme Silveira
 */
public class DefaultComponentType implements ComponentType {

	private static final Logger logger = Logger.getLogger(DefaultComponentType.class);

	private Map<String, DefaultLogicMethod> actions;

	private List<FieldAnnotation<In>> inAnnotations;

	private List<FieldAnnotation<Out>> outAnnotations;

	private List<FieldReadParameter> readParameters = new ArrayList<FieldReadParameter>();

	private List<InterceptorClass> interceptors;

	private Class<?> clazz;

	private String name;

	<T> DefaultComponentType(Class<T> clazz, String name,
			Map<String, DefaultLogicMethod> actions,
			List<FieldAnnotation<In>> inAnnotations,
			List<FieldAnnotation<Out>> outAnnotations,
			List<FieldAnnotation<Read>> readAnnotations,
			List<InterceptorClass> interceptors) {
		this.name = name;
		this.actions = actions;
		this.inAnnotations = inAnnotations;
		this.outAnnotations = outAnnotations;
		this.interceptors = interceptors;
		this.clazz = clazz;
		for (FieldAnnotation<Read> info : readAnnotations) {
			this.readParameters.add(new FieldReadParameter(info));
		}
	}

	/* (non-Javadoc)
	 * @see org.vraptor.component.ComponentType#newInstance()
	 */
	public Object newInstance() throws ComponentInstantiationException {
		logger.debug("Instantiating class " + this.clazz.getName());
		return ReflectionUtil.instantiate(this.clazz);
	}

	/* (non-Javadoc)
	 * @see org.vraptor.component.ComponentType#getName()
	 */
	public String getName() {
		return this.name;
	}

	/* (non-Javadoc)
	 * @see org.vraptor.component.ComponentType#getLogic(java.lang.String)
	 */
	public LogicMethod getLogic(String key) throws LogicNotFoundException {
		if (!this.actions.containsKey(key)) {
			throw new LogicNotFoundException("Unable to find action " + key);
		}
		return this.actions.get(key);
	}

	/* (non-Javadoc)
	 * @see org.vraptor.component.ComponentType#getInterceptors()
	 */
	public List<InterceptorClass> getInterceptors() {
		return this.interceptors;
	}

	/* (non-Javadoc)
	 * @see org.vraptor.component.ComponentType#getInAnnotations()
	 */
	public List<FieldAnnotation<In>> getInAnnotations() {
		return this.inAnnotations;
	}

	/* (non-Javadoc)
	 * @see org.vraptor.component.ComponentType#getOutAnnotations()
	 */
	public List<FieldAnnotation<Out>> getOutAnnotations() {
		return this.outAnnotations;
	}

	/* (non-Javadoc)
	 * @see org.vraptor.component.ComponentType#getReadParameters()
	 */
	public List<FieldReadParameter> getReadParameters() {
		return this.readParameters;
	}

	public Collection<LogicMethod> getLogics() {
		return new ArrayList<LogicMethod>(this.actions.values());
	}

}
