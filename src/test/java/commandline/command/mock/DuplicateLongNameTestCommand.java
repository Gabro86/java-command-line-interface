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
@SuppressWarnings("EmptyMethod")
public class DuplicateLongNameTestCommand extends ExecutableCommand {
	public DuplicateLongNameTestCommand() {
		super();
	}

	@SuppressWarnings("UnusedParameters")
	@CliArgument(shortName = "s", longName = "test-key", validator = DefaultArgumentValidator.class,
			parser = StringArgumentParser.class, obligatory = true, examples = {ValidTestCommand.ARGUMENT_EXAMPLE},
			description = ValidTestCommand.COMMAND_DESCRIPTION)
	public void method1(String a) {
	}

	@SuppressWarnings("UnusedParameters")
	@CliArgument(shortName = "t", longName = "test-key", validator = DefaultArgumentValidator.class,
			parser = StringArgumentParser.class, obligatory = true, examples = {ValidTestCommand.ARGUMENT_EXAMPLE},
			description = ValidTestCommand.COMMAND_DESCRIPTION)
	public void method2(String a) {
	}

	@Override
	public void execute(@NotNull Command command) {
	}
}