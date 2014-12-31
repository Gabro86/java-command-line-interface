package commandline.language.parser.argument;

import commandline.command.CommandLineException;

/**
 * User: gno, Date: 01.08.13 - 11:25
 */
public class ArgumentParseException extends CommandLineException {
	private static final long serialVersionUID = - 383333766055884523L;

	public ArgumentParseException(String message) {
		super(message);
	}

	public ArgumentParseException(Throwable cause) {
		super(cause);
	}

	public ArgumentParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ArgumentParseException(String message, Throwable cause) {
		super(message, cause);
	}
}
