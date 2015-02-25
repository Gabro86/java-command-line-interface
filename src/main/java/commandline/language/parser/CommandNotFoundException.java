package commandline.language.parser;

import commandline.command.CommandLineException;
import org.jetbrains.annotations.Nullable;

/**
 * User: gno, Date: 25.02.2015 - 14:05
 */
public class CommandNotFoundException extends CommandLineException {
	@Nullable
	private final String commandName;

	public CommandNotFoundException(@Nullable String commandName) {
		//commandName can be null
		this.commandName = commandName;
	}

	public CommandNotFoundException(@Nullable String message, @Nullable String commandName) {
		super(message);
		//commandName can be null
		this.commandName = commandName;
	}

	public CommandNotFoundException(@Nullable String message, @Nullable Throwable cause, @Nullable String commandName) {
		super(message, cause);
		//commandName can be null
		this.commandName = commandName;
	}

	@Nullable
	public String getCommandName() {
		return this.commandName;
	}
}
