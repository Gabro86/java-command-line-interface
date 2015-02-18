package commandline.language.parser.specific;

import commandline.exception.ArgumentNullException;
import commandline.language.parser.ArgumentParseException;
import commandline.language.parser.ArgumentParser;
import org.jetbrains.annotations.NotNull;

/**
 * User: gno, Date: 09.07.13 - 10:46
 */
public class FloatArgumentParser extends ArgumentParser<Float> {
	public FloatArgumentParser() {
		super();
	}

	@Override
	public boolean isCompatible(@NotNull Class<?> clazz) {
		return clazz.isAssignableFrom(float.class) || clazz.isAssignableFrom(Float.class);
	}

	@NotNull
	@Override
	public Float parse(@NotNull String value) {
		if (value == null) {
			throw new ArgumentNullException();
		}
		try {
			return Float.valueOf(value);
		} catch (Exception e) {
			throw new ArgumentParseException(e.getMessage(), e);
		}
	}
}
