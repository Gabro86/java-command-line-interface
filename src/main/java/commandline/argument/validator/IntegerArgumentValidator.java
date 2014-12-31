package commandline.argument.validator;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * User: gno, Date: 09.07.13 - 10:50
 */
public class IntegerArgumentValidator extends ArgumentValidator<Integer> {
	public IntegerArgumentValidator() {
		super();
	}

	@Override
	public void validate(@Nullable Integer value) {
	}

	@NotNull
	@Override
	public Class<Integer> getSupportedClass() {
		return Integer.class;
	}
}
