package commandline.language.gnu.syntax;

import commandline.language.syntax.CommandSyntaxValidator;
import commandline.language.syntax.SyntaxException;
import org.jetbrains.annotations.Nullable;

/**
 * User: gno Date: 28.06.13 Time: 12:18
 */
public class GnuCommandSyntaxValidator extends CommandSyntaxValidator {
	public GnuCommandSyntaxValidator() {
		super();
	}

	@Override
	public void validate(@Nullable String[] commandTokens) {
		GnuCommandNameSyntaxValidator nameValidator;
		GnuKeySyntaxValidator keyValidator;
		GnuValueSyntaxValidator valueValidator;
		String commandName;
		boolean keyExists;
		boolean valueExists;
		String argument;

		if (commandTokens == null) {
			throw new SyntaxException(
					"The syntax validation of the command failed, because the passed command tokens variable is null.");
		}
		//Test if at least a command name was passed.
		if (commandTokens.length == 0) {
			throw new SyntaxException("The syntax validation of the command failed, because the command tokens do not contain " +
					"a command name or command arguments");
		}

		//Validates the command name.
		commandName = commandTokens[0];
		nameValidator = new GnuCommandNameSyntaxValidator();
		nameValidator.validate(commandName);

		//Validates the keys and values of the cli arguments
		keyExists = false;
		valueExists = false;
		if (commandTokens.length > 1) {
			keyValidator = new GnuKeySyntaxValidator();
			valueValidator = new GnuValueSyntaxValidator();
			for (int i = 1; i < commandTokens.length; i++) {
				argument = commandTokens[i];
				if (keyValidator.isValid(argument)) {
					keyExists = true;
				}
				if (valueValidator.isValid(argument)) {
					valueExists = true;
				}
				if (!keyValidator.isValid(argument) && !valueValidator.isValid(argument)) {
					throw new SyntaxException("The syntax validation of the command \"" + commandName +
							"\" failed, because the syntax of the argument token \"" + argument + "\" is invalid.");
				}
			}
		}
		if (!keyExists && valueExists) {
			throw new SyntaxException("The syntax validation of the command \"" + commandName + "\" failed, because the command " +
					"doesn't contain argument names, but at least one argument value. Every argument value must have an argument " +
					"name.");
		}
	}
}
