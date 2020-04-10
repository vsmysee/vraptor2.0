package org.vraptor.config;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * A config component xml converter
 * 
 * @author Guilherme Silveira
 */
public class ConfigComponentConverter implements Converter {

	/**
	 * Can it convert this class type?
	 * 
	 * @see com.thoughtworks.xstream.converters.Converter#canConvert(java.lang.Class)
	 */
	public boolean canConvert(Class clazz) {
		return clazz.equals(ConfigComponent.class);
	}

	/**
	 * Translates an object in the space to a xml string
	 * 
	 * @see com.thoughtworks.xstream.converters.Converter#marshal(java.lang.Object,
	 *      com.thoughtworks.xstream.io.HierarchicalStreamWriter,
	 *      com.thoughtworks.xstream.converters.MarshallingContext)
	 */
	public void marshal(Object value, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		ConfigComponent object = (ConfigComponent) value;
		writer.setValue(object.getFactoryClass().getName());
	}

	/**
	 * Translates xml to an object
	 * 
	 * @see com.thoughtworks.xstream.converters.Converter#unmarshal(com.thoughtworks.xstream.io.HierarchicalStreamReader,
	 *      com.thoughtworks.xstream.converters.UnmarshallingContext)
	 */
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		try {
			return new ConfigComponent(Class.forName(reader.getValue()));
		} catch (ClassNotFoundException e) {
			throw new ConversionException(e.getMessage(), e);
		}
	}

}
