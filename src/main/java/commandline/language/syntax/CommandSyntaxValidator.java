package commandline.language.syntax;

import org.jetbrains.annotations.Nullable;

/**
 * User: gno Date: 28.06.13 Time: 12:14
 */
public abstract class CommandSyntaxValidator {
	public CommandSyntaxValidator() {
		super();
	}

	public abstract void validate(@Nullable String[] cliArguments);
}
