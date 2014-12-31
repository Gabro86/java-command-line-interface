package commandline.argument;

import commandline.argument.metainfo.ArgumentMetaInfo;
import commandline.language.parser.argument.StringArgumentParser;
import commandline.argument.validator.StringArgumentValidator;

/**
 * User: gno, Date: 26.07.13 - 13:56
 */
public class MockArgument extends Argument<String> {
	public MockArgument() {
		super(new ArgumentMetaInfo("longName", "s", String.class, StringArgumentParser.class, StringArgumentValidator.class, true,
				null, "description", new String[] {"example"}), "mock value");
	}
}
