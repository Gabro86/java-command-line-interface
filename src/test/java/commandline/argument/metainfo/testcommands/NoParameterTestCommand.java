package commandline.argument.metainfo.testcommands;

import commandline.argument.metainfo.CommandArgument;
import commandline.argument.validator.DefaultArgumentValidator;
import commandline.language.parser.argument.StringArgumentParser;

/**
 * User: gno, Date: 02.08.13 - 15:40
 */
public class NoParameterTestCommand {
	public NoParameterTestCommand() {
		super();
	}

	@CommandArgument(shortName = "t", longName = "test-key", validator = DefaultArgumentValidator.class,
			parser = StringArgumentParser.class, obligatory = true, examples = {ValidTestCommand.EXAMPLE},
			description = ValidTestCommand.DESCRIPTION)
	public void method() {
	}
}
