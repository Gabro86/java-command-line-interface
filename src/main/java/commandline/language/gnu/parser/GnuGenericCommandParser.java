package commandline.language.gnu.parser;

import commandline.argument.GenericArgument;
import commandline.command.GenericCommand;
import commandline.exception.ArgumentNullException;
import commandline.language.gnu.syntax.GnuCommandSyntaxValidator;
import commandline.language.parser.GenericCommandParser;
import commandline.language.syntax.CommandSyntaxValidator;
import org.jetbrains.annotations.NotNull;

/**
 * User: gno, Date: 06.01.2015 - 16:02
 */
public class GnuGenericCommandParser implements GenericCommandParser {
	public GnuGenericCommandParser() {
		super();
	}

	@NotNull
	@Override
	public GenericCommand parse(@NotNull String[] cliArguments) {
		String value;
		String name;
		GenericArgument argument;
		GenericCommand command;
		CommandSyntaxValidator syntaxValidator;

		if (cliArguments == null) {
			throw new ArgumentNullException();
		}

		syntaxValidator = new GnuCommandSyntaxValidator();
		syntaxValidator.validate(cliArguments);

		command = new GenericCommand(cliArguments[0]);
		//Parses the argument represented by key-value pairs
		for (int i = 1; i < cliArguments.length - 1; i += 2) {
			name = removePrefix(cliArguments[i]);
			value = cliArguments[i + 1];
			argument = new GenericArgument(name, value);
			command.addArgument(argument);
		}

		return command;
	}

	@NotNull
	String removePrefix(@NotNull String value) {
		String editValue;

		if (value == null) {
			throw new ArgumentNullException();
		}
		if (value.startsWith("--")) {
			editValue = value.substring(2);
		} else if (value.startsWith("-")) {
			editValue = value.substring(1);
		} else {
			editValue = value;
		}

		return editValue;
	}
}
