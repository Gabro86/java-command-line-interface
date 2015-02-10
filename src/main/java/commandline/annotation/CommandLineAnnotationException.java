package commandline.annotation;

import commandline.command.CommandLineException;
import org.jetbrains.annotations.NotNull;

/**
 * User: gno, Date: 07.01.2015 - 15:01
 */
public class CommandLineAnnotationException extends CommandLineException {
	public CommandLineAnnotationException() {
		super();
	}

	public CommandLineAnnotationException(@NotNull String message) {
		super(message);
	}

	public CommandLineAnnotationException(@NotNull String message, @NotNull Throwable cause) {
		super(message, cause);
	}

	public CommandLineAnnotationException(@NotNull Throwable cause) {
		super(cause);
	}

	public CommandLineAnnotationException(@NotNull String message, @NotNull Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
