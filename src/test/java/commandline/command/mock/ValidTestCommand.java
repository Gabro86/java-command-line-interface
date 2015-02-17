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
@CliCommand(name = ValidTestCommand.NAME, description = ValidTestCommand.DESCRIPTION)
public class ValidTestCommand extends ExecutableCommand {
	public static final String NAME = "valid-command";
	public static final String DESCRIPTION = "This is a description.";
	public static final String EXAMPLE = "example";
	public static final String COMMAND_1_SHORT_NAME = "a";
	public static final String COMMAND_1_LONG_NAME = "test-key1";
	public static final String COMMAND_2_SHORT_NAME = "b";
	public static final String COMMAND_2_LONG_NAME = "test-key2";
	public static final String COMMAND_3_SHORT_NAME = "c";
	public static final String COMMAND_3_LONG_NAME = "test-key3";
	public static final String COMMAND_4_SHORT_NAME = "d";
	public static final String COMMAND_4_LONG_NAME = "test-key4";
	public static final String COMMAND_5_SHORT_NAME = "e";
	public static final String COMMAND_5_LONG_NAME = "test-key5";
	public static final String COMMAND_6_SHORT_NAME = "f";
	public static final String COMMAND_6_LONG_NAME = "test-key6";

	public ValidTestCommand() {
		super();
	}

	@SuppressWarnings("UnusedParameters")
	@CliArgument(shortName = COMMAND_1_SHORT_NAME, longName = COMMAND_1_LONG_NAME, obligatory = true,
			parser = StringArgumentParser.class, validator = DefaultArgumentValidator.class, description = DESCRIPTION,
			examples = {EXAMPLE})
	public void obligatoryWithParserWithValidator(String a) {
	}

	@SuppressWarnings("UnusedParameters")
	@CliArgument(shortName = COMMAND_2_SHORT_NAME, longName = COMMAND_2_LONG_NAME, obligatory = true,
			parser = StringArgumentParser.class,
			description = DESCRIPTION, examples = {EXAMPLE})
	public void obligatoryWithParserNoValidator(String a) {
	}

	@SuppressWarnings("UnusedParameters")
	@CliArgument(shortName = COMMAND_3_SHORT_NAME, longName = COMMAND_3_LONG_NAME, obligatory = true,
			validator = DefaultArgumentValidator.class,
			description = DESCRIPTION, examples = {EXAMPLE})
	public void obligatoryNoParserWithValidator(String a) {
	}

	@SuppressWarnings("UnusedParameters")
	@CliArgument(shortName = COMMAND_6_SHORT_NAME, longName = COMMAND_6_LONG_NAME, obligatory = true,
			description = DESCRIPTION, examples = {EXAMPLE})
	public void obligatoryNoParserNoValidator(String a) {
	}

	@SuppressWarnings("UnusedParameters")
	@CliArgument(shortName = COMMAND_4_SHORT_NAME, longName = COMMAND_4_LONG_NAME, obligatory = false,
			defaultValue = "test-value", parser = StringArgumentParser.class, validator = DefaultArgumentValidator.class,
			description = DESCRIPTION, examples = {EXAMPLE})
	public void optionalNonNullDefaultValue(String a) {
	}

	@SuppressWarnings("UnusedParameters")
	@CliArgument(shortName = COMMAND_5_SHORT_NAME, longName = COMMAND_5_LONG_NAME, obligatory = false,
			isDefaultValueNull = true, parser = StringArgumentParser.class, validator = DefaultArgumentValidator.class,
			description = DESCRIPTION, examples = {EXAMPLE})
	public void optionalNullDefaultValue(String a) {
	}

	@Override
	public void execute(@NotNull Command command) {
	}
}