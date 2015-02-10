package commandline.language.parser.specific;

import commandline.language.parser.ArgumentParseException;
import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.assertEquals;

public class UrlArgumentParserTest {
	@Test
	public void testParse() throws Exception {
		UrlArgumentParser parser;
		URL urlBefore;
		URL urlAfter;
		String urlString;

		urlString = "http://localhost";
		urlBefore = new URL(urlString);

		parser = new UrlArgumentParser();
		urlAfter = parser.parse(urlString);
		assertEquals(urlBefore, urlAfter);
	}

	@Test(expected = ArgumentParseException.class)
	public void testParse_InvalidUrl() throws Exception {
		new UrlArgumentParser().parse("invalid-url");
	}
}