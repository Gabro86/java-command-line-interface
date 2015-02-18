package commandline.language.parser.specific.list;

import commandline.language.parser.specific.DoubleArgumentParser;

/*
 * This class is aimed to be used in the @CliAnnotation
 */

/**
 * User: gno, Date: 18.02.2015 - 14:44
 */
public class DoubleListArgumentParser extends ListArgumentParser<Double> {
	public DoubleListArgumentParser() {
		super(new DoubleArgumentParser());
	}
}
