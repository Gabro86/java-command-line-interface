package commandline.language.parser.argument;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: gno, Date: 01.08.13 - 11:22
 */

//The test for too big values does not work, because the method Double.valueOf does not throw an exception when a too big value is
//passed.
public class FloatArgumentParserTest {
	public FloatArgumentParserTest() {
		super();
	}

	@Test
	public void testParse() {
		FloatArgumentParser parser;

		parser = new FloatArgumentParser();
		assertEquals(100, parser.parse("100"), 0);
	}

	@Test(expected = ArgumentParseException.class)
	public void testParse_InvalidValue() {
		FloatArgumentParser parser;

		parser = new FloatArgumentParser();
		parser.parse("invalid value");
	}
}
