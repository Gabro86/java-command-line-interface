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

	@Test(expected = CommandLineException.class)
	public void testValidateValue_ValueClassMismatch() {
		ArgumentDefinition definition;

		definition = new ArgumentDefinition("longName", "s", String.class, new StringArgumentParser(), new DefaultArgumentValidator(),
				true, null, "description", new String[] {"example"});
		Argument.validateValue(definition, new Object());
	}

	@Test(expected = CommandLineException.class)
	public void testValidateValue_ValueIsNullButObligatory() {
		ArgumentDefinition definition;

		definition = new ArgumentDefinition("longName", "s", String.class, new StringArgumentParser(), new DefaultArgumentValidator(),
				true, null, "description", new String[] {"example"});
		Argument.validateValue(definition, null);
	}
}
