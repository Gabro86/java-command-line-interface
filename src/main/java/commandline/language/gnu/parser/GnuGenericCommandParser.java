package commandline.language.gnu.parser;

import commandline.argument.GenericArgument;
import commandline.command.GenericCommand;
import commandline.exception.ArgumentNullException;
import commandline.language.gnu.syntax.GnuCommandSyntaxValidator;
import commandline.language.parser.GenericCommandParser;
import commandline.language.syntax.CommandSyntaxValidator;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

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
		String name;
		GenericCommand command;
		CommandSyntaxValidator syntaxValidator;
		String cliArgument;
		String commandName;
		HashMap<String, StringBuilder> argumentMap;
		StringBuilder values;
		GenericArgument genericArgument;
		String argumentName;
		String argumentValue;

		if (cliArguments == null) {
			throw new ArgumentNullException();
		}

		syntaxValidator = new GnuCommandSyntaxValidator();
		syntaxValidator.validate(cliArguments);

		values = new StringBuilder();
		argumentMap = new HashMap<>();
		for (int i = 1; i < cliArguments.length; i++) {
			//The arguments must not be validated since the syntax of the cli arguments have been already validated.
			cliArgument = cliArguments[i].trim();
			if (isArgumentName(cliArgument)) {
				name = removePrefix(cliArgument);
				values = new StringBuilder();
				argumentMap.put(name, values);
			} else {
				if (values.length() > 0) {
					values.append(" ");
				}
				values.append(cliArgument);
			}
		}

		//The command name must not be validated since the syntax of the cli arguments have been already validated.
		commandName = cliArguments[0];
		command = new GenericCommand(commandName);
		for (Map.Entry<String, StringBuilder> entry : argumentMap.entrySet()) {
			argumentName = entry.getKey();
			argumentValue = entry.getValue().toString();
			genericArgument = new GenericArgument(argumentName, argumentValue);
			command.addArgument(genericArgument);
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

	boolean isArgumentName(@NotNull String value) {
		if (value == null) {
			throw new ArgumentNullException();
		}
		return value.startsWith("--") || value.startsWith("-");
	}
}
