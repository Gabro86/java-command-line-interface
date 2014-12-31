package commandline.language.parser.argument;

import commandline.argument.ArgumentList;
import commandline.argument.metainfo.ArgumentMetaInfo;
import commandline.argument.metainfo.ArgumentMetaInfoExtractor;
import commandline.argument.metainfo.testcommands.ValidTestCommand;
import commandline.argument.validator.DefaultArgumentValidator;
import commandline.command.CommandLineException;
import commandline.language.syntax.SyntaxException;
import commandline.exception.ArgumentNullException;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * User: gno, Date: 26.07.13 - 15:36
 */
public class GnuArgumentsParserTest {
	public GnuArgumentsParserTest() {
		super();
	}

	@Test
	public void testParse_Success_ShortArgument() throws Exception {
		ArgumentList arguments;
		ArgumentMetaInfo info;

		info = createMetaInfo("key", "k");
		arguments = parse("command -k value", info);
		assertEquals("value", arguments.get(info.getLongName()).getValue());
	}

	@Test
	public void testParse_Success_LongArgument() throws Exception {
		ArgumentList arguments;
		ArgumentMetaInfo info;

		info = createMetaInfo("key", "k");
		arguments = parse("command --key value", info);
		assertEquals("value", arguments.get(info.getLongName()).getValue());
	}

	@Test
	public void testParse_Success_MultipleLongArguments() throws Exception {
		ArgumentList arguments;
		ArgumentMetaInfo info1;
		ArgumentMetaInfo info2;

		info1 = createMetaInfo("key1", "k");
		info2 = createMetaInfo("key2", "l");
		arguments = parse("command --key1 value --key2 value", info1, info2);
		assertEquals("value", arguments.get(info1.getLongName()).getValue());
		assertEquals("value", arguments.get(info2.getLongName()).getValue());
	}

	@Test
	public void testParse_Success_MultipleShortArguments() throws Exception {
		ArgumentList arguments;
		ArgumentMetaInfo info1;
		ArgumentMetaInfo info2;

		info1 = createMetaInfo("key1", "k");
		info2 = createMetaInfo("key2", "l");
		arguments = parse("command -k value -l value", info1, info2);
		assertEquals("value", arguments.get(info1.getLongName()).getValue());
		assertEquals("value", arguments.get(info2.getLongName()).getValue());
	}

	@Test
	public void testParse_Success_LongAndShortArguments() throws Exception {
		ArgumentList arguments;
		ArgumentMetaInfo info1;
		ArgumentMetaInfo info2;

		info1 = createMetaInfo("key1", "k");
		info2 = createMetaInfo("key2", "l");
		arguments = parse("command --key1 value -l value", info1, info2);
		assertEquals("value", arguments.get(info1.getLongName()).getValue());
		assertEquals("value", arguments.get(info2.getLongName()).getValue());
	}

	@Test
	public void testParse_Success_NoArguments() throws Exception {
		ArgumentList arguments;

		arguments = parse("");
		assertEquals(true, arguments.isEmpty());
	}

	@Test
	public void testParse_Success_EmptyString() throws Exception {
		ArgumentList arguments;

		arguments = parse("   ");
		assertEquals(true, arguments.isEmpty());
	}

	@Test(expected = CommandLineException.class)
	public void testParse_Fail_NoCommand() throws Exception {
		ArgumentMetaInfo info;
		ArgumentList arguments;

		info = createMetaInfo("key", "k");
		arguments = parse("--key value", info);
		assertEquals(true, arguments.isEmpty());
	}

	@Test(expected = CommandLineException.class)
	public void testParse_Fail_NoArgumentValue() throws Exception {
		ArgumentMetaInfo info;
		ArgumentList arguments;

		info = createMetaInfo("key", "k");
		arguments = parse("command --key", info);
		assertEquals(true, arguments.isEmpty());
	}

	@Test(expected = CommandLineException.class)
	public void testParse_Fail_NoArgumentName() throws Exception {
		ArgumentMetaInfo info;
		ArgumentList arguments;

		info = createMetaInfo("key", "k");
		arguments = parse("command value", info);
		assertEquals(true, arguments.isEmpty());
	}

	@Test(expected = CommandLineException.class)
	public void testParse_Fail_MultipleNoArgumentName() throws Exception {
		ArgumentMetaInfo info;
		ArgumentList arguments;

		info = createMetaInfo("key", "k");
		arguments = parse("command value value", info);
		assertEquals(true, arguments.isEmpty());
	}

	@Test
	public void testParse_Success_MultipleValues() throws Exception {
		ArgumentMetaInfo info;
		ArgumentList arguments;

		info = createMetaInfo("key", "k");
		arguments = parse("command --key value1 value2", info);
		assertEquals("value1", arguments.get(info.getLongName()).getValue());
	}

	@Test(expected = CommandLineException.class)
	public void testParse_Success_ArgumentsWithNoValues() throws Exception {
		ArgumentMetaInfo info1;
		ArgumentMetaInfo info2;
		ArgumentList arguments;

		info1 = createMetaInfo("key1", "k");
		info2 = createMetaInfo("key2", "l");
		arguments = parse("command --key1 --key2", info1, info2);
		assertEquals(true, arguments.isEmpty());
	}

