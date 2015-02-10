package commandline.language.parser;

import org.jetbrains.annotations.NotNull;

/**
 * User: gno Date: 25.06.13 Time: 12:24
 */
public abstract class ArgumentParser<T> {
	public ArgumentParser() {
		super();
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean isCompatible(@NotNull Class<?> clazz) {
		return clazz.isAssignableFrom(getValueClass());
	}

	@NotNull
	public abstract T parse(@NotNull String value) throws ArgumentParseException;

	@NotNull
	public abstract Class<T> getValueClass();
}
