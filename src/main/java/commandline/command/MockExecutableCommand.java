package commandline.command;

import commandline.annotation.CliArgument;
import commandline.annotation.CliCommand;
import commandline.argument.validator.DefaultArgumentValidator;
import commandline.exception.ArgumentNullException;
import commandline.language.parser.specific.StringArgumentParser;
import org.jetbrains.annotations.NotNull;

/**
 * User: gno Date: 28.06.13 Time: 10:23
 */
@SuppressWarnings("SameParameterValue")
@CliCommand(name = MockExecutableCommand.COMMAND_NAME, description = MockExecutableCommand.COMMAND_DESCRIPTION)
public class MockExecutableCommand extends ExecutableCommand {
	public static final String COMMAND_NAME = "test-command";
	public static final String COMMAND_DESCRIPTION = "This is a test command.";
	public static final String ARGUMENT_TEST_LONG_NAME = "test-key";
	public static final String ARGUMENT_TEST_SHORT_NAME = "t";
	public static final Class<String> ARGUMENT_TEST_VALUE_CLASS = String.class;
	public static final Class<StringArgumentParser> ARGUMENT_TEST_PARSER = StringArgumentParser.class;
	public static final Class<DefaultArgumentValidator> ARGUMENT_TEST_VALIDATOR = DefaultArgumentValidator.class;
	public static final boolean ARGUMENT_TEST_OBLIGATORY = true;
	public static final String ARGUMENT_TEST_DEFAULT_VALUE = null;
	public static final String ARGUMENT_TEST_DESCRIPTION = "This is a description.";
	public static final String ARGUMENT_TEST_EXAMPLE = "example";
	public static final String[] ARGUMENT_TEST_EXAMPLES = {ARGUMENT_TEST_EXAMPLE};
	private String textValue;

	public MockExecutableCommand() {
		super();
		setTestField("");
	}

	public String getTestValue() {
		return this.textValue;
	}

	@CliArgument(shortName = ARGUMENT_TEST_SHORT_NAME, longName = ARGUMENT_TEST_LONG_NAME, validator = DefaultArgumentValidator.class,
			parser = StringArgumentParser.class, obligatory = ARGUMENT_TEST_OBLIGATORY,
			examples = {ARGUMENT_TEST_EXAMPLE}, description = ARGUMENT_TEST_DESCRIPTION)
	public void setTestField(String textValue) {
		if (textValue == null) {
			throw new ArgumentNullException();
		}
		this.textValue = textValue;
	}

	@Override
	public void execute(@NotNull Command command) {
	}
}
