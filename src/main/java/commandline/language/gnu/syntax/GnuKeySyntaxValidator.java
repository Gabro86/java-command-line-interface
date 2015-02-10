package commandline.language.gnu.syntax;

import commandline.language.syntax.SyntaxException;
import commandline.language.syntax.TokenSyntaxValidator;
import org.jetbrains.annotations.Nullable;

/**
 * User: gno Date: 28.06.13 Time: 12:21
 */
public class GnuKeySyntaxValidator extends TokenSyntaxValidator {
	public GnuKeySyntaxValidator() {
		super();
	}

	@Override
	public void validate(@Nullable String key) {
		if (key == null) {
			throw new SyntaxException(String.format(
					"The syntax validation of the argument key \"%s\" failed, because the passed key is null.", key));
		}
		if (!isValid(key)) {
			throw new SyntaxException(String.format(
					"The syntax validation of the argument key \"%s\" failed, because the passed key contains syntax errors.", key));
		}
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean isValid(@Nullable String key) {
		GnuShortKeySyntaxValidator shortValidator;
		GnuLongKeySyntaxValidator longValidator;

		if (key == null) {
			return false;
		}
		shortValidator = new GnuShortKeySyntaxValidator();
		longValidator = new GnuLongKeySyntaxValidator();
		if (shortValidator.isValid(key) || longValidator.isValid(key)) {
			return true;
		}

		return false;
	}
}
