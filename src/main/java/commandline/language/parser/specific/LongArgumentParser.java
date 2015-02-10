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
	public Class<Long> getValueClass() {
		return Long.class;
	}
}
