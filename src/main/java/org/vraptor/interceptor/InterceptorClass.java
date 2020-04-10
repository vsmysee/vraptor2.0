package org.vraptor.interceptor;

import java.util.List;

import org.apache.log4j.Logger;
import org.vraptor.Interceptor;
import org.vraptor.annotations.In;
import org.vraptor.annotations.Out;
import org.vraptor.component.ComponentInstantiationException;
import org.vraptor.component.FieldAnnotation;
import org.vraptor.util.ReflectionUtil;

/**
 * Interceptor class to deal with interceptors instances
 * 
 * @author Guilherme Silveira
 */
public class InterceptorClass {

	private static final Logger logger = Logger
			.getLogger(InterceptorClass.class);

	private Class<? extends Interceptor> clazz;

	/** Out annotations */
	private List<FieldAnnotation<Out>> outAnnotations;

	/** In annotations */
	private List<FieldAnnotation<In>> inAnnotations;

	/**
	 * New interceptor class
	 * 
	 * @param c
	 *            class
	 */
	public InterceptorClass(Class<? extends Interceptor> c) {

		logger.info("Reading interceptor class " + c.getName());

		this.clazz = c;

		this.outAnnotations = ReflectionUtil.readAnnotations(this.clazz,
				Out.class);

		this.inAnnotations = ReflectionUtil.readAnnotations(this.clazz,
				In.class);

		InterceptorClass.logger.debug("Interceptor " + c.getName() + "read");

	}

	public Interceptor newInstance() throws ComponentInstantiationException {
		return ReflectionUtil.instantiate(this.clazz);
	}

	/**
	 * @return Returns the outAnnotations.
	 */
	public List<FieldAnnotation<Out>> getOutAnnotations() {
		return this.outAnnotations;
	}

	/**
	 * @return Returns the inAnnotations.
	 */
	public List<FieldAnnotation<In>> getInAnnotations() {
		return this.inAnnotations;
	}

}
