package main.java.com.booksaw.Engine2D.exception;

import java.security.InvalidParameterException;

/**
 * This is an error which is thrown when the provided class does not extend the
 * required superclass
 * 
 * @author booksaw
 *
 */
public class ClassTypeMismatchException extends InvalidParameterException {

	private static final long serialVersionUID = 5900699060120300543L;

	public ClassTypeMismatchException(Class<?> superclass) {
		super("The provided class does not extend the superclass" + superclass);
	}

}
