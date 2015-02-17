package commandline.command.mock;

import commandline.command.Command;
import commandline.command.ExecutableCommand;
import org.jetbrains.annotations.NotNull;

/**
 * User: gno, Date: 16.02.2015 - 15:37
 */
public class NoCliCommandAnnotationTestCommand extends ExecutableCommand {
	public NoCliCommandAnnotationTestCommand() {
		super();
	}

	@Override
	public void execute(@NotNull Command command) {
	}
}
