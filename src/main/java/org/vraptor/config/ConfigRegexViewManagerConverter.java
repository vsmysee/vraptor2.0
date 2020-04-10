package org.vraptor.config;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * A regex view manager converter
 * 
 * @author Guilherme Silveira
 */
public class ConfigRegexViewManagerConverter implements Converter {

	/**
	 * Can it convert this class type?
	 * 
	 * @see com.thoughtworks.xstream.converters.Converter#canConvert(java.lang.Class)
	 */
	public boolean canConvert(Class clazz) {
		return clazz.equals(ConfigRegexViewManager.class);
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
		ConfigRegexViewManager object = (ConfigRegexViewManager) value;
		writer.setValue(object.getRegex());
	}

	/**
	 * Translates xml to an object
	 * 
	 * @see com.thoughtworks.xstream.converters.Converter#unmarshal(com.thoughtworks.xstream.io.HierarchicalStreamReader,
	 *      com.thoughtworks.xstream.converters.UnmarshallingContext)
	 */
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		return new ConfigRegexViewManager(reader.getValue());
	}

}
