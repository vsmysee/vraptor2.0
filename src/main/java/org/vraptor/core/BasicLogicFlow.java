package org.vraptor.core;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.vraptor.Interceptor;
import org.vraptor.LogicException;
import org.vraptor.LogicFlow;
import org.vraptor.RedirectException;
import org.vraptor.component.ComponentClassFactory;
import org.vraptor.component.ComponentInstantiationException;
import org.vraptor.component.ComponentType;
import org.vraptor.interceptor.InterceptorClass;
import org.vraptor.interceptor.InterceptorDealer;
import org.vraptor.interceptor.InterceptorInstantiationException;
import org.vraptor.url.InternalLogicRequest;
import org.vraptor.util.GettingException;
import org.vraptor.util.SettingException;
import org.vraptor.view.ViewException;

/**
 * The base implementation of logic flow
 * 
 * @author Guilherme Silveira
 * 
 */
public class BasicLogicFlow implements LogicFlow {

	private static final Logger logger = Logger.getLogger(BasicLogicFlow.class);

	private InterceptorDealer interceptorDealer;

	private LinkedList<Interceptor> interceptors = new LinkedList<Interceptor>();

	private LinkedList<InterceptorClass> interceptorsClass;

	private Interceptor lastInterceptor;

	private InterceptorClass lastInterceptorClass;

	private InternalLogicRequest request;

	public BasicLogicFlow(ComponentType componentClass,
			VRaptorExecution engine, ExecuteLogicInterceptor logicInterceptor)
			throws InterceptorInstantiationException {
		logger.debug("Creating new basic logic flow");
		this.interceptorsClass = new LinkedList<InterceptorClass>(
				componentClass.getInterceptors());
		this.request = engine.getRequest();
		this.interceptorDealer = new InterceptorDealer(engine.getController()
				.getIntrospector());
		for (InterceptorClass clazz : this.interceptorsClass) {
			try {
				interceptors.addLast(clazz.newInstance());
			} catch (ComponentInstantiationException e) {
				throw new InterceptorInstantiationException(e.getMessage(), e);
			}
		}
		interceptors.addLast(logicInterceptor);
		interceptorsClass
				.addLast(ComponentClassFactory.INTERCEPTOR_LIST_FACTORY
						.getInterceptorClass(ExecuteLogicInterceptor.class));
	}

	/**
	 * Executes the next step in the flow
	 */
	public void execute() throws ViewException, LogicException {
		logger.debug("Calling execute on flow");
		if (lastInterceptor != null) {
			try {
				interceptorDealer.outject(lastInterceptor,
						lastInterceptorClass, request);
			} catch (GettingException e) {
				throw new LogicException(e.getMessage(), e);
			}
		}
		lastInterceptor = interceptors.removeFirst();
		lastInterceptorClass = interceptorsClass.removeFirst();
		try {
			interceptorDealer.inject(lastInterceptor, lastInterceptorClass,
					request);
		} catch (ComponentInstantiationException e) {
			throw new LogicException(e.getMessage(), e);
		} catch (SettingException e) {
			throw new LogicException(e.getMessage(), e);
		}
		lastInterceptor.intercept(this);
	}

	/**
	 * 
	 * Redirects somewhere in the server
	 * 
	 * @throws LogicException
	 *             some logic exception has hapenned
	 * @see org.vraptor.LogicFlow#redirect(java.lang.String)
	 */
	public void redirect(String url) throws RedirectException {
		try {
			request.getRequest().getRequestDispatcher(url).forward(
					request.getRequest(), request.getResponse());
		} catch (ServletException e) {
			if (e.getRootCause() == null) {
				throw new RedirectException(e.getMessage(), e);
			}
			throw new RedirectException(e.getRootCause().getMessage(), e
					.getRootCause());
		} catch (IOException e) {
			throw new RedirectException(e.getMessage(), e);
		}
	}

}
