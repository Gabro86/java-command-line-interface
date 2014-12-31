package commandline.language.syntax.validator.key;

import commandline.language.syntax.SyntaxException;
import commandline.language.syntax.validator.TokenSyntaxValidator;
import commandline.exception.ArgumentNullException;
import org.jetbrains.annotations.NotNull;

/**
 * User: gno Date: 28.06.13 Time: 12:21
 */
public class GnuLongKeySyntaxValidator extends TokenSyntaxValidator {
	public GnuLongKeySyntaxValidator() {
		super();
	}

	@Override
	public void validate(@NotNull String key) {
		if (key == null) {
			throw new ArgumentNullException();
		}
		if (!isValid(key)) {
			throw new SyntaxException(String.format(
					"The validation of the long key argument \"%s\" failed, because it contains syntax errors.", key));
		}
	}

	@SuppressWarnings("ResultOfMethodCallIgnored")
	public boolean isValid(@NotNull String key) {
		String regex;

		if (key == null) {
			throw new ArgumentNullException();
		}
		/*
		 * Only values consisting solely of digits are allowed to have a '-' char at the beginning, because the char '-' is reserved
		 * for short command names. Therefore command names with only digits are not allowed,
		 * because it would be difficult to distinguish a negative number (e.g. -1245) from a command name (e.g. -2)
		 */
		try {
			Long.parseLong(key);
			return false;
		} catch (NumberFormatException ignored) {
		}
		regex = "--[a-z0-9]+(-[a-z0-9]+)*[a-z0-9]";
		if (key.matches(regex)) {
			return true;
		}

		return false;
	}
}