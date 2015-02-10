package commandline.language.gnu;

import commandline.language.CommandLineLanguage;
import commandline.language.gnu.parser.GnuGenericCommandParser;
import commandline.language.gnu.syntax.GnuCommandSyntaxValidator;

/**
 * User: gno, Date: 09.07.13 - 14:24
 */
public class GnuCommandLineLanguage extends CommandLineLanguage {
	public GnuCommandLineLanguage() {
		super(new GnuCommandSyntaxValidator(), new GnuGenericCommandParser());
	}
}