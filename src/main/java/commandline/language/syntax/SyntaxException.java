package commandline.language.syntax;

import commandline.command.CommandLineException;

/**
 * User: gno Date: 28.06.13 Time: 12:35
 */
public class SyntaxException extends CommandLineException {
	private static final long serialVersionUID = - 4899918143627433543L;

	public SyntaxException(String message) {
		super(message);
	}

	public SyntaxException(String message, Throwable cause) {
		super(message, cause);
	}

	public SyntaxException(Throwable cause) {
		super(cause);
	}

	public SyntaxException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
