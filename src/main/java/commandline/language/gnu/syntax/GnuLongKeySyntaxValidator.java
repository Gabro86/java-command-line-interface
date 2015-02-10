package commandline.language.gnu.syntax;

import commandline.language.syntax.SyntaxException;
import commandline.language.syntax.TokenSyntaxValidator;
import org.jetbrains.annotations.Nullable;

/**
 * User: gno Date: 28.06.13 Time: 12:21
 */
public class GnuLongKeySyntaxValidator extends TokenSyntaxValidator {
	public GnuLongKeySyntaxValidator() {
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
		regex = "--[a-z0-9]+(-[a-z0-9]+)*[a-z0-9]";
		if (key.matches(regex)) {
			return true;
		}

		return false;
	}
}