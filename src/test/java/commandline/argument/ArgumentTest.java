package commandline.argument;

import commandline.argument.metainfo.ArgumentMetaInfo;
import commandline.command.CommandLineException;
import commandline.language.parser.argument.IntegerArgumentParser;
import commandline.language.parser.argument.StringArgumentParser;
import commandline.argument.validator.IntegerArgumentValidator;
import commandline.argument.validator.StringArgumentValidator;
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
		ArgumentMetaInfo info;

		info = new ArgumentMetaInfo("longName", "s", String.class, StringArgumentParser.class, StringArgumentValidator.class, true,
				null, "description", new String[] {"example"});
		new Argument<>(info, "value");
	}

	@SuppressWarnings("ResultOfObjectAllocationIgnored")
	@Test(expected = CommandLineException.class)
	public void testConstructor_ValueTypeMismatch() {
		ArgumentMetaInfo info;

		info = new ArgumentMetaInfo("longName", "s", String.class, StringArgumentParser.class, StringArgumentValidator.class, true,
				null, "description", new String[] {"example"});
		new Argument<>(info, new Object());
	}

	@Test
	public void testParse() throws Exception {
		ArgumentMetaInfo info;
		Argument<Integer> argument;

		info = new ArgumentMetaInfo("longName", "s", Integer.class, IntegerArgumentParser.class, IntegerArgumentValidator.class, true,
				null, "description", new String[] {"example"});
		argument = Argument.parse(info, "100");
		assertEquals(100, (int) argument.getValue());
	}

	@Test
	public void testParse_NullValue() throws Exception {
		ArgumentMetaInfo info;
		Argument<Object> argument;

		info = new ArgumentMetaInfo("longName", "s", Integer.class, IntegerArgumentParser.class, IntegerArgumentValidator.class,
				false,
				null, "description", new String[] {"example"});
		argument = Argument.parse(info, null);
		assertEquals(null, argument.getValue());
	}

	@Test
	public void testParse_NullValueWithNonNullDefaultValue() throws Exception {
		ArgumentMetaInfo info;
		Argument<Integer> argument;

		info = new ArgumentMetaInfo("longName", "s", Integer.class, IntegerArgumentParser.class, IntegerArgumentValidator.class,
				false,
				"100", "description", new String[] {"example"});
		argument = Argument.parse(info, null);
		assertEquals(100, (int) argument.getValue());
	}

	@Test(expected = CommandLineException.class)
	public void testParse_NullObligatoryValue() throws Exception {
		ArgumentMetaInfo info;

		info = new ArgumentMetaInfo("longName", "s", Integer.class, IntegerArgumentParser.class, IntegerArgumentValidator.class, true,
				null, "description", new String[] {"example"});
		Argument.parse(info, null);
	}
}
