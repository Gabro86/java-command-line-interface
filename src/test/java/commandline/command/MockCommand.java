package commandline.command;

import commandline.argument.metainfo.CommandArgument;
import commandline.argument.validator.DefaultArgumentValidator;
import commandline.language.parser.argument.StringArgumentParser;
import commandline.exception.ArgumentNullException;

/**
 * User: gno Date: 28.06.13 Time: 10:23
 */
@SuppressWarnings("SameParameterValue")
public class MockCommand extends Command {
	public static final String LONG_NAME = "test-key";
	public static final String SHORT_NAME = "t";
	public static final Class<String> TYPE = String.class;
	public static final Class<StringArgumentParser> PARSER = StringArgumentParser.class;
	public static final Class<DefaultArgumentValidator> VALIDATOR = DefaultArgumentValidator.class;
	public static final boolean OBLIGATORY = true;
	public static final String DEFAULT_VALUE = null;
	public static final String DESCRIPTION = "This is a description.";
	public static final String EXAMPLE = "example";
	public static final String[] EXAMPLES = {EXAMPLE};
	public static final String COMMAND_NAME = "test-command";
	private String textValue;

	public MockCommand() {
		super(COMMAND_NAME, "This is a test command.");
		setTestField("");
	}

	public String getTestValue() {
		return this.textValue;
	}

	@CommandArgument(shortName = "t", longName = "test-key", validator = DefaultArgumentValidator.class,
			parser = StringArgumentParser.class, obligatory = true, examples = {"example"},
			description = "This is a description.")
	public void setTestField(String textValue) {
		if (textValue == null) {
			throw new ArgumentNullException();
		}
		this.textValue = textValue;
	}

	@Override
	public void execute() {
	}
}
