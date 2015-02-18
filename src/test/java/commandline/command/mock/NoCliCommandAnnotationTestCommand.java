package commandline.command.mock;

import commandline.command.Command;
import commandline.command.ExecutableCommand;
import org.jetbrains.annotations.NotNull;

/**
 * User: gno, Date: 16.02.2015 - 15:37
 */
public class NoCliCommandAnnotationTestCommand extends ExecutableCommand {
	public static String COMMAND_NAME = "NoCliCommandAnnotationTestCommand";
	public static String COMMAND_DESCRIPTION = "This is a command description.";

	public NoCliCommandAnnotationTestCommand() {
		super();
	}

	@Override
	public void execute(@NotNull Command command) {
	}
}
