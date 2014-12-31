package commandline.language.syntax.validator.command;

import commandline.command.CommandLineException;
import commandline.language.syntax.SyntaxException;
import commandline.language.syntax.validator.key.GnuKeySyntaxValidator;
import commandline.language.syntax.validator.value.GnuValueSyntaxValidator;
import commandline.exception.ArgumentNullException;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * User: gno Date: 28.06.13 Time: 12:18
 */
public class GnuCommandSyntaxValidator extends CommandSyntaxValidator {
	public GnuCommandSyntaxValidator() {
		super();
	}

	@Override
	public void validate(@NotNull String[] commandTokens) {
		GnuCommandNameSyntaxValidator nameValidator;
		GnuKeySyntaxValidator keyValidator;
		GnuValueSyntaxValidator valueValidator;
		String key;
		String value;
		String[] arguments;

		if (commandTokens == null) {
			throw new ArgumentNullException();
		}

		//When the argument array contains at least one command name, a key and a value the key and value will be validated.
		if (commandTokens.length == 0) {
			throw new SyntaxException("The validation of the command failed, because the command does not contain a command " +
					"name or command arguments");
		}
		nameValidator = new GnuCommandNameSyntaxValidator();
		try {
			nameValidator.validate(commandTokens[0]);
		} catch (ArgumentNullException e) {
			throw new CommandLineException(e.getMessage(), e);
		}

		//Validates the keys and values, when the command tokens contain more tokens beside the command name token.
		if (commandTokens.length > 1) {
			//The number of tokens must be even, because every key must have a value.
			arguments = Arrays.copyOfRange(commandTokens, 1, commandTokens.length);
			if (arguments.length % 2 != 0) {
				throw new SyntaxException(
						"The validation of the command failed, because at least one key does not have a value or vice versa.");
			}

			keyValidator = new GnuKeySyntaxValidator();
			valueValidator = new GnuValueSyntaxValidator();
			for (int i = 0; i < arguments.length / 2; i += 2) {
				key = arguments[i];
				value = arguments[i + 1];
				if (!keyValidator.isValid(key)) {
					throw new SyntaxException(String.format(
							"The validation of the command failed, because the syntax of the key \"%s\" is invalid.", key));
				}
				if (!valueValidator.isValid(value)) {
					throw new SyntaxException(String.format(
							"The validation of the command failed, because the syntax of the value \"%s\" is invalid.", value));
				}
			}
		}
	}
}
