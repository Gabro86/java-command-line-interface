package commandline.argument.validator;

import org.jetbrains.annotations.Nullable;

/**
 * This exception class is thrown when an error occurred while validating a command line argument.
 */
public class ValidationException extends RuntimeException {
	/**
	 * Creates an instance of this class.
	 */
	public ValidationException() {
		super();
	}

	/**
	 * Creates an instance of this class.
	 * @param message Message of the validation error that occurred.
	 */
	public ValidationException(@Nullable String message) {
		super(message);
	}

	/**
	 * Creates an instance of this class.
	 * @param message Message of the validation error that occurred.
	 * @param cause Source exception instance that was thrown when the error occurred.
	 */
	public ValidationException(@Nullable String message, @Nullable Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates an instance of this class.
	 * @param cause Source exception instance that was thrown when the error occurred.
	 */
	public ValidationException(@Nullable Throwable cause) {
		super(cause);
	}
}
