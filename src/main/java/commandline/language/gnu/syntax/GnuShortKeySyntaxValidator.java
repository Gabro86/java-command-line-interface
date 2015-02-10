package commandline.language.gnu.syntax;

import commandline.language.syntax.SyntaxException;
import commandline.language.syntax.TokenSyntaxValidator;
import org.jetbrains.annotations.Nullable;

/**
 * User: gno Date: 28.06.13 Time: 12:21
 */
public class GnuShortKeySyntaxValidator extends TokenSyntaxValidator {
	public static final String KEY_CHAR_REGEX = "[a-z0-9]";

	public GnuShortKeySyntaxValidator() {
		super();
	}

	@Override
	public void validate(@Nullable String key) {
		if (key == null) {
			throw new SyntaxException(String.format("The syntax validation of the key \"%s\" failed, because the passed key is null.",
					key));
		}
		if (!isValid(key)) {
			throw new SyntaxException(String.format(
					"The syntax validation of the key \"%s\" failed, because it contains syntax errors.", key));
		}
	}

	@SuppressWarnings("ResultOfMethodCallIgnored")
	public boolean isValid(@Nullable String key) {
		String regex;

		if (key == null) {
			return false;
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
		regex = "-" + KEY_CHAR_REGEX;
		if (key.matches(regex)) {
			return true;
		}

		return false;
	}
}
