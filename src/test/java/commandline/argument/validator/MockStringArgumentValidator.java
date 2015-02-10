package commandline.argument.validator;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * User: gno, Date: 09.02.2015 - 13:47
 */
public class MockStringArgumentValidator extends ArgumentValidator<String> {
	public MockStringArgumentValidator() {
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
