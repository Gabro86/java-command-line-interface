package commandline.language.parser.specific.list;

import commandline.language.parser.specific.UrlArgumentParser;

import java.net.URL;

/*
 * This class is aimed to be used in the @CliAnnotation
 */

/**
 * User: gno, Date: 18.02.2015 - 14:44
 */
public class UrlListArgumentParser extends ListArgumentParser<URL> {
	public UrlListArgumentParser() {
		super(new UrlArgumentParser());
	}
}
