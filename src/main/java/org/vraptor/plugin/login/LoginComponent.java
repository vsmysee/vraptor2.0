package org.vraptor.plugin.login;

import org.vraptor.annotations.Component;
import org.vraptor.annotations.Read;

/**
 * A basic authorization and authentication component
 * 
 * @author Guilherme Silveira
 */
@Component("login")
public class LoginComponent {

	/**
	 * What has failed
	 */
	@Read
	private String what;

	/**
	 * Failed...
	 * 
	 * @return what has failed
	 * 
	 */
	public String failed() {
		if(what.equals("authentication")) {
			// go back to login form
			
		}
		// does nothing
		return what;
	}
	
}
