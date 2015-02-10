package commandline.language.syntax;

import commandline.command.CommandLineException;
import org.jetbrains.annotations.Nullable;

/**
 * User: gno Date: 28.06.13 Time: 12:35
 */
public class SyntaxException extends CommandLineException {
	private static final long serialVersionUID = -4899918143627433543L;

	public SyntaxException(@Nullable String message) {
		super(message);
	}

	public SyntaxException(@Nullable String message, @Nullable Throwable cause) {
		super(message, cause);
	}

	public SyntaxException(@Nullable Throwable cause) {
		super(cause);
	}

	public SyntaxException(@Nullable String message, @Nullable Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
