package commandline.argument.validator;

/**
 * User: gno Date: 25.06.13 Time: 16:21
 */
public class ValidationException extends RuntimeException {
	private static final long serialVersionUID = - 1654407075942016610L;

	public ValidationException() {
		super();
	}

	public ValidationException(String message) {
		super(message);
	}

	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValidationException(Throwable cause) {
		super(cause);
	}

	public ValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
