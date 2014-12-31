package commandline.argument.metainfo.testcommands;

import commandline.argument.metainfo.CommandArgument;
import commandline.argument.validator.DefaultArgumentValidator;
import commandline.language.parser.argument.StringArgumentParser;

/**
 * User: gno, Date: 02.08.13 - 15:41
 */
@SuppressWarnings("EmptyMethod")
public class DoubleLongCommandArgumentTestCommand {
	public DoubleLongCommandArgumentTestCommand() {
		super();
	}

	@SuppressWarnings("UnusedParameters")
	@CommandArgument(shortName = "s", longName = "test-key", validator = DefaultArgumentValidator.class,
			parser = StringArgumentParser.class, obligatory = true, examples = {ValidTestCommand.EXAMPLE},
			description = ValidTestCommand.DESCRIPTION)
	public void method1(String a) {
	}

	@SuppressWarnings("UnusedParameters")
	@CommandArgument(shortName = "t", longName = "test-key", validator = DefaultArgumentValidator.class,
			parser = StringArgumentParser.class, obligatory = true, examples = {ValidTestCommand.EXAMPLE},
			description = ValidTestCommand.DESCRIPTION)
	public void method2(String a) {
	}
}