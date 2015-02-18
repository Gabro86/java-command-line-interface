package commandline.language.parser.specific.list;

import commandline.language.parser.specific.LongArgumentParser;

/*
 * This class is aimed to be used in the @CliAnnotation
 */

/**
 * User: gno, Date: 18.02.2015 - 14:44
 */
public class LongListArgumentParser extends ListArgumentParser<Long> {
	public LongListArgumentParser() {
		super(new LongArgumentParser());
	}
}
