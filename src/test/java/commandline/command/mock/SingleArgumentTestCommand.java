package commandline.command.mock;

import commandline.annotation.CliCommand;
import commandline.command.Command;
import commandline.command.ExecutableCommand;
import org.jetbrains.annotations.NotNull;

/**
 * User: gno, Date: 02.08.13 - 15:40
 */
@CliCommand(name = SingleArgumentTestCommand.COMMAND_NAME, description = SingleArgumentTestCommand.COMMAND_DESCRIPTION)
public class SingleArgumentTestCommand extends ExecutableCommand {
	public static final String COMMAND_NAME = "single-test-command";
	public static final String COMMAND_DESCRIPTION = "This is a command description.";
	private boolean executed;

	public SingleArgumentTestCommand() {
		super();
		setExecuted(false);
	}

	public boolean isExecuted() {
		return this.executed;
	}

	private void setExecuted(boolean executed) {
		this.executed = executed;
	}

	@Override
	public void execute(@NotNull Command command) {
		setExecuted(true);
	}
}