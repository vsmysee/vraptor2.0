package org.vraptor.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.vraptor.LogicRequest;

/**
 * Regex based view manager.
 * 
 * @author Guilherme Silveira
 */
public class RegexViewManager implements ViewManager {

	private static final Logger logger = Logger
			.getLogger(RegexViewManager.class);

	private String replacement;

	private String regex = ("(.*);(.*);(.*)");

	/**
	 * Instantiates the regex view manager with this replacement string.
	 * 
	 * @param replacement
	 *            the replacement string
	 */
	public RegexViewManager(String replacement) {
		this.replacement = replacement;
	}

	/**
	 * 
	 * @see org.vraptor.view.ViewManager#getForward(org.vraptor.LogicRequest,
	 *      java.lang.String)
	 */
	private String getForward(LogicRequest request, String result)
			throws ViewException {
		String value = request.getComponentName() + ";"
				+ request.getLogicName() + ";" + result;
		return value.replaceAll(regex, replacement);

	}

	public void forward(LogicRequest logicRequest, String result,
			HttpServletRequest request, HttpServletResponse response)
			throws ViewException {
		String forward = getForward(logicRequest, result);
		directForward(logicRequest, result, request, response, forward);
	}

	public void directForward(LogicRequest logicRequest, String result,
			HttpServletRequest request, HttpServletResponse response,
			String forwardUrl) throws ViewException {
		logger.debug("Redirecting to: " + forwardUrl);
		try {
			request.getRequestDispatcher(forwardUrl).forward(request, response);
		} catch (ServletException ex) {
			if (ex.getCause() != null) {
				throw new ViewException(ex.getCause().getMessage(), ex
						.getCause());
			}
			throw new ViewException(ex.getMessage(), ex);
		} catch (IOException ex) {
			throw new ViewException(ex.getMessage(), ex);
		}
	}

}
