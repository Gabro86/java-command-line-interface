package commandline.language.parser.argument;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: gno, Date: 01.08.13 - 11:22
 */

//The test for too big values does not work, because the method Double.valueOf does not throw an exception when a too big value is
//passed.
public class DoubleArgumentParserTest {
	public DoubleArgumentParserTest() {
		super();
	}

	@Test
	public void testParse() {
		DoubleArgumentParser parser;

		parser = new DoubleArgumentParser();
		assertEquals(100, parser.parse("100"), 0);
	}

	@Test(expected = ArgumentParseException.class)
	public void testParse_InvalidValue() {
		DoubleArgumentParser parser;

		parser = new DoubleArgumentParser();
		parser.parse("invalid value");
	}
}
