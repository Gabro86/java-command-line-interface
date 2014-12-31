package commandline.exception;

public class ArgumentNullException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ArgumentNullException() {
		super("Transmitted Value should not be NULL.");
	}

	public ArgumentNullException(String message) {
		super(message);
	}

	public ArgumentNullException(String message, Throwable cause) {
		super(message, cause);
	}

	public ArgumentNullException(Throwable cause) {
		super(cause);
	}

	public ArgumentNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
