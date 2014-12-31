package commandline.language.parser.argument;

import commandline.exception.ArgumentNullException;
import org.jetbrains.annotations.NotNull;

/**
 * User: gno, Date: 09.07.13 - 10:46
 */
public class FloatArgumentParser extends ArgumentParser<Float> {
	public FloatArgumentParser() {
		super();
	}

	@Override
	public boolean isCompatible(Class<?> clazz) {
		return super.isCompatible(clazz) || clazz.equals(float.class);
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

	@NotNull
	@Override
	public Class<Float> getValueType() {
		return Float.class;
	}
}
