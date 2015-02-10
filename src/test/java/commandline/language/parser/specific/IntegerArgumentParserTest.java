package commandline.language.parser.specific;

import commandline.language.parser.ArgumentParseException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: gno, Date: 01.08.13 - 11:22
 */
public class IntegerArgumentParserTest {
	public IntegerArgumentParserTest() {
		super();
	}

	@Test
	public void testParse() {
		ShortArgumentParser parser;

		parser = new ShortArgumentParser();
		assertEquals(100, (int) parser.parse("100"));
	}

	@Test(expected = ArgumentParseException.class)
	public void testParse_TooBigValue() {
		IntegerArgumentParser parser;

		parser = new IntegerArgumentParser();
		parser.parse("1000000000000000000000000000000000000000");
	}

	@Test(expected = ArgumentParseException.class)
	public void testParse_InvalidValue() {
		IntegerArgumentParser parser;

		parser = new IntegerArgumentParser();
		parser.parse("invalid value");
	}
}
