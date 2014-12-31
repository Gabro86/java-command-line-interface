package commandline.command;

public class CommandLineException extends RuntimeException {
	private static final long serialVersionUID = 3779918911788472991L;

	public CommandLineException() {
		super();
	}

	public CommandLineException(String message) {
		super(message);
	}

	public CommandLineException(Throwable cause) {
		super(cause);
	}

	public CommandLineException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CommandLineException(String message, Throwable cause) {
		super(message, cause);
	}
}
