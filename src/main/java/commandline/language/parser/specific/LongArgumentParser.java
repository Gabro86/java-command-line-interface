package commandline.language.parser.specific;

import commandline.exception.ArgumentNullException;
import commandline.language.parser.ArgumentParseException;
import commandline.language.parser.ArgumentParser;
import org.jetbrains.annotations.NotNull;

/**
 * User: gno, Date: 09.07.13 - 10:46
 */
public class LongArgumentParser extends ArgumentParser<Long> {
	public LongArgumentParser() {
		super();
	}

	@Override
	public boolean isParsedValueClassCompatible(@NotNull Class<?> clazz) {
		return clazz.isAssignableFrom(long.class) || clazz.isAssignableFrom(Long.class);
	}

	@NotNull
	@Override
	public Long parse(@NotNull String value) {
		if (value == null) {
			throw new ArgumentNullException();
		}
		try {
			return Long.valueOf(value);
		} catch (Exception e) {
			throw new ArgumentParseException(e.getMessage(), e);
		}
	}
}
