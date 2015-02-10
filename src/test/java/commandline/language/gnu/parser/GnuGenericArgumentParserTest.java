package commandline.language.gnu.parser;

import commandline.argument.GenericArgument;
import commandline.command.CommandLineException;
import commandline.command.GenericCommand;
import commandline.language.syntax.SyntaxException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: gno, Date: 26.07.13 - 15:36
 */
public class GnuGenericArgumentParserTest {
	public GnuGenericArgumentParserTest() {
		super();
	}

	@Test
	public void testParse_Success_ShortArgument() throws Exception {
		GenericCommand command;
		GnuGenericCommandParser parser;
		String cliCommand;
		String[] arguments;
		GenericArgument argument;

		parser = new GnuGenericCommandParser();
		cliCommand = "command -k value";
		arguments = cliCommand.split(" ");
		command = parser.parse(arguments);
		argument = command.getArgument("k");
		assertEquals("value", argument.getValue());
	}

	@Test
	public void testParse_Success_LongArgument() throws Exception {
		GenericCommand command;
		GnuGenericCommandParser parser;
		String cliCommand;
		String[] arguments;
		GenericArgument argument;

		parser = new GnuGenericCommandParser();
		cliCommand = "command --key value";
		arguments = cliCommand.split(" ");
		command = parser.parse(arguments);
		argument = command.getArgument("key");
		assertEquals("value", argument.getValue());
	}

	@Test
	public void testParse_Success_MultipleLongArguments() throws Exception {
		GenericCommand command;
		GnuGenericCommandParser parser;
		String cliCommand;
		String[] arguments;
		GenericArgument argument1;
		GenericArgument argument2;

		parser = new GnuGenericCommandParser();
		cliCommand = "command --key1 value1 --key2 value2";
		arguments = cliCommand.split(" ");
		command = parser.parse(arguments);
		argument1 = command.getArgument("key1");
		argument2 = command.getArgument("key2");
		assertEquals("value1", argument1.getValue());
		assertEquals("value2", argument2.getValue());
	}

	@Test
	public void testParse_Success_MultipleShortArguments() throws Exception {
		GenericCommand command;
		GnuGenericCommandParser parser;
		String cliCommand;
		String[] arguments;
		GenericArgument argument1;
		GenericArgument argument2;

		parser = new GnuGenericCommandParser();
		cliCommand = "command -k value1 -l value2";
		arguments = cliCommand.split(" ");
		command = parser.parse(arguments);
		argument1 = command.getArgument("k");
		argument2 = command.getArgument("l");
		assertEquals("value1", argument1.getValue());
		assertEquals("value2", argument2.getValue());
	}

	@Test
	public void testParse_Success_LongAndShortArguments() throws Exception {
		GenericCommand command;
		GnuGenericCommandParser parser;
		String cliCommand;
		String[] arguments;
		GenericArgument argument1;
		GenericArgument argument2;

		parser = new GnuGenericCommandParser();
		cliCommand = "command --key value1 -l value2";
		arguments = cliCommand.split(" ");
		command = parser.parse(arguments);
		argument1 = command.getArgument("key");
		argument2 = command.getArgument("l");
		assertEquals("value1", argument1.getValue());
		assertEquals("value2", argument2.getValue());
	}

	@Test(expected = SyntaxException.class)
	public void testParse_NoArguments() throws Exception {
		GnuGenericCommandParser parser;
		String[] arguments;

		parser = new GnuGenericCommandParser();
		arguments = new String[0];
		parser.parse(arguments);
	}

	@Test(expected = SyntaxException.class)
	public void testParse_EmptyString() throws Exception {
		GnuGenericCommandParser parser;
		String cliCommand;
		String[] arguments;

		parser = new GnuGenericCommandParser();
		cliCommand = "   ";
		arguments = cliCommand.split(" ");
		parser.parse(arguments);
	}

	@Test(expected = SyntaxException.class)
	public void testParse_Fail_NoCommand() throws Exception {
		GnuGenericCommandParser parser;
		String cliCommand;
		String[] arguments;

		parser = new GnuGenericCommandParser();
		cliCommand = "--key value";
		arguments = cliCommand.split(" ");
		parser.parse(arguments);
	}

