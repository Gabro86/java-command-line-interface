package commandline.argument.validator;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * User: gno, Date: 09.02.2015 - 13:36
 */
public class MockInvalidArgumentValidator extends ArgumentValidator<String> {
	public MockInvalidArgumentValidator() {
		super();
	}

	@Override
	public boolean isCompatible(@NotNull Class<?> clazz) {
		return false;
	}

	@Override
	public void validate(@Nullable String value) {
		throw new ValidationException("This is a mock validation exception message.");
	}

	@NotNull
	@Override
	public Class<String> getSupportedClass() {
		return String.class;
	}
}
