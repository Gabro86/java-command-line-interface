package commandline.language.syntax;

import org.jetbrains.annotations.Nullable;

/**
 * User: gno Date: 28.06.13 Time: 15:51
 */
public abstract class CommandNameSyntaxValidator {
	public CommandNameSyntaxValidator() {
		super();
	}

	public abstract void validate(@Nullable String name);
	public abstract boolean isValid(@Nullable String name);
}
