package org.vraptor.jpath;

import java.lang.reflect.Type;
import org.vraptor.util.SettingException;

/**
 * A generic set dealer
 * 
 * @author Guilherme Silveira
 */
public interface SetDealer {

	/**
	 * Guarantees that this object has the desired length, instantiating a new one if needed and returning it.
	 * @param object	the current object
	 * @param length	the desired length
	 * @param mayCreate	may instantiate objects inside this one
	 * @param type		type to be instantiated
	 * @return	the object itself or a new resized object
	 * @throws SettingException	setter problems
	 */
	Object resize(Object object, int length, boolean mayCreate,
			Type type) throws SettingException;

	Object getPosition(Object object, int arrayPosition,
			boolean mayCreate) throws SettingException;

}
