package commandline.argument.validator;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

/**
 * User: gno Date: 25.06.13 Time: 16:14
 */
public class FileArgumentValidator extends ArgumentValidator<File> {
	@Override
	public void validate(@Nullable File value) {
		if (value == null) {
			throw new ValidationException("The file failed, because the passed file is null.");
		}
		if (! value.exists()) {
			throw new ValidationException("The file failed, because the passed file does not exist.");
		}
		if (! value.isFile()) {
			throw new ValidationException("The file failed, because the passed file does not point to a file.");
		}
	}

	@NotNull
	@Override
	public Class<File> getSupportedClass() {
		return File.class;
	}
}
