package commandline.language.parser;

import org.jetbrains.annotations.NotNull;

/**
 * User: gno Date: 25.06.13 Time: 12:24
 */
public abstract class ArgumentParser<OutputValueClass> {
	public ArgumentParser() {
		super();
	}

	@NotNull
	public abstract OutputValueClass parse(@NotNull String value) throws ArgumentParseException;
	public abstract boolean isCompatibleWithOutputClass(@NotNull Class<?> clazz);
}