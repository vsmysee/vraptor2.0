package org.vraptor;

import org.vraptor.scope.Context;
import org.vraptor.scope.LogicContext;
import org.vraptor.scope.RequestContext;
import org.vraptor.scope.SessionContext;


/**
 * An interface to extract from the url which action and component should be
 * called and contains a logic request information.
 * 
 * @author Guilherme Silveira
 */
public interface LogicRequest {

	/**
	 * @return Returns the actionName.
	 */
	String getLogicName();

	/**
	 * @return Returns the componentName.
	 */
	String getComponentName();

	/**
	 * Returns a wrapper for the application context
	 * 
	 * @return the context
	 */
	Context getApplicationContext();

	/**
	 * Returns a wrapper for the user session
	 * 
	 * @return the context
	 */
	SessionContext getSessionContext();

	/**
	 * Returns a wrapper for the request session
	 * 
	 * @return the context
	 */
	RequestContext getRequestContext();

	/**
	 * Returns a wrapper for the logic context
	 * 
	 * @return the context
	 */
	LogicContext getLogicContext();

}