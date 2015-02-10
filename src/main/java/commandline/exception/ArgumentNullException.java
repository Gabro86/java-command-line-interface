package commandline.exception;

import org.jetbrains.annotations.NotNull;

public class ArgumentNullException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ArgumentNullException() {
		super("Transmitted Value should not be NULL.");
	}

	public ArgumentNullException(@NotNull String message) {
		super(message);
	}

	public ArgumentNullException(@NotNull String message, @NotNull Throwable cause) {
		super(message, cause);
	}

	public ArgumentNullException(@NotNull Throwable cause) {
		super(cause);
	}

	public ArgumentNullException(@NotNull String message, @NotNull Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
