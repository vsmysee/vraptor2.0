package org.vraptor.test;

import org.vraptor.config.ConfigException;
import org.vraptor.core.ControllerFactory;
import org.vraptor.core.VRaptorController;

/**
 * A test engine.
 * 
 * @author Guilherme Silveira
 * 
 */
public class TestEngine {

	private final VRaptorController controller;

	private TestEngine() throws TestConfigException {
		try {
			this.controller = ControllerFactory
					.configure(new MockedServletContext());
		} catch (ConfigException e) {
			throw new TestConfigException(e);
		}
	}

	/**
	 * Loads vraptor configuration files and setups the engine
	 * 
	 * @return the new engine
	 * @throws ConfigException
	 *             some configuration problem was found
	 */
	public static TestEngine createEngine() throws TestConfigException {
		return new TestEngine();
	}

	/**
	 * Creates a new test session
	 * 
	 * @return a test session
	 */
	public TestSession createSession() {
		return new DefaultTestSession(controller);
	}

}
