package org.vraptor.core;

import java.util.List;

import org.apache.log4j.Logger;
import org.vraptor.Interceptor;
import org.vraptor.LogicException;
import org.vraptor.LogicFlow;
import org.vraptor.component.ComponentInstantiationException;
import org.vraptor.component.ComponentType;
import org.vraptor.component.LogicMethod;
import org.vraptor.converter.ConversionProblems;
import org.vraptor.i18n.Message;
import org.vraptor.introspector.Introspector;
import org.vraptor.util.GettingException;
import org.vraptor.util.SettingException;
import org.vraptor.view.ViewException;

/**
 * The last interceptor: calls the logic method
 * 
 * @author Guilherme Silveira
 */
public class ExecuteLogicInterceptor implements Interceptor {

	private static final String INVALID = "invalid";

	private static final Logger logger = Logger
			.getLogger(ExecuteLogicInterceptor.class);

	private VRaptorExecution engine;

	private ComponentType componentClass;

	private LogicMethod logicMethod;

	public ExecuteLogicInterceptor(VRaptorExecution engine,
			ComponentType componentClass, LogicMethod logicMethod) {
		this.engine = engine;
		this.componentClass = componentClass;
		this.logicMethod = logicMethod;
	}

	public void intercept(LogicFlow logic) throws LogicException, ViewException {

		Introspector introspector = engine.getController().getIntrospector();

		logger.debug("last interception: ready to execute logic");

		try {

			// populate
			Object component = instantiateComponent(introspector);

			// execute
			String result = executeLogic(component);
			outjectLogic(introspector, component);

			logger.debug("last interception: ready to redirect");
			if (this.logicMethod.shouldRedirect()) {
				this.engine.redirect(result);
			}

		} catch (ConversionProblems ex) {
			logger.debug("Some conversion errors were found");
			this.engine.getRequest().getRequestContext().setAttribute("errors",
					ex.getProblems());
			this.engine.redirect(INVALID);
		}

	}

	private Object instantiateComponent(Introspector introspector)
			throws LogicException, ConversionProblems {

		try {

			// instantiate
			Object component = componentClass.newInstance();

			// inject
			introspector.inject(componentClass.getInAnnotations(), component,
					engine.getRequest());

			// read parameters
			List<Message> problems = introspector.readParameters(
					componentClass, component, engine.getRequest(), engine
							.getController().getWebApplication()
							.getConverterManager());
			if (problems.isEmpty()) {
				return component;
			} else {
				throw new ConversionProblems(problems);
			}

		} catch (ComponentInstantiationException e) {
			throw new LogicException(e.getMessage(), e);
		} catch (SettingException e) {
			throw new LogicException(e.getMessage(), e);
		}

	}

	/**
	 * @param introspector
	 * @param component
	 * @throws GettingException
	 */
	private void outjectLogic(Introspector introspector, Object component)
			throws LogicException {
		try {
			introspector.outject(componentClass.getOutAnnotations(), component,
					engine.getRequest());
		} catch (GettingException e) {
			throw new LogicException(e.getMessage(), e);
		}
	}

	/**
	 * Executes the validation and logic call
	 * 
	 * @param component
	 *            the current component
	 * @throws LogicException
	 *             logic problem
	 */
	private String executeLogic(Object component) throws LogicException {
		return logicMethod.execute(component, engine.getRequest());
	}

}
