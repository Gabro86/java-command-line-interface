package commandline.command.mock;

import commandline.annotation.CliArgument;
import commandline.argument.validator.DefaultArgumentValidator;
import commandline.command.Command;
import commandline.command.ExecutableCommand;
import commandline.language.parser.specific.StringArgumentParser;
import org.jetbrains.annotations.NotNull;

/**
 * User: gno, Date: 02.08.13 - 15:40
 */
public class NoSetterParameterTestCommand extends ExecutableCommand {
	public NoSetterParameterTestCommand() {
		super();
	}

	@CliArgument(shortName = "t", longName = "test-key", validator = DefaultArgumentValidator.class,
			parser = StringArgumentParser.class, obligatory = true, examples = {ValidTestCommand.EXAMPLE},
			description = ValidTestCommand.DESCRIPTION)
	public void method() {
	}

	@Override
	public void execute(@NotNull Command command) {
	}
}
