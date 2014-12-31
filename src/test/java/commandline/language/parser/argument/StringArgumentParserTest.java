package commandline.language.parser.argument;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: gno, Date: 01.08.13 - 11:29
 */
public class StringArgumentParserTest {
	public StringArgumentParserTest() {
		super();
	}

	@Test
	public void testParse() {
		StringArgumentParser parser;
		String value;

		parser = new StringArgumentParser();
		value = parser.parse("test value");
		assertEquals("test value", value);
	}
}
