package org.vraptor.plugin;

import java.util.ArrayList;
import java.util.List;

import org.vraptor.webapp.DefaultWebApplication;
import org.vraptor.webapp.PluginManager;

public class DefaultPluginManager implements PluginManager {

	private List<VRaptorPlugin> plugins = new ArrayList<VRaptorPlugin>();

	public DefaultPluginManager() {
		super();
	}

	public void register(VRaptorPlugin pluginClass) {
		plugins.add(pluginClass);
	}

	public List<VRaptorPlugin> getPlugins() {
		return plugins;
	}

	public void init(DefaultWebApplication application) {
		for (VRaptorPlugin plugin : this.plugins) {
			plugin.init(application);
		}
	}

}
