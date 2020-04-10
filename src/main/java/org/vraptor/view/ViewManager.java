package org.vraptor.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.vraptor.LogicRequest;

/**
 * A view manager decides where to go after an action has been executed.
 * 
 * @author Guilherme Silveira
 */
public interface ViewManager {

	/**
	 * Forwards the user based on some specific result
	 */
	public void forward(LogicRequest logicRequest, String result,
			HttpServletRequest request, HttpServletResponse response)
			throws ViewException;

	/**
	 * Directly forwards to the selected forward url.
	 */
	public void directForward(LogicRequest logicRequest, String result,
			HttpServletRequest request, HttpServletResponse response,
			String forwardUrl) throws ViewException;

}
