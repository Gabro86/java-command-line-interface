package commandline.language.parser.specific;

import commandline.exception.ArgumentNullException;
import commandline.language.parser.ArgumentParser;
import org.jetbrains.annotations.NotNull;

/**
 * User: gno Date: 25.06.13 Time: 15:57
 */
public class StringArgumentParser extends ArgumentParser<String> {
	@NotNull
	@Override
	public String parse(@NotNull String value) {
		if (value == null) {
			throw new ArgumentNullException();
		}
		return value;
	}

	@NotNull
	@Override
	public Class<String> getValueClass() {
		return String.class;
	}
}
