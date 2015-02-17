package commandline.command.mock;

import commandline.annotation.CliArgument;
import commandline.annotation.CliCommand;
import commandline.command.Command;
import commandline.command.ExecutableCommand;
import org.jetbrains.annotations.NotNull;

/**
 * User: gno, Date: 02.08.13 - 15:40
 */
@CliCommand(name = OverriddenAnnotationsTestCommand.COMMAND_NAME, description = OverriddenAnnotationsTestCommand.COMMAND_DESCRIPTION)
public class OverriddenAnnotationsTestCommand extends ExecutableCommand {
	public static final String COMMAND_NAME = "overridden-annotations-test-command";
	public static final String COMMAND_DESCRIPTION = "This is a overridden command description.";
	public static final String ARGUMENT_OVERRIDDEN_LONG_NAME = "help2";

	public OverriddenAnnotationsTestCommand() {
		super();
	}

	@CliArgument(shortName = ARGUMENT_HELP_SHORT_NAME, longName = ARGUMENT_OVERRIDDEN_LONG_NAME,
			obligatory = ARGUMENT_HELP_OBLIGATORY,
			defaultValue = ARGUMENT_HELP_DEFAULT_VALUE, examples = {ARGUMENT_HELP_EXAMPLE1, ARGUMENT_HELP_EXAMPLE2},
			description = ARGUMENT_HELP_DESCRIPTION)
	@Override
	public void setShowHelp(boolean showHelp) {
		super.setShowHelp(showHelp);
	}

	@Override
	public void execute(@NotNull Command command) {
	}
}