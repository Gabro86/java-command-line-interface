package commandline.language.syntax;

import org.jetbrains.annotations.Nullable;

/**
 * User: gno Date: 28.06.13 Time: 12:20
 */
public abstract class TokenSyntaxValidator {
	public TokenSyntaxValidator() {
		super();
	}

	public abstract void validate(@Nullable String token);
}