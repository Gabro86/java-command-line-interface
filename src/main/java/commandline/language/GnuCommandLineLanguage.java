package commandline.language;

import commandline.language.parser.argument.GnuArgumentsParser;
import commandline.language.syntax.validator.command.GnuCommandSyntaxValidator;

/**
 * User: gno, Date: 09.07.13 - 14:24
 */
public class GnuCommandLineLanguage extends CommandLineLanguage {
	public GnuCommandLineLanguage() {
		super(new GnuArgumentsParser(), new GnuCommandSyntaxValidator());
	}
}
