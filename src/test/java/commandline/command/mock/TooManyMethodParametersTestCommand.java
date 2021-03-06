package commandline.command.mock;

import commandline.annotation.CliArgument;
import commandline.argument.validator.DefaultArgumentValidator;
import commandline.command.Command;
import commandline.command.ExecutableCommand;
import commandline.language.parser.specific.StringArgumentParser;
import org.jetbrains.annotations.NotNull;

/**
 * User: gno, Date: 02.08.13 - 15:41
 */
public class TooManyMethodParametersTestCommand extends ExecutableCommand {
	public TooManyMethodParametersTestCommand() {
		super();
	}

	@SuppressWarnings("UnusedParameters")
	@CliArgument(shortName = "t", longName = "test-key", validator = DefaultArgumentValidator.class,
			parser = StringArgumentParser.class, obligatory = true, examples = {ValidTestCommand.ARGUMENT_EXAMPLE},
			description = ValidTestCommand.COMMAND_DESCRIPTION)
	public void method(String a, String b) {
	}

	@Override
	public void execute(@NotNull Command command) {
	}
}