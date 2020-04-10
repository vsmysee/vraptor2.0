package org.vraptor.webapp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.vraptor.LogicRequest;
import org.vraptor.component.ComponentClassFactory;
import org.vraptor.component.ComponentManager;
import org.vraptor.component.ComponentNotFoundException;
import org.vraptor.component.ComponentType;
import org.vraptor.component.InvalidComponentException;
import org.vraptor.component.LogicMethod;

/**
 * A simple implementation of a component manager
 * 
 * @author Guilherme Silveira
 */
public class DefaultComponentManager implements ComponentManager {

	private static final Logger logger = Logger
			.getLogger(DefaultComponentManager.class);

	private Map<String, ComponentType> actions = new HashMap<String, ComponentType>();

	private ComponentClassFactory componentClassFactory = new ComponentClassFactory();

	/**
	 * 
	 * @see org.vraptor.component.ComponentManager#register(java.lang.Class)
	 */
	public void register(Class clazz) throws InvalidComponentException {
		register(this.componentClassFactory.getComponentClass(clazz));
	}

	public ComponentType getComponent(LogicRequest request)
			throws ComponentNotFoundException {
		String completeName = request.getComponentName() + "."
				+ request.getLogicName();
		ComponentType component = this.actions.get(completeName);
		if (component == null) {
			throw new ComponentNotFoundException(String.format(
					"Component %s not found", completeName));
		}
		return component;
	}

	public Set<ComponentType> getComponents() {
		return new HashSet<ComponentType>(actions.values());
	}

	public void register(ComponentType type) {
		logger.debug("Registering component " + type.getName());
		for (LogicMethod action : type.getLogics()) {
			this.actions.put(String.format("%s.%s", type.getName(), action
					.getName()), type);
		}
		logger.debug("Component " + type.getName() + " ok");
	}

}
