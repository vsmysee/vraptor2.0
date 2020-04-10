package org.vraptor.scope;

import org.apache.log4j.Logger;
import org.vraptor.LogicRequest;

/**
 * A helper for scope localization.
 * 
 * @author Guilherme Silveira
 */
public class ScopeLocator {

	private static final Logger logger = Logger.getLogger(ScopeLocator.class);

	public Context get(ScopeType type, LogicRequest request) {
		if(type.equals(ScopeType.REQUEST)) {
			return request.getRequestContext();
		} else if(type.equals(ScopeType.REQUEST)) {
			return request.getRequestContext();
		} else if(type.equals(ScopeType.REQUEST)) {
			return request.getRequestContext();
		} else if(type.equals(ScopeType.REQUEST)) {
			return request.getRequestContext();
		} else {
			logger.fatal("Fatal error in vraptor: context exists but has not been configured! " + type.name());
			throw new RuntimeException("Fatal error in vraptor: context exists but has not been configured!");
		}
	}
}
