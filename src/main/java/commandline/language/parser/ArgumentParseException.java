package commandline.language.parser;

import commandline.command.CommandLineException;
import org.jetbrains.annotations.Nullable;

/**
 * User: gno, Date: 01.08.13 - 11:25
 */
public class ArgumentParseException extends CommandLineException {
	private static final long serialVersionUID = -383333766055884523L;

	public ArgumentParseException(@Nullable String message) {
		super(message);
	}

	public ArgumentParseException(@Nullable Throwable cause) {
		super(cause);
	}

	public ArgumentParseException(@Nullable String message, @Nullable Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ArgumentParseException(@Nullable String message, @Nullable Throwable cause) {
		super(message, cause);
	}
}
