package commandline.language.parser.argument;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * User: gno, Date: 01.08.13 - 11:13
 */
public class FileArgumentParserTest {
	public FileArgumentParserTest() {
		super();
	}

	@Test
	public void testParse() {
		FileArgumentParser parser;
		File file;
		String path;

		parser = new FileArgumentParser();
		path = "C:\\test.txt";
		file = parser.parse(path);
		assertEquals(path, file.getAbsolutePath());
	}
}
