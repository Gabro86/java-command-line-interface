package commandline.language.parser.argument;

/**
 * User: gno Date: 25.06.13 Time: 12:24
 */
public abstract class ArgumentParser<T> {
	public ArgumentParser() {
		super();
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean isCompatible(Class<?> clazz) {
		return clazz.isAssignableFrom(getValueType());
	}

	public abstract T parse(String value) throws ArgumentParseException;

	public abstract Class<T> getValueType();
}
