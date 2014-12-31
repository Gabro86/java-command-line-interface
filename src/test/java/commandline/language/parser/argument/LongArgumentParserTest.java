package commandline.language.parser.argument;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: gno, Date: 01.08.13 - 11:22
 */
public class LongArgumentParserTest {
	public LongArgumentParserTest() {
		super();
	}

	@Test
	public void testParse() {
		LongArgumentParser parser;

		parser = new LongArgumentParser();
		assertEquals(100, (long) parser.parse("100"));
	}

	@Test(expected = ArgumentParseException.class)
	public void testParse_TooBigValue() {
		LongArgumentParser parser;

		parser = new LongArgumentParser();
		parser.parse("1000000000000000000000000000000000000000");
	}

	@Test(expected = ArgumentParseException.class)
	public void testParse_InvalidValue() {
		LongArgumentParser parser;

		parser = new LongArgumentParser();
		parser.parse("invalid value");
	}
}
