package commandline.command.mock;

import commandline.annotation.CliArgument;
import commandline.argument.validator.DefaultArgumentValidator;
import commandline.language.parser.specific.StringArgumentParser;

/**
 * User: gno, Date: 02.08.13 - 15:41
 */
@SuppressWarnings("EmptyMethod")
public class DuplicateShortNameTestCommand {
	public DuplicateShortNameTestCommand() {
		super();
	}

	@SuppressWarnings("UnusedParameters")
	@CliArgument(shortName = "t", longName = "test-keyA", validator = DefaultArgumentValidator.class,
			parser = StringArgumentParser.class, obligatory = true, examples = {ValidTestCommand.ARGUMENT_EXAMPLE},
			description = ValidTestCommand.COMMAND_DESCRIPTION)
	public void method1(String a) {
	}

	@SuppressWarnings("UnusedParameters")
	@CliArgument(shortName = "t", longName = "test-keyB", validator = DefaultArgumentValidator.class,
			parser = StringArgumentParser.class, obligatory = true, examples = {ValidTestCommand.ARGUMENT_EXAMPLE},
			description = ValidTestCommand.COMMAND_DESCRIPTION)
	public void method2(String a) {
	}
}