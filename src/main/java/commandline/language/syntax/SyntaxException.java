package commandline.language.syntax;

import commandline.command.CommandLineException;
import org.jetbrains.annotations.NotNull;

/**
 * User: gno Date: 28.06.13 Time: 12:35
 */
public class SyntaxException extends CommandLineException {
	private static final long serialVersionUID = - 4899918143627433543L;

	public SyntaxException(@NotNull String message) {
		super(message);
	}

	public SyntaxException(@NotNull String message, @NotNull Throwable cause) {
		super(message, cause);
	}

	public SyntaxException(@NotNull Throwable cause) {
		super(cause);
	}

	public SyntaxException(@NotNull String message, @NotNull Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
