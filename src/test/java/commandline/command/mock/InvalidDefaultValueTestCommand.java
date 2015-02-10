package commandline.command.mock;

import commandline.annotation.CliArgument;
import commandline.argument.validator.DefaultArgumentValidator;
import commandline.language.parser.specific.StringArgumentParser;

/**
 * User: gno, Date: 02.08.13 - 15:41
 */
@SuppressWarnings("EmptyMethod")
public class InvalidDefaultValueTestCommand {
	public InvalidDefaultValueTestCommand() {
		super();
	}

	@SuppressWarnings("UnusedParameters")
	@CliArgument(shortName = ValidTestCommand.COMMAND_1_SHORT_NAME, longName = ValidTestCommand.COMMAND_1_LONG_NAME,
			obligatory = false,
			defaultValue = "test-value", isDefaultValueNull = true, parser = StringArgumentParser.class,
			validator = DefaultArgumentValidator.class,
			description = ValidTestCommand.DESCRIPTION,
			examples = {ValidTestCommand.EXAMPLE})
	public void method(String a) {
	}
}
