package commandline.argument.validator;

import commandline.exception.ArgumentNullException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This class validates values of command line arguments.
 */
public abstract class ArgumentValidator<T> {
	/**
	 * Creates an instance of this class.
	 */
	public ArgumentValidator() {
		super();
	}

	/**
	 * Tests if a class can be validated by this validator class.
	 * @param clazz Class to test
	 * @return Returns true, when the passed class is compatible with this validator class and false otherwise.
	 */
	public boolean isCompatible(@NotNull Class<?> clazz) {
		if (clazz == null) {
			throw new ArgumentNullException();
		}
		return getSupportedClass().isAssignableFrom(clazz);
	}

	/**
	 * Validates an command line argument value
	 * @param value Value to validate
	 */
	public abstract void validate(@Nullable T value);

	/**
	 * Returns the class that can be validated by this validator class.
	 * @return Returns the class that can be validated by this validator class.
	 */
	@NotNull
	public abstract Class<T> getSupportedClass();
}