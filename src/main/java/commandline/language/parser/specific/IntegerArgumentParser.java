package commandline.language.parser.specific;

import commandline.exception.ArgumentNullException;
import commandline.language.parser.ArgumentParseException;
import commandline.language.parser.ArgumentParser;
import org.jetbrains.annotations.NotNull;

/**
 * User: gno, Date: 09.07.13 - 10:46
 */
public class IntegerArgumentParser extends ArgumentParser<Integer> {
	public IntegerArgumentParser() {
		super();
	}

	@Override
	public boolean isCompatibleWithOutputClass(@NotNull Class<?> clazz) {
		return clazz.isAssignableFrom(int.class) || clazz.isAssignableFrom(Integer.class);
	}

	@NotNull
	@Override
	public Integer parse(@NotNull String value) {
		if (value == null) {
			throw new ArgumentNullException();
		}
		try {
			return Integer.valueOf(value);
		} catch (Exception e) {
			throw new ArgumentParseException(e.getMessage(), e);
		}
	}
}
