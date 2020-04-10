package org.vraptor.webapp;

import java.util.List;

import org.vraptor.plugin.VRaptorPlugin;

public interface PluginManager {

	public void register(VRaptorPlugin plugin);

	public List<VRaptorPlugin> getPlugins();

	public void init(DefaultWebApplication application);

}
