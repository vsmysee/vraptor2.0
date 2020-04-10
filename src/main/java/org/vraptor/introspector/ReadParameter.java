package org.vraptor.introspector;

import org.vraptor.converter.Converter;
import org.vraptor.util.SettingException;

/**
 * Parameters to be read
 * 
 * @author Guilherme Silveira
 */
public interface ReadParameter {

	/**
	 * The parameter key
	 * 
	 * @return key
	 */
	public String getKey();

	/**
	 * Should it instantiate required fields on the fly?
	 * 
	 * @return true if it should instantiate them
	 */
	public boolean mightCreate();

	/**
	 * Returns the overriden converter class attached to this read parameter
	 * 
	 * @return the converter class
	 */
	public abstract Class<? extends Converter> getOverridenConverter();

	/**
	 * Guarantees it's existence by instantiating itself if needed (or oblied),
	 * connecting to the logic component and returning itself
	 * 
	 * @param component
	 *            the logic component
	 * @return the instantiated object itself
	 * @throws SettingException
	 *             some problem during its creation
	 */
	public abstract Object guaranteeExistence(Object component)
			throws SettingException;

}
