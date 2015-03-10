package commandline.language.parser;

import org.jetbrains.annotations.NotNull;

/**
 * User: gno, Date: 02.08.13 - 12:59
 */
public class MockArgumentParser extends ArgumentParser<Object> {
	public MockArgumentParser() {
		super();
	}

	@Override
	public boolean isCompatibleWithOutputClass(@NotNull Class<?> clazz) {
		return clazz.isAssignableFrom(Object.class);
	}

	@NotNull
	@Override
	public Object parse(@NotNull String value) throws ArgumentParseException {
		return value;
	}
}