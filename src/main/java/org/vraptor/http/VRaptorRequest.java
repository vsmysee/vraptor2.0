package org.vraptor.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.vraptor.LogicRequest;
import org.vraptor.core.VRaptorController;
import org.vraptor.factory.FactoryException;

/**
 * A simple request wrapper for dealing with variable lookup.
 * 
 * @author Guilherme Silveira
 */
public class VRaptorRequest extends HttpServletRequestWrapper {

	private LogicRequest context;

	private VRaptorController controller;

	/**
	 * Constructor for this wrapper
	 * 
	 * @param req
	 *            request
	 * @param controller
	 *            the controller
	 */
	public VRaptorRequest(HttpServletRequest req, VRaptorController controller) {
		super(req);
		this.controller = controller;
	}

	/**
	 * Overriding getAttribute
	 * 
	 * @see javax.servlet.ServletRequestWrapper#getAttribute(java.lang.String)
	 */
	@Override
	public Object getAttribute(String key) {
		if (super.getAttribute(key) != null
				|| (!controller.getWebApplication().getFactoryManager()
						.canInstantiate(key))) {
			return super.getAttribute(key);
		}
		return instantiate(key);
	}

	/**
	 * Instantiates the desired key
	 * 
	 * @param key
	 *            desired key
	 * @return the object
	 */
	private Object instantiate(String key) {
		try {
			return controller.getWebApplication().getFactoryManager()
					.instantiate(key, this.context,
							this.controller.getIntrospector());
		} catch (FactoryException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Changes the current context
	 * 
	 * @param context
	 *            context
	 */
	public void setCurrentRequest(LogicRequest context) {
		this.context = context;
	}

}
