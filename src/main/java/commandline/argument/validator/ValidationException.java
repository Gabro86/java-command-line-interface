package commandline.argument.validator;

import org.jetbrains.annotations.Nullable;

/**
 * User: gno Date: 25.06.13 Time: 16:21
 */
public class ValidationException extends RuntimeException {
	public ValidationException() {
		super();
	}

	public ValidationException(@Nullable String message) {
		super(message);
	}

	public ValidationException(@Nullable String message, @Nullable Throwable cause) {
		super(message, cause);
	}

	public ValidationException(@Nullable Throwable cause) {
		super(cause);
	}

	public ValidationException(@Nullable String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
