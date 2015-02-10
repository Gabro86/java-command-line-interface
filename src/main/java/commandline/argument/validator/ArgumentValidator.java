package commandline.argument.validator;

import commandline.exception.ArgumentNullException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * User: gno Date: 25.06.13 Time: 16:00
 */
public abstract class ArgumentValidator<T> {
	public ArgumentValidator() {
		super();
	}

	public boolean isCompatible(@NotNull Class<?> clazz) {
		if (clazz == null) {
			throw new ArgumentNullException();
		}
		return getSupportedClass().isAssignableFrom(clazz);
	}

	public abstract void validate(@Nullable T value);
	@NotNull
	public abstract Class<T> getSupportedClass();
}