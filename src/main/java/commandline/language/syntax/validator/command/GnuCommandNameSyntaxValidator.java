package commandline.language.syntax.validator.command;

import commandline.language.syntax.SyntaxException;
import commandline.exception.ArgumentNullException;

/**
 * User: gno Date: 28.06.13 Time: 15:52
 */
public class GnuCommandNameSyntaxValidator extends CommandNameValidator {
	public GnuCommandNameSyntaxValidator() {
		super();
	}

	@Override
	public void validate(String name) {
		String regex;

		if (name == null) {
			throw new ArgumentNullException();
		}
		regex = "[a-z0-9]+(-[a-z0-9]+)*";
		if (!name.matches(regex)) {
			throw new SyntaxException(String.format(
					"The of the command name \"%s\" failed, because it contains syntax errors.", name));
		}
	}
}
