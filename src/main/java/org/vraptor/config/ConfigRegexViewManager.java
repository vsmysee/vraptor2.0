package org.vraptor.config;

import org.vraptor.view.RegexViewManager;
import org.vraptor.webapp.DefaultWebApplication;

/**
 * Configures the view manager
 * 
 * @author Guilherme Silveira
 */
public class ConfigRegexViewManager implements ConfigItem {

	private String regex;

	public <T> ConfigRegexViewManager(String regex) {
		this.regex = regex;
	}

	/**
	 * @return the regex
	 */
	public String getRegex() {
		return regex;
	}

	/**
	 * Registers itself
	 * 
	 * @see org.vraptor.config.ConfigItem#register(org.vraptor.webapp.DefaultWebApplication)
	 */
	public void register(DefaultWebApplication application)
			throws ConfigException {
		application.setViewManager(new RegexViewManager(regex));
	}

	/**
	 * 
	 * @see org.vraptor.config.ConfigItem#isComponent()
	 */
	public boolean isComponent() {
		return false;
	}

	/**
	 * 
	 * @see org.vraptor.config.ConfigItem#isManager()
	 */
	public boolean isManager() {
		return true;
	}

}
