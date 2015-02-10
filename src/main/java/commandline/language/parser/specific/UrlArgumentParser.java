package commandline.language.parser.specific;

import commandline.language.parser.ArgumentParseException;
import commandline.language.parser.ArgumentParser;
import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * User: gno, Date: 09.08.13 - 09:59
 */
public class UrlArgumentParser extends ArgumentParser<URL> {
	public UrlArgumentParser() {
		super();
	}

	@NotNull
	@Override
	public URL parse(@NotNull String value) {
		try {
			return new URL(value);
		} catch (MalformedURLException e) {
			throw new ArgumentParseException(e.getMessage(), e);
		}
	}

	@NotNull
	@Override
	public Class<URL> getValueClass() {
		return URL.class;
	}
}
