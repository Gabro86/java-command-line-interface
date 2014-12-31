package commandline.language;

import commandline.language.parser.argument.ArgumentsParser;
import commandline.language.syntax.validator.command.CommandSyntaxValidator;
import commandline.exception.ArgumentNullException;
import org.jetbrains.annotations.NotNull;

/**
 * User: gno, Date: 09.07.13 - 14:22
 */
public class CommandLineLanguage {
	@NotNull
	private final ArgumentsParser argumentsParser;
	@NotNull
	private final CommandSyntaxValidator syntaxValidator;

	public CommandLineLanguage(@NotNull ArgumentsParser argumentsParser, @NotNull CommandSyntaxValidator syntaxValidator) {
		super();
		if (argumentsParser == null) {
			throw new ArgumentNullException();
		}
		if (syntaxValidator == null) {
			throw new ArgumentNullException();
		}
		this.argumentsParser = argumentsParser;
		this.syntaxValidator = syntaxValidator;
	}

	@NotNull
	public ArgumentsParser getArgumentsParser() {
		return this.argumentsParser;
	}

	@NotNull
	public CommandSyntaxValidator getSyntaxValidator() {
		return this.syntaxValidator;
	}
}
