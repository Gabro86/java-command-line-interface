package commandline.command.mock;

import commandline.annotation.CliArgument;
import commandline.argument.validator.DefaultArgumentValidator;
import commandline.language.parser.specific.StringArgumentParser;

/**
 * User: gno, Date: 02.08.13 - 15:40
 */
public class NoMethodParameterTestCommand {
	public NoMethodParameterTestCommand() {
		super();
	}

	@CliArgument(shortName = "t", longName = "test-key", validator = DefaultArgumentValidator.class,
			parser = StringArgumentParser.class, obligatory = true, examples = {ValidTestCommand.EXAMPLE},
			description = ValidTestCommand.DESCRIPTION)
	public void method() {
	}
}
