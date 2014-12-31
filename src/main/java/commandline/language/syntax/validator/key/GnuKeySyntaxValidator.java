package commandline.language.syntax.validator.key;

import commandline.language.syntax.SyntaxException;
import commandline.language.syntax.validator.TokenSyntaxValidator;
import commandline.exception.ArgumentNullException;
import org.jetbrains.annotations.NotNull;

/**
 * User: gno Date: 28.06.13 Time: 12:21
 */
public class GnuKeySyntaxValidator extends TokenSyntaxValidator {
	public GnuKeySyntaxValidator() {
		super();
	}

	@Override
	public void validate(@NotNull String key) {
		if (key == null) {
			throw new ArgumentNullException();
		}
		if (!isValid(key)) {
			throw new SyntaxException(String.format(
					"The validation of the argument key \"%s\" failed, because the passed key contains syntax errors.", key));
		}
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean isValid(@NotNull String key) {
		GnuShortKeySyntaxValidator shortValidator;
		GnuLongKeySyntaxValidator longValidator;

		if (key == null) {
			throw new ArgumentNullException();
		}
		shortValidator = new GnuShortKeySyntaxValidator();
		longValidator = new GnuLongKeySyntaxValidator();
		if (shortValidator.isValid(key) || longValidator.isValid(key)) {
			return true;
		}

		return false;
	}
}
