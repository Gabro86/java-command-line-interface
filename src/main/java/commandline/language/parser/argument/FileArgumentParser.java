package commandline.language.parser.argument;

import commandline.exception.ArgumentNullException;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * User: gno Date: 25.06.13 Time: 16:16
 */
public class FileArgumentParser extends ArgumentParser<File> {
	@NotNull
	@Override
	public File parse(@NotNull String value) {
		if (value == null) {
			throw new ArgumentNullException();
		}
		return new File(String.valueOf(value));
	}

	@NotNull
	@Override
	public Class<File> getValueType() {
		return File.class;
	}
}
