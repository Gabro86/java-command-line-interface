package commandline.command.mock;

import commandline.annotation.CliArgument;
import commandline.annotation.CliCommand;
import commandline.argument.validator.DefaultArgumentValidator;
import commandline.command.Command;
import commandline.command.ExecutableCommand;
import commandline.language.parser.specific.StringArgumentParser;
import org.jetbrains.annotations.NotNull;

/**
 * User: gno, Date: 02.08.13 - 15:40
 */
@CliCommand(name = ValidTestCommand.COMMAND_NAME, description = ValidTestCommand.COMMAND_DESCRIPTION)
public class ValidTestCommand extends ExecutableCommand {
	public static final String COMMAND_NAME = "valid-command";
	public static final String COMMAND_DESCRIPTION = "This is a command description.";
	public static final String ARGUMENT_DESCRIPTION = "This is an argument description.";
	public static final String ARGUMENT_EXAMPLE = "example1";
	public static final String[] ARGUMENT_EXAMPLES = new String[] {ARGUMENT_EXAMPLE};
	public static final String ARGUMENT_1_SHORT_NAME = "a";
	public static final String ARGUMENT_1_LONG_NAME = "test-key1";
	public static final String ARGUMENT_2_SHORT_NAME = "b";
	public static final String ARGUMENT_2_LONG_NAME = "test-key2";
	public static final String ARGUMENT_3_SHORT_NAME = "c";
	public static final String ARGUMENT_3_LONG_NAME = "test-key3";
	public static final String ARGUMENT_4_SHORT_NAME = "d";
	public static final String ARGUMENT_4_LONG_NAME = "test-key4";
	public static final String ARGUMENT_5_SHORT_NAME = "e";
	public static final String ARGUMENT_5_LONG_NAME = "test-key5";
	public static final String ARGUMENT_6_SHORT_NAME = "f";
	public static final String ARGUMENT_6_LONG_NAME = "test-key6";

	public ValidTestCommand() {
		super();
	}

	@SuppressWarnings("UnusedParameters")
	@CliArgument(shortName = ARGUMENT_1_SHORT_NAME, longName = ARGUMENT_1_LONG_NAME, obligatory = true,
			parser = StringArgumentParser.class, validator = DefaultArgumentValidator.class, description = ARGUMENT_DESCRIPTION,
			examples = {ARGUMENT_EXAMPLE})
	public void obligatoryWithParserWithValidator(String a) {
	}

	@SuppressWarnings("UnusedParameters")
	@CliArgument(shortName = ARGUMENT_2_SHORT_NAME, longName = ARGUMENT_2_LONG_NAME, obligatory = true,
			parser = StringArgumentParser.class,
			description = ARGUMENT_DESCRIPTION, examples = {ARGUMENT_EXAMPLE})
	public void obligatoryWithParserNoValidator(String a) {
	}

	@SuppressWarnings("UnusedParameters")
	@CliArgument(shortName = ARGUMENT_3_SHORT_NAME, longName = ARGUMENT_3_LONG_NAME, obligatory = true,
			validator = DefaultArgumentValidator.class,
			description = ARGUMENT_DESCRIPTION, examples = {ARGUMENT_EXAMPLE})
	public void obligatoryNoParserWithValidator(String a) {
	}

	@SuppressWarnings("UnusedParameters")
	@CliArgument(shortName = ARGUMENT_6_SHORT_NAME, longName = ARGUMENT_6_LONG_NAME, obligatory = true,
			description = ARGUMENT_DESCRIPTION, examples = {ARGUMENT_EXAMPLE})
	public void obligatoryNoParserNoValidator(String a) {
	}

	@SuppressWarnings("UnusedParameters")
	@CliArgument(shortName = ARGUMENT_4_SHORT_NAME, longName = ARGUMENT_4_LONG_NAME, obligatory = false,
			defaultValue = "test-value", parser = StringArgumentParser.class, validator = DefaultArgumentValidator.class,
			description = ARGUMENT_DESCRIPTION, examples = {ARGUMENT_EXAMPLE})
	public void optionalNonNullDefaultValue(String a) {
	}

	@SuppressWarnings("UnusedParameters")
	@CliArgument(shortName = ARGUMENT_5_SHORT_NAME, longName = ARGUMENT_5_LONG_NAME, obligatory = false,
			isDefaultValueNull = true, parser = StringArgumentParser.class, validator = DefaultArgumentValidator.class,
			description = ARGUMENT_DESCRIPTION, examples = {ARGUMENT_EXAMPLE})
	public void optionalNullDefaultValue(String a) {
	}

	@Override
	public void execute(@NotNull Command command) {
	}
}