package org.vraptor.plugin;

import java.util.Set;

import org.vraptor.component.ComponentType;
import org.vraptor.webapp.DefaultWebApplication;

/**
 * A plugin capable of creating functional testing classes.
 * 
 * @author Guilherme Silveira
 */
public class ProxyTestPlugin implements VRaptorPlugin {

	private final HtmlTestCreator creator = new HtmlTestCreator();

	public void init(DefaultWebApplication application) {
		Set<ComponentType> components = application.getComponentManager()
				.getComponents();
		for (ComponentType component : components) {
			application.getComponentManager()
					.register(wrapComponent(component));
		}
		application.setViewManager(new HtmlProxyTestViewManager(application
				.getViewManager()));
	}

	private ComponentType wrapComponent(ComponentType component) {
		return new ProxyTestComponent(component, creator);
	}

}
