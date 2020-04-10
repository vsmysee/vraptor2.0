package org.vraptor.plugin;

import java.util.Collection;
import java.util.List;

import org.vraptor.annotations.In;
import org.vraptor.annotations.Out;
import org.vraptor.component.ComponentInstantiationException;
import org.vraptor.component.ComponentType;
import org.vraptor.component.FieldAnnotation;
import org.vraptor.component.LogicMethod;
import org.vraptor.component.LogicNotFoundException;
import org.vraptor.interceptor.InterceptorClass;
import org.vraptor.introspector.FieldReadParameter;

class ProxyTestComponent implements ComponentType {

	private ComponentType type;
	private TestCreator creator;

	public ProxyTestComponent(ComponentType component, TestCreator creator) {
		this.type = component;
		this.creator = creator;
	}

	public List<FieldAnnotation<In>> getInAnnotations() {
		return type.getInAnnotations();
	}

	public List<InterceptorClass> getInterceptors() {
		return type.getInterceptors();
	}

	public LogicMethod getLogic(String key) throws LogicNotFoundException {
		return new ProxyTestLogicMethod(type.getLogic(key), creator);
	}

	public String getName() {
		return type.getName();
	}

	public List<FieldAnnotation<Out>> getOutAnnotations() {
		return type.getOutAnnotations();
	}

	public List<FieldReadParameter> getReadParameters() {
		return type.getReadParameters();
	}

	public Object newInstance() throws ComponentInstantiationException {
		return type.newInstance();
	}

	public Collection<LogicMethod> getLogics() {
		return type.getLogics();
	}

}
