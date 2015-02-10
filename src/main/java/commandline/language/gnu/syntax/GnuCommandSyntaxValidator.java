package commandline.language.gnu.syntax;

import commandline.language.syntax.CommandSyntaxValidator;
import commandline.language.syntax.SyntaxException;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

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
		String key;
		String value;
		String[] arguments;
		String commandName;

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
		if (!nameValidator.isValid(commandName)) {
			throw new SyntaxException("The syntax validation of the command failed, because the syntax of the command name \"" +
					commandName + "\" is invalid.");
		}

		//Validates the keys and values of the cli arguments
		if (commandTokens.length > 1) {
			arguments = Arrays.copyOfRange(commandTokens, 1, commandTokens.length);
			//The number of tokens must be even, because every key must have a value.
			if (arguments.length % 2 != 0) {
				throw new SyntaxException("The syntax validation of the command \"" + commandName +
						"\" failed, because at least one key does not have a value or vice versa.");
			}

			keyValidator = new GnuKeySyntaxValidator();
			valueValidator = new GnuValueSyntaxValidator();
			for (int i = 0; i < arguments.length / 2; i += 2) {
				key = arguments[i];
				value = arguments[i + 1];
				if (!keyValidator.isValid(key)) {
					throw new SyntaxException("The syntax validation of the command \"" + commandName +
							"\" failed, because the syntax of the key \"" + key + "\" is invalid.");
				}
				if (!valueValidator.isValid(value)) {
					throw new SyntaxException("The syntax validation of the command \"" + commandName +
							"\" failed, because the syntax of the value \"" + value + "\" is invalid.");
				}
			}
		}
	}
}
