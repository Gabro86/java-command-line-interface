package commandline.argument.validator;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * User: gno Date: 25.06.13 Time: 16:11
 */
public class DefaultArgumentValidator extends ArgumentValidator<Object> {
	@Override
	public void validate(@Nullable Object value) {
	}

	@NotNull
	@Override
	public Class<Object> getSupportedClass() {
		return Object.class;
	}

	@Override
	public boolean isCompatible(@NotNull Class<?> clazz) {
		return true;
	}
}
