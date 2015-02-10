package commandline.language.parser.specific;

import commandline.language.parser.ArgumentParseException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: gno, Date: 01.08.13 - 11:22
 */
public class ShortArgumentParserTest {
	public ShortArgumentParserTest() {
		super();
	}

	@Test
	public void testParse() {
		ShortArgumentParser parser;

		parser = new ShortArgumentParser();
		assertEquals(100, (short) parser.parse("100"));
	}

	@Test(expected = ArgumentParseException.class)
	public void testParse_TooBigValue() {
		ShortArgumentParser parser;

		parser = new ShortArgumentParser();
		parser.parse("1000000000000000000000000000000000000000");
	}

	@Test(expected = ArgumentParseException.class)
	public void testParse_InvalidValue() {
		ShortArgumentParser parser;

		parser = new ShortArgumentParser();
		parser.parse("invalid value");
	}
}
