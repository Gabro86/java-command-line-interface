package commandline.command;

import org.jetbrains.annotations.NotNull;

public class CommandLineException extends RuntimeException {
	private static final long serialVersionUID = 3779918911788472991L;

	public CommandLineException() {
		super();
	}

	public CommandLineException(@NotNull String message) {
		super(message);
	}

	public CommandLineException(@NotNull Throwable cause) {
		super(cause);
	}

	public CommandLineException(@NotNull String message, @NotNull Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CommandLineException(@NotNull String message, @NotNull Throwable cause) {
		super(message, cause);
	}
}
