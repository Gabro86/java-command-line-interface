package commandline.language.parser.argument;

import commandline.exception.ArgumentNullException;
import org.jetbrains.annotations.NotNull;

/**
 * User: gno, Date: 09.07.13 - 10:46
 */
public class IntegerArgumentParser extends ArgumentParser<Integer> {
	public IntegerArgumentParser() {
		super();
	}

	@Override
	public boolean isCompatible(Class<?> clazz) {
		return super.isCompatible(clazz) || clazz.equals(int.class);
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

	@NotNull
	@Override
	public Class<Integer> getValueType() {
		return Integer.class;
	}
}
