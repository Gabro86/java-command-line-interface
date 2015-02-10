package commandline.language.parser.specific;

import commandline.exception.ArgumentNullException;
import commandline.language.parser.ArgumentParseException;
import commandline.language.parser.ArgumentParser;
import org.jetbrains.annotations.NotNull;

/**
 * User: gno, Date: 02.08.13 - 10:27
 */
public class CharacterArgumentParser extends ArgumentParser<Character> {
	public CharacterArgumentParser() {
		super();
	}

	@NotNull
	@Override
	public Character parse(@NotNull String value) throws ArgumentParseException {
		if (value == null) {
			throw new ArgumentNullException();
		}
		if (value.isEmpty()) {
			throw new ArgumentParseException("The argument could not been parsed to an character, because the passed " +
					"value doesn't contain any character.");
		} else if (value.length() > 1) {
			throw new ArgumentParseException("The argument could not been parsed to an character, because the passed " +
					"value contains more than one character.");
		}
		try {
			return value.charAt(0);
		} catch (Exception e) {
			throw new ArgumentParseException(e.getMessage(), e);
		}
	}

	@NotNull
	@Override
	public Class<Character> getValueClass() {
		return Character.class;
	}
}
