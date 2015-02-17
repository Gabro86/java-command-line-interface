package commandline.command.mock;

import commandline.annotation.CliCommand;
import commandline.command.Command;
import commandline.command.ExecutableCommand;
import org.jetbrains.annotations.NotNull;

/**
 * User: gno, Date: 02.08.13 - 15:41
 */
@CliCommand(name = "", description = "")
public class NoCommandNameTestCommand extends ExecutableCommand {
	public NoCommandNameTestCommand() {
		super();
	}

	@Override
	public void execute(@NotNull Command command) {
	}
}