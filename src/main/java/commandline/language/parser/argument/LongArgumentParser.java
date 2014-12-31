package commandline.language.parser.argument;

import commandline.exception.ArgumentNullException;
import org.jetbrains.annotations.NotNull;

/**
 * User: gno, Date: 09.07.13 - 10:46
 */
public class LongArgumentParser extends ArgumentParser<Long> {
	public LongArgumentParser() {
		super();
	}

	@Override
	public boolean isCompatible(Class<?> clazz) {
		return super.isCompatible(clazz) || clazz.equals(long.class);
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

	@NotNull
	@Override
	public Class<Long> getValueType() {
		return Long.class;
	}
}
