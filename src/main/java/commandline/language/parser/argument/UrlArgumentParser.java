package commandline.language.parser.argument;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * User: gno, Date: 09.08.13 - 09:59
 */
public class UrlArgumentParser extends ArgumentParser<URL> {
	public UrlArgumentParser() {
		super();
	}

	@Override
	public URL parse(String value) throws ArgumentParseException {
		try {
			return new URL(value);
		} catch (MalformedURLException e) {
			throw new ArgumentParseException(e.getMessage(), e);
		}
	}

	@Override
	public Class<URL> getValueType() {
		return URL.class;
	}
}
