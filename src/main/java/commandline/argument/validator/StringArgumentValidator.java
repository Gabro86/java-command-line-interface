package commandline.argument.validator;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * User: gno, Date: 09.07.13 - 10:53
 */
public class StringArgumentValidator extends ArgumentValidator<String> {
	public StringArgumentValidator() {
		super();
	}

	@Override
	public void validate(@Nullable String value) {
	}

	@NotNull
	@Override
	public Class<String> getSupportedClass() {
		return String.class;
	}
}
