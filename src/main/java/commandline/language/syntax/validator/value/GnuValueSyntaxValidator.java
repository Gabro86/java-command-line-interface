package commandline.language.syntax.validator.value;

import commandline.exception.ArgumentNullException;
import commandline.language.syntax.SyntaxException;
import commandline.language.syntax.validator.TokenSyntaxValidator;
import org.jetbrains.annotations.Nullable;

/**
 * User: gno Date: 28.06.13 Time: 12:21
 */
public class GnuValueSyntaxValidator extends TokenSyntaxValidator {
	public GnuValueSyntaxValidator() {
		super();
	}

	@Override
	public void validate(@Nullable String token) {
		if (!isValid(token)) {
			throw new SyntaxException("The validation of the argument value failed, because the passed value contains syntax errors");
		}
	}

	@SuppressWarnings({"EmptyCatchBlock", "BooleanMethodIsAlwaysInverted", "ResultOfMethodCallIgnored"})
	public boolean isValid(@Nullable String value) {
		String regex;

		if (value == null) {
			throw new ArgumentNullException();
		}
		/*
		 * Only values consisting solely of digits are allowed to have a '-' char at the beginning, because the char '-' is reserved
		 * for short command names. Therefore command names with only digits are not allowed,
		 * because it would be difficult to distinguish a negative number (e.g. -1245) from a command name (e.g. -2)
		 */
		try {
			Long.parseLong(value);
			return true;
		} catch (NumberFormatException e) {
		}
		regex = "^[^-]+.*";
		if (value.isEmpty() || value.matches(regex)) {
			return true;
		}

		return false;
	}
}
