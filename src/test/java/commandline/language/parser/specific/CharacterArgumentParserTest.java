package commandline.language.parser.specific;

import commandline.language.parser.ArgumentParseException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: gno, Date: 01.08.13 - 11:29
 */
public class CharacterArgumentParserTest {
	public CharacterArgumentParserTest() {
		super();
	}

	@Test
	public void testParse() {
		CharacterArgumentParser parser;
		char value;

		parser = new CharacterArgumentParser();
		value = parser.parse("a");
		assertEquals('a', value);
	}

	@Test(expected = ArgumentParseException.class)
	public void testParse_EmptyString() {
		CharacterArgumentParser parser;

		parser = new CharacterArgumentParser();
		parser.parse("");
	}

	@Test(expected = ArgumentParseException.class)
	public void testParse_TooLongString() {
		CharacterArgumentParser parser;

		parser = new CharacterArgumentParser();
		parser.parse("too long string");
	}
}