	@Test(expected = SyntaxException.class)
	public void testParse_Fail_NoArgumentValue() throws Exception {
		GnuGenericCommandParser parser;
		String cliCommand;
		String[] arguments;

		parser = new GnuGenericCommandParser();
		cliCommand = "command --key";
		arguments = cliCommand.split(" ");
		parser.parse(arguments);
	}

	@Test(expected = SyntaxException.class)
	public void testParse_Fail_NoArgumentName() throws Exception {
		GnuGenericCommandParser parser;
		String cliCommand;
		String[] arguments;

		parser = new GnuGenericCommandParser();
		cliCommand = "command value";
		arguments = cliCommand.split(" ");
		parser.parse(arguments);
	}

	@Test(expected = SyntaxException.class)
	public void testParse_Fail_MultipleNoArgumentName() throws Exception {
		GnuGenericCommandParser parser;
		String cliCommand;
		String[] arguments;

		parser = new GnuGenericCommandParser();
		cliCommand = "command value value";
		arguments = cliCommand.split(" ");
		parser.parse(arguments);
	}

	@Test(expected = SyntaxException.class)
	public void testParse_Fail_MultipleValues() throws Exception {
		GnuGenericCommandParser parser;
		String cliCommand;
		String[] arguments;

		parser = new GnuGenericCommandParser();
		cliCommand = "command --key value1 value2";
		arguments = cliCommand.split(" ");
		parser.parse(arguments);
	}

	@Test(expected = CommandLineException.class)
	public void testParse_Success_ArgumentsWithNoValues() throws Exception {
		GenericCommand command;
		GnuGenericCommandParser parser;
		String cliCommand;
		String[] arguments;
		GenericArgument argument1;
		GenericArgument argument2;

		parser = new GnuGenericCommandParser();
		cliCommand = "command --key1 --key2";
		arguments = cliCommand.split(" ");
		command = parser.parse(arguments);
		argument1 = command.getArgument("key1");
		argument2 = command.getArgument("key2");
		assertEquals(null, argument1.getValue());
		assertEquals(null, argument2.getValue());
	}

	@Test(expected = CommandLineException.class)
	public void testParse_Fail_NotAllArgumentsHaveValues() throws Exception {
		GenericCommand command;
		GnuGenericCommandParser parser;
		String cliCommand;
		String[] arguments;
		GenericArgument argument1;
		GenericArgument argument2;

		parser = new GnuGenericCommandParser();
		cliCommand = "command --key1 value --key2";
		arguments = cliCommand.split(" ");
		command = parser.parse(arguments);
		argument1 = command.getArgument("key1");
		argument2 = command.getArgument("key2");
		assertEquals("value", argument1.getValue());
		assertEquals(null, argument2.getValue());
	}

	@Test
	public void testRemovePrefix_ShortPrefix() throws Exception {
		GnuGenericCommandParser parser;
		String value;
		String editValue;

		value = "-a";
		parser = new GnuGenericCommandParser();
		editValue = parser.removePrefix(value);
		assertEquals("a", editValue);
	}

	@Test
	public void testRemovePrefix_ShortPrefixNoOtherChar() throws Exception {
		GnuGenericCommandParser parser;
		String value;
		String editValue;

		value = "-";
		parser = new GnuGenericCommandParser();
		editValue = parser.removePrefix(value);
		assertEquals("", editValue);
	}

	@Test
	public void testRemovePrefix_LongPrefix() throws Exception {
		GnuGenericCommandParser parser;
		String value;
		String editValue;

		value = "--a";
		parser = new GnuGenericCommandParser();
		editValue = parser.removePrefix(value);
		assertEquals("a", editValue);
	}

	@Test
	public void testRemovePrefix_LongPrefix_NoOtherChar() throws Exception {
		GnuGenericCommandParser parser;
		String value;
		String editValue;

		value = "--";
		parser = new GnuGenericCommandParser();
		editValue = parser.removePrefix(value);
		assertEquals("", editValue);
	}

	@Test
	public void testRemovePrefix_NoPrefix() throws Exception {
		GnuGenericCommandParser parser;
		String value;
		String editValue;

		value = "a";
		parser = new GnuGenericCommandParser();
		editValue = parser.removePrefix(value);
		assertEquals("a", editValue);
	}
}
