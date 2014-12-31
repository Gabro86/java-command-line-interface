package commandline.language.parser.argument;

/**
 * User: gno, Date: 02.08.13 - 12:59
 */
public class MockArgumentParser extends ArgumentParser<Object> {
	public MockArgumentParser() {
		super();
	}

	@Override
	public Object parse(String value) throws ArgumentParseException {
		return value;
	}

	@Override
	public Class<Object> getValueType() {
		return Object.class;
	}
}
