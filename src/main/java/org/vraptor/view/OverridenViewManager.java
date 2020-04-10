package org.vraptor.view;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.vraptor.LogicRequest;

/**
 * Overriden view manager checks if the view was overriden and returns it.
 * Otherwise it returns the default view that its internal view manager would
 * return.
 * 
 * @author Guilherme Silveira
 */
public class OverridenViewManager implements ViewManager {

	private static final Logger logger = Logger
			.getLogger(OverridenViewManager.class);

	private final Map<String, String> views = new HashMap<String, String>();

	private final ViewManager internalManager;

	/**
	 * Creates the overriden view manager with a default view manager
	 * 
	 * @param viewManager
	 *            the default view manager
	 */
	public OverridenViewManager(ViewManager viewManager) {
		super();
		this.internalManager = viewManager;
	}

	/**
	 * Register a new view
	 * 
	 * @param key
	 *            key
	 * @param value
	 *            value
	 */
	public void overrideView(String key, String value) {
		logger.info(String.format("Overriding view %s --> %s", key, value));
		this.views.put(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vraptor.view.ViewManager#forward(org.vraptor.LogicRequest,
	 *      java.lang.String, javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void forward(LogicRequest logicRequest, String result,
			HttpServletRequest request, HttpServletResponse response)
			throws ViewException {
		// if it is overriden, overrides the forward
		String key = logicRequest.getComponentName() + "."
				+ logicRequest.getLogicName() + "." + result;
		if (this.views.containsKey(key)) {
			logger.debug("overriden view found " + key);
			String forward = this.views.get(key);
			internalManager.directForward(logicRequest, result, request,
					response, forward);
		} else {
			internalManager.forward(logicRequest, result, request, response);
		}
	}

	public void directForward(LogicRequest logicRequest, String result,
			HttpServletRequest request, HttpServletResponse response,
			String forwardUrl) throws ViewException {
		internalManager.directForward(logicRequest, result, request, response,
				forwardUrl);
	}

}
