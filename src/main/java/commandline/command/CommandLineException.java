package commandline.command;

import org.jetbrains.annotations.Nullable;

public class CommandLineException extends RuntimeException {
	private static final long serialVersionUID = 3779918911788472991L;

	public CommandLineException() {
		super();
	}

	public CommandLineException(@Nullable String message) {
		super(message);
	}

	public CommandLineException(@Nullable Throwable cause) {
		super(cause);
	}

	public CommandLineException(@Nullable String message, @Nullable Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CommandLineException(@Nullable String message, @Nullable Throwable cause) {
		super(message, cause);
	}
}
