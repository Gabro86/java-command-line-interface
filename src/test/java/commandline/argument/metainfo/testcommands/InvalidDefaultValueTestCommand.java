package commandline.argument.metainfo.testcommands;

import commandline.argument.metainfo.CommandArgument;
import commandline.argument.validator.DefaultArgumentValidator;
import commandline.language.parser.argument.StringArgumentParser;

/**
 * User: gno, Date: 02.08.13 - 15:41
 */
@SuppressWarnings("EmptyMethod")
public class InvalidDefaultValueTestCommand {
	public InvalidDefaultValueTestCommand() {
		super();
	}

	@SuppressWarnings("UnusedParameters")
	@CommandArgument(shortName = ValidTestCommand.COMMAND_1_SHORT_NAME, longName = ValidTestCommand.COMMAND_1_LONG_NAME,
			obligatory = false,
			defaultValue = "test-value", defaultToNull = true, parser = StringArgumentParser.class,
			validator = DefaultArgumentValidator.class,
			description = ValidTestCommand.DESCRIPTION,
			examples = {ValidTestCommand.EXAMPLE})
	public void method(String a) {
	}
}
