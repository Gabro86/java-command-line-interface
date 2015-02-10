package commandline.language;

import commandline.exception.ArgumentNullException;
import commandline.language.parser.GenericCommandParser;
import commandline.language.syntax.CommandSyntaxValidator;
import org.jetbrains.annotations.NotNull;

/**
 * User: gno, Date: 09.07.13 - 14:22
 */
public class CommandLineLanguage {
	@NotNull
	private final CommandSyntaxValidator syntaxValidator;
	@NotNull
	private final GenericCommandParser genericCommandParser;

	public CommandLineLanguage(@NotNull CommandSyntaxValidator syntaxValidator, @NotNull GenericCommandParser genericCommandParser) {
		super();
		if (syntaxValidator == null) {
			throw new ArgumentNullException();
		}
		if (genericCommandParser == null) {
			throw new ArgumentNullException();
		}
		this.syntaxValidator = syntaxValidator;
		this.genericCommandParser = genericCommandParser;
	}

	@NotNull
	public CommandSyntaxValidator getSyntaxValidator() {
		return this.syntaxValidator;
	}

	@NotNull
	public GenericCommandParser getGenericCommandParser() {
		return this.genericCommandParser;
	}
}
