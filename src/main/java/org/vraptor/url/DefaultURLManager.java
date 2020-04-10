package org.vraptor.url;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.vraptor.scope.DefaultLogicRequest;

/**
 * The default url manager translates url data.
 * 
 * @author Guilherme Silveira
 */
public class DefaultURLManager implements URLManager {

	private static final Logger logger = Logger
			.getLogger(DefaultURLManager.class);

	public InternalLogicRequest getLogicRequest(HttpServletRequest req,
			HttpServletResponse res, ServletContext context)
			throws InvalidURLException {
		String uri = req.getRequestURI();
		logger.debug("Requested url (request wrapped): " + uri);
		uri = uri.substring(uri.lastIndexOf('/') + 1);
		logger.debug("requested uri: " + uri);
		int lastPosition = uri.lastIndexOf('.');
		// no extension found!!! error!!!
		if (lastPosition == -1) {
			throw new InvalidURLException("Unable to deal with url " + uri);
		}
		int position = uri.indexOf('.');
		if (position == lastPosition) {
			throw new InvalidURLException("Unable to deal with desired url: "
					+ uri);
		}
		String componentName = uri.substring(0, position);
		String actionName = uri.substring(position + 1, lastPosition);
		return new DefaultLogicRequest(componentName, actionName, req, res,
				context);
	}

}
