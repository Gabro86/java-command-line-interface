package commandline.language.parser;

import commandline.command.CommandLineException;
import org.jetbrains.annotations.NotNull;

/**
 * User: gno, Date: 01.08.13 - 11:25
 */
public class ArgumentParseException extends CommandLineException {
	private static final long serialVersionUID = -383333766055884523L;

	public ArgumentParseException(@NotNull String message) {
		super(message);
	}

	public ArgumentParseException(@NotNull Throwable cause) {
		super(cause);
	}

	public ArgumentParseException(@NotNull String message, @NotNull Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ArgumentParseException(@NotNull String message, @NotNull Throwable cause) {
		super(message, cause);
	}
}
