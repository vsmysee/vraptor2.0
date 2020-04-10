package org.vraptor.component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Represents a field annotated
 * 
 * @author Guilherme Silveira
 * @param <T>
 *            the type of annotation which shall be used
 */
public class FieldAnnotation<T extends Annotation> {

	private T annotation;

	private Field field;

	/**
	 * Simple constructor
	 * @param annotation	annotation
	 * @param f				field
	 */
	public FieldAnnotation(T annotation, Field f) {
		this.annotation = annotation;
		this.field = f;
	}

	/**
	 * @return Returns the annotation.
	 */
	public T getAnnotation() {
		return this.annotation;
	}

	/**
	 * @return Returns the field.
	 */
	public Field getField() {
		return this.field;
	}

}
