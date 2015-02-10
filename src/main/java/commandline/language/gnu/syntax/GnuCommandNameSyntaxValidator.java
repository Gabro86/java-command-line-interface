package commandline.language.gnu.syntax;

import commandline.language.syntax.CommandNameSyntaxValidator;
import commandline.language.syntax.SyntaxException;
import org.jetbrains.annotations.Nullable;

/**
 * User: gno Date: 28.06.13 Time: 15:52
 */
public class GnuCommandNameSyntaxValidator extends CommandNameSyntaxValidator {
	private static final String REGEX = "[a-z0-9]+(-[a-z0-9]+)*";

	public GnuCommandNameSyntaxValidator() {
		super();
	}

	@Override
	public void validate(@Nullable String name) {
		if (name == null) {
			throw new SyntaxException(String.format("The of the command name \"%s\" failed, because the passed name is null.", name));
		}
		if (!name.matches(REGEX)) {
			throw new SyntaxException(String.format("The of the command name \"%s\" failed, because it contains syntax errors.",
					name));
		}
	}

	@Override
	public boolean isValid(@Nullable String name) {
		if (name == null) {
			return false;
		}
		if (!name.matches(REGEX)) {
			return false;
		}

		return true;
	}
}
