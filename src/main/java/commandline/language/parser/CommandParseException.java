package commandline.language.parser;

import commandline.command.CommandLineException;
import org.jetbrains.annotations.Nullable;

/**
 * User: gno, Date: 06.01.2015 - 17:04
 */
public class CommandParseException extends CommandLineException {
	public CommandParseException() {
		super();
	}

	public CommandParseException(@Nullable String message) {
		super(message);
	}

	public CommandParseException(@Nullable Throwable cause) {
		super(cause);
	}

	public CommandParseException(@Nullable String message, @Nullable Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CommandParseException(@Nullable String message, @Nullable Throwable cause) {
		super(message, cause);
	}
}
