package org.vraptor.plugin.login;

import org.vraptor.Interceptor;
import org.vraptor.LogicException;
import org.vraptor.LogicFlow;
import org.vraptor.RedirectException;
import org.vraptor.view.ViewException;

/**
 * An interceptor capable of redirecting flow in case an authorization or
 * authentication exception occurs
 * 
 * @author Guilherme Silveira
 */
public class AuthorizationAuthenticationInterceptor implements Interceptor {

	/**
	 * Intercepts and in case of a authentication, authorization exception
	 * redirects to some pre-defined logic
	 * 
	 * @see org.vraptor.Interceptor#intercept(org.vraptor.LogicFlow)
	 */
	public void intercept(LogicFlow flow) throws LogicException, ViewException {
		try {
			flow.execute();
		} catch (LogicException ex) {
			Throwable cause = ex.getCause();
			try {
				if (cause instanceof AuthenticationException) {
					// login/failed.authentication.jsp --> login form
					flow.redirect("/login.form.logic?what=authentication");
				} else if (cause instanceof AuthorizationException) {
					flow.redirect("/login.failed.logic?what=authorization");
				}
			} catch (RedirectException e) {
				throw new LogicException(e.getMessage(), e.getCause());
			}
		}
	}

}