	@Test(expected = CommandLineException.class)
	public void testParse_Fail_NotAllArgumentsHaveValues() throws Exception {
		ArgumentMetaInfo info1;
		ArgumentMetaInfo info2;
		ArgumentList arguments;

		info1 = createMetaInfo("key1", "k");
		info2 = createMetaInfo("key2", "l");
		arguments = parse("command --key1 value --key2", info1, info2);
		assertEquals("value", arguments.get(info1.getLongName()).getValue());
		assertNull(arguments.get(info2.getLongName()));
	}

	@Test(expected = CommandLineException.class)
	public void testParse_Fail_NoArgumentsPassed() throws Exception {
		ArgumentMetaInfo info1;

		info1 = createMetaInfo("key1", "k");
		parse("command", info1);
	}

	public ArgumentList parse(String command, ArgumentMetaInfo... infos) {
		GnuArgumentsParser parser;
		String[] tokens;
		List<ArgumentMetaInfo> infoList;
		ArgumentList arguments;
		ArgumentMetaInfo[] editInfos;

		if (command == null) {
			throw new ArgumentNullException();
		}
		editInfos = infos;
		if (editInfos == null) {
			editInfos = new ArgumentMetaInfo[0];
		}
		tokens = command.split(" ");
		infoList = Arrays.asList(editInfos);
		parser = new GnuArgumentsParser();
		arguments = parser.parse(tokens, infoList);

		return arguments;
	}

	private ArgumentMetaInfo createMetaInfo(String longName, String shortName) {
		ArgumentMetaInfo info;

		if (longName == null) {
			throw new ArgumentNullException();
		}
		if (shortName == null) {
			throw new ArgumentNullException();
		}
		info = new ArgumentMetaInfo(longName, shortName, String.class, StringArgumentParser.class, DefaultArgumentValidator.class,
				true, null, "description", new String[] {"example"});

		return info;
	}

	@Test
	public void testRemovePrefix_ShortPrefix() throws Exception {
		GnuArgumentsParser parser;
		String value;
		String editValue;

		value = "-a";
		parser = new GnuArgumentsParser();
		editValue = parser.removePrefix(value);
		assertEquals("a", editValue);
	}

	@Test
	public void testRemovePrefix_ShortPrefixNoOtherChar() throws Exception {
		GnuArgumentsParser parser;
		String value;
		String editValue;

		value = "-";
		parser = new GnuArgumentsParser();
		editValue = parser.removePrefix(value);
		assertEquals("", editValue);
	}

	@Test
	public void testRemovePrefix_LongPrefix() throws Exception {
		GnuArgumentsParser parser;
		String value;
		String editValue;

		value = "--a";
		parser = new GnuArgumentsParser();
		editValue = parser.removePrefix(value);
		assertEquals("a", editValue);
	}

	@Test
	public void testRemovePrefix_LongPrefix_NoOtherChar() throws Exception {
		GnuArgumentsParser parser;
		String value;
		String editValue;

		value = "--";
		parser = new GnuArgumentsParser();
		editValue = parser.removePrefix(value);
		assertEquals("", editValue);
	}

	@Test(expected = SyntaxException.class)
	public void testRemovePrefix_NoPrefix() throws Exception {
		GnuArgumentsParser parser;
		String value;
		String editValue;

		value = "a";
		parser = new GnuArgumentsParser();
		editValue = parser.removePrefix(value);
		assertEquals("a", editValue);
	}

	@Test
	public void testParse() throws Exception {
		testParse("valid-command", "--" + ValidTestCommand.COMMAND_1_LONG_NAME, "value1", "--" + ValidTestCommand.COMMAND_2_LONG_NAME,
				"value2", "--" + ValidTestCommand.COMMAND_3_LONG_NAME, "value3", "--" + ValidTestCommand.COMMAND_4_LONG_NAME,
				"value4",
				"--" + ValidTestCommand.COMMAND_5_LONG_NAME, "value5");
	}

	@Test(expected = CommandLineException.class)
	public void testParse_MissingObligatoryArgument() throws Exception {
		testParse("valid-command", "--" + ValidTestCommand.COMMAND_2_LONG_NAME, "value2", "--" + ValidTestCommand.COMMAND_3_LONG_NAME,
				"value3", "--" + ValidTestCommand.COMMAND_4_LONG_NAME, "value4", "--" + ValidTestCommand.COMMAND_5_LONG_NAME,
				"value5");
	}

	@Test
	public void testParse_MissingOptionalArgument() throws Exception {
		//The validator tests if every argument was passed, also the optional arguments. If the user does not pass an argument
		//if will be add internally to the passed argument list. This way the arguments are all obligatory for the validator.
		testParse("valid-command", "--" + ValidTestCommand.COMMAND_1_LONG_NAME, "value1", "--" + ValidTestCommand.COMMAND_2_LONG_NAME,
				"value2", "--" + ValidTestCommand.COMMAND_3_LONG_NAME, "value3", "--" + ValidTestCommand.COMMAND_5_LONG_NAME,
				"value5");
	}

	public void testParse(String... commandTokens) throws Exception {
		List<ArgumentMetaInfo> metaInfos;
		ArgumentMetaInfoExtractor extractor;
		GnuArgumentsParser parser;

		if (commandTokens == null) {
			throw new ArgumentNullException();
		}
		extractor = new ArgumentMetaInfoExtractor();
		metaInfos = extractor.extract(ValidTestCommand.class);
		parser = new GnuArgumentsParser();
		parser.parse(commandTokens, metaInfos);
	}
}
