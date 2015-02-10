package commandline.argument.validator;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * User: gno Date: 25.06.13 Time: 16:21
 */
public class ValidationException extends RuntimeException {
	public ValidationException() {
		super();
	}

	public ValidationException(@NotNull String message) {
		super(message);
	}

	public ValidationException(@NotNull String message, @Nullable Throwable cause) {
		super(message, cause);
	}

	public ValidationException(@Nullable Throwable cause) {
		super(cause);
	}

	public ValidationException(@NotNull String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
