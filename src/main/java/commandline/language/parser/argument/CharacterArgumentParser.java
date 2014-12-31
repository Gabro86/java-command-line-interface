package commandline.language.parser.argument;

import commandline.exception.ArgumentNullException;

/**
 * User: gno, Date: 02.08.13 - 10:27
 */
public class CharacterArgumentParser extends ArgumentParser<Character> {
	public CharacterArgumentParser() {
		super();
	}

	@Override
	public boolean isCompatible(Class<?> clazz) {
		return super.isCompatible(clazz) || clazz.equals(char.class);
	}

	@Override
	public Character parse(String value) throws ArgumentParseException {
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

	@Override
	public Class<Character> getValueType() {
		return Character.class;
	}
}
