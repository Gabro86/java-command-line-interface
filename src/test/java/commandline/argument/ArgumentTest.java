package commandline.argument;

import commandline.argument.validator.DefaultArgumentValidator;
import commandline.command.CommandLineException;
import commandline.language.parser.specific.IntegerArgumentParser;
import commandline.language.parser.specific.StringArgumentParser;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

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

	@Test
	public void testParse() throws Exception {
		ArgumentDefinition definition;
		Argument<Integer> argument;

		definition = new ArgumentDefinition("longName", "s", Integer.class, IntegerArgumentParser.class,
				DefaultArgumentValidator.class, true, null, "description", new String[] {"example"});
		argument = Argument.parse(definition, "100");
		assertEquals(100, (int) argument.getValue());
	}

	@Test
	public void testParse_NullValue_NonObligatoryValue_NullDefaultValue() throws Exception {
		ArgumentDefinition definition;
		Argument<Object> argument;

		definition = new ArgumentDefinition("longName", "s", Integer.class, IntegerArgumentParser.class,
				DefaultArgumentValidator.class, false, null, "description", new String[] {"example"});
		argument = Argument.parse(definition, null);
		assertEquals(null, argument.getValue());
	}

	@Test
	public void testParse_NullValue_NonObligatory_NonNullDefaultValue() throws Exception {
		ArgumentDefinition definition;
		Argument<Integer> argument;

		definition = new ArgumentDefinition("longName", "s", Integer.class, IntegerArgumentParser.class,
				DefaultArgumentValidator.class, false, "100", "description", new String[] {"example"});
		argument = Argument.parse(definition, null);
		assertEquals(100, (int) argument.getValue());
	}

	@Test(expected = CommandLineException.class)
	public void testParse_NullValue_Obligatory_NullDefaultValue() throws Exception {
		ArgumentDefinition definition;

		definition = new ArgumentDefinition("longName", "s", Integer.class, IntegerArgumentParser.class,
				DefaultArgumentValidator.class, true, null, "description", new String[] {"example"});
		Argument.parse(definition, null);
	}
}
