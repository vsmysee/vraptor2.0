package org.vraptor.url;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Responds to uri requests by giving back a logic request: a component and
 * action that should be executed.
 * 
 * @author Guilherme Silveira
 */
public interface URLManager {

	/**
	 * Returns the logic request
	 * 
	 * @param context
	 *            the servlet context
	 * 
	 * @return the logic request associated to this request uri
	 * @throws InvalidURLException
	 *             invalid url was requested
	 */
	public InternalLogicRequest getLogicRequest(HttpServletRequest req,
			HttpServletResponse res, ServletContext context)
			throws InvalidURLException;

}
