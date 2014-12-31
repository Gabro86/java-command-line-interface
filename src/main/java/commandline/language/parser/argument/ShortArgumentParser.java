package commandline.language.parser.argument;

import commandline.exception.ArgumentNullException;
import org.jetbrains.annotations.NotNull;

/**
 * User: gno, Date: 02.08.13 - 10:21
 */
public class ShortArgumentParser extends ArgumentParser<Short> {
	public ShortArgumentParser() {
		super();
	}

	@Override
	public boolean isCompatible(Class<?> clazz) {
		return super.isCompatible(clazz) || clazz.equals(short.class);
	}

	@NotNull
	@Override
	public Short parse(@NotNull String value) {
		if (value == null) {
			throw new ArgumentNullException();
		}
		try {
			return Short.valueOf(value);
		} catch (Exception e) {
			throw new ArgumentParseException(e.getMessage(), e);
		}
	}

	@NotNull
	@Override
	public Class<Short> getValueType() {
		return Short.class;
	}
}