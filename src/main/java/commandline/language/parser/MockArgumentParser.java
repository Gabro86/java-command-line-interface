package commandline.language.parser;

import org.jetbrains.annotations.NotNull;

/**
 * User: gno, Date: 02.08.13 - 12:59
 */
public class MockArgumentParser extends ArgumentParser<Object> {
	public MockArgumentParser() {
		super();
	}

	@NotNull
	@Override
	public Object parse(@NotNull String value) throws ArgumentParseException {
		return value;
	}

	@NotNull
	@Override
	public Class<Object> getValueClass() {
		return Object.class;
	}
}
