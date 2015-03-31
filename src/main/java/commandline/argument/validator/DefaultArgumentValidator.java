package commandline.argument.validator;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This class is a mock validator class that is used when no validator should be used.
 */
public class DefaultArgumentValidator extends ArgumentValidator<Object> {
	/**
	 * Creates an instance of this class.
	 */
	public DefaultArgumentValidator() {
		super();
	}

	/**
	 * This method doesn't do anything.
	 * @param value Value to validate
	 */
	@Override
	public void validate(@Nullable Object value) {
	}

	/**
	 * Returns the class that is supported by this validator class.
	 * @return Returns {@link java.lang.Object}
	 */
	@NotNull
	@Override
	public Class<Object> getSupportedClass() {
		return Object.class;
	}

	/**
	 * Tests if a class can be validated by this validator class.
	 * @param clazz Class to test
	 * @return Returns always true
	 */
	@Override
	public boolean isCompatible(@NotNull Class<?> clazz) {
		return true;
	}
}
