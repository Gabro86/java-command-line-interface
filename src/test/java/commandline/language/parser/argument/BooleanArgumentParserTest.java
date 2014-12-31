package commandline.language.parser.argument;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: gno, Date: 01.08.13 - 11:29
 */
public class BooleanArgumentParserTest {
	public BooleanArgumentParserTest() {
		super();
	}

	@Test
	public void testParse_TrueValue() {
		BooleanArgumentParser parser;
		boolean value;

		parser = new BooleanArgumentParser();
		value = parser.parse("true");
		assertEquals(true, value);
	}

	@Test
	public void testParse_FalseValue() {
		BooleanArgumentParser parser;
		boolean value;

		parser = new BooleanArgumentParser();
		value = parser.parse("false");
		assertEquals(false, value);
	}

	@Test(expected = ArgumentParseException.class)
	public void testParse_InvalidValue() {
		BooleanArgumentParser parser;

		parser = new BooleanArgumentParser();
		parser.parse("invalid value");
	}
}
