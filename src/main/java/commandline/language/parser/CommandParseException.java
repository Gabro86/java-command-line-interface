package commandline.language.parser;

import commandline.command.CommandLineException;
import org.jetbrains.annotations.NotNull;

/**
 * User: gno, Date: 06.01.2015 - 17:04
 */
public class CommandParseException extends CommandLineException {
	public CommandParseException() {
		super();
	}

	public CommandParseException(@NotNull String message) {
		super(message);
	}

	public CommandParseException(@NotNull Throwable cause) {
		super(cause);
	}

	public CommandParseException(@NotNull String message, @NotNull Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CommandParseException(@NotNull String message, @NotNull Throwable cause) {
		super(message, cause);
	}
}
