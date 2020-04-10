package org.vraptor.config;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * A config converter xml converter (weird name...)
 * 
 * @author Guilherme Silveira
 */
public class ConfigConverterConverter implements Converter {

	/**
	 * Can it convert this class type?
	 * 
	 * @see com.thoughtworks.xstream.converters.Converter#canConvert(java.lang.Class)
	 */
	public boolean canConvert(Class clazz) {
		return clazz.equals(ConfigConverter.class);
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
		ConfigConverter object = (ConfigConverter) value;
		writer.setValue(object.getConverterClass().getName());
	}

	/**
	 * Translates xml to an object
	 * 
	 * @see com.thoughtworks.xstream.converters.Converter#unmarshal(com.thoughtworks.xstream.io.HierarchicalStreamReader,
	 *      com.thoughtworks.xstream.converters.UnmarshallingContext)
	 */
	@SuppressWarnings("unchecked")
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		try {
			Class<? extends Converter> clazz = (Class<? extends Converter>) Class
					.forName(reader.getValue());
			return new ConfigConverter(clazz);
		} catch (ClassCastException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			throw new ConversionException(e.getMessage(), e);
		}
	}

}
