package commandline.language.parser.argument;

import commandline.exception.ArgumentNullException;
import org.jetbrains.annotations.NotNull;

/**
 * User: gno, Date: 09.07.13 - 10:46
 */
public class BooleanArgumentParser extends ArgumentParser<Boolean> {
	public BooleanArgumentParser() {
		super();
	}

	@Override
	public boolean isCompatible(Class<?> clazz) {
		return super.isCompatible(clazz) || clazz.equals(boolean.class);
	}

	@NotNull
	@Override
	public Boolean parse(@NotNull String value) {
		if (value == null) {
			throw new ArgumentNullException();
		}
		if (! value.equals("true") && ! value.equals("false")) {
			throw new ArgumentParseException(String.format(
					"The value \"%s\" could not been parsed to a boolean, because only the values true and false are accepted.",
					value));
		}
		try {
			return Boolean.valueOf(value);
		} catch (Exception e) {
			throw new ArgumentParseException(e.getMessage(), e);
		}
	}

	@NotNull
	@Override
	public Class<Boolean> getValueType() {
		return Boolean.class;
	}
}
