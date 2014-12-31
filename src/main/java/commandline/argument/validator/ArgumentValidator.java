package commandline.argument.validator;

/**
 * User: gno Date: 25.06.13 Time: 16:00
 */
public abstract class ArgumentValidator<T> {
	public ArgumentValidator() {
		super();
	}

	public boolean isCompatible(Class<?> clazz) {
		return getSupportedClass().isAssignableFrom(clazz);
	}

	public abstract void validate(T value);
	public abstract Class<T> getSupportedClass();
}
