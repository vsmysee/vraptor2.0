package org.vraptor.converter.basic;

import org.apache.log4j.Logger;
import org.vraptor.converter.SimpleConverterManager;

/**
 * Utility for registering all primitive and primitive-wrappers converters for
 * xpathing parameters
 * 
 * @author Guilherme Silveira
 */
public class BasicConverters {

	private static final Logger logger = Logger
			.getLogger(BasicConverters.class);

	/**
	 * Registers those basic converters
	 * 
	 */
	public static void register(SimpleConverterManager repository) {
		BasicConverters.logger.debug("Registering the basic converters");
		repository.register(new SimpleLongConverter());
		repository.register(new PrimitiveLongConverter());
		repository.register(new SimpleIntegerConverter());
		repository.register(new PrimitiveIntConverter());
		repository.register(new SimpleFloatConverter());
		repository.register(new PrimitiveFloatConverter());
		repository.register(new SimpleDoubleConverter());
		repository.register(new PrimitiveDoubleConverter());
		repository.register(new StringConverter());
		repository.register(new SimpleBooleanConverter());
		repository.register(new PrimitiveBooleanConverter());
		repository.register(new SimpleShortConverter());
		repository.register(new PrimitiveShortConverter());
		repository.register(new SimpleByteConverter());
		repository.register(new PrimitiveByteConverter());
		repository.register(new SimpleCharacterConverter());
		repository.register(new PrimitiveCharConverter());
		repository.register(new LocaleCalendarDateConverter());
		repository.register(new EnumConverter());
	}

}
