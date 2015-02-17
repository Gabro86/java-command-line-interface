package commandline.command.mock;

import commandline.annotation.CliArgument;
import commandline.annotation.CliCommand;
import commandline.command.Command;
import commandline.command.ExecutableCommand;
import org.jetbrains.annotations.NotNull;

/**
 * User: gno, Date: 02.08.13 - 15:40
 */
@CliCommand(name = InheritedArgumentTestCommand.COMMAND_NAME, description = InheritedArgumentTestCommand.COMMAND_DESCRIPTION)
public class InheritedArgumentTestCommand extends ExecutableCommand {
	public static final String COMMAND_NAME = "inherited-argument-test-command";
	public static final String COMMAND_DESCRIPTION = "This is a command description.";
	public static final String ARGUMENT_TEST_LONG_NAME = "test-argument";

	public InheritedArgumentTestCommand() {
		super();
	}

	@CliArgument(shortName = "t", longName = ARGUMENT_TEST_LONG_NAME, obligatory = false, isDefaultValueNull = true,
			description = "This is a test argument.", examples = {"example1"})
	public void setTestArgument(String value) {
	}

	@Override
	public void execute(@NotNull Command command) {
	}
}