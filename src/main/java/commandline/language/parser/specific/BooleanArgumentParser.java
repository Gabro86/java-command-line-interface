package commandline.language.parser.specific;

import commandline.exception.ArgumentNullException;
import commandline.language.parser.ArgumentParseException;
import commandline.language.parser.ArgumentParser;
import org.jetbrains.annotations.NotNull;

/**
 * User: gno, Date: 09.07.13 - 10:46
 */
public class BooleanArgumentParser extends ArgumentParser<Boolean> {
	public BooleanArgumentParser() {
		super();
	}

	@Override
	public boolean isParsedValueClassCompatible(@NotNull Class<?> clazz) {
		return clazz.isAssignableFrom(boolean.class) || clazz.isAssignableFrom(Boolean.class);
	}

	@NotNull
	@Override
	public Boolean parse(@NotNull String value) {
		if (value == null) {
			throw new ArgumentNullException();
		}
		if (!value.equals("true") && !value.equals("false")) {
			throw new ArgumentParseException(String.format(
					"The value \"%s\" could not been parsed to a boolean, because only the values true and false are accepted.",
					value));
		}
		return Boolean.valueOf(value);
	}
}
