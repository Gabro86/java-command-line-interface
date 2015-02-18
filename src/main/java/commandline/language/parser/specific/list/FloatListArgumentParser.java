package commandline.language.parser.specific.list;

import commandline.language.parser.specific.FloatArgumentParser;

/*
 * This class is aimed to be used in the @CliAnnotation
 */

/**
 * User: gno, Date: 18.02.2015 - 14:44
 */
public class FloatListArgumentParser extends ListArgumentParser<Float> {
	public FloatListArgumentParser() {
		super(new FloatArgumentParser());
	}
}
