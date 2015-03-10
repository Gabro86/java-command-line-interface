package commandline.language.parser.specific;

import commandline.exception.ArgumentNullException;
import commandline.language.parser.ArgumentParser;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * User: gno Date: 25.06.13 Time: 16:16
 */
public class FileArgumentParser extends ArgumentParser<File> {
	@Override
	public boolean isCompatibleWithOutputClass(@NotNull Class<?> clazz) {
		return clazz.isAssignableFrom(File.class);
	}

	@NotNull
	@Override
	public File parse(@NotNull String value) {
		if (value == null) {
			throw new ArgumentNullException();
		}
		return new File(String.valueOf(value));
	}
}
