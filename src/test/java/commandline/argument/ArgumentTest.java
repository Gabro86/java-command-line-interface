package commandline.argument;

import commandline.argument.validator.DefaultArgumentValidator;
import commandline.command.CommandLineException;
import commandline.language.parser.specific.StringArgumentParser;
import org.junit.Test;

/**
 * User: gno, Date: 26.07.13 - 11:07
 */
public class ArgumentTest {
	public ArgumentTest() {
		super();
	}

	@SuppressWarnings("ResultOfObjectAllocationIgnored")
	@Test
	public void testConstructor() {
		ArgumentDefinition definition;

		definition = new ArgumentDefinition("longName", "s", String.class, StringArgumentParser.class, DefaultArgumentValidator.class,
				true, null, "description", new String[] {"example"});
		new Argument<>(definition, "value");
	}

	@SuppressWarnings("ResultOfObjectAllocationIgnored")
	@Test(expected = CommandLineException.class)
	public void testConstructor_ValueClassMismatch() {
		ArgumentDefinition definition;

		definition = new ArgumentDefinition("longName", "s", String.class, StringArgumentParser.class, DefaultArgumentValidator.class,
				true, null, "description", new String[] {"example"});
		new Argument<>(definition, new Object());
	}
}
