package commandline.language.syntax.validator;

/**
 * User: gno Date: 28.06.13 Time: 12:20
 */
public abstract class TokenSyntaxValidator {
	public TokenSyntaxValidator() {
		super();
	}

	public abstract void validate(String token);
}
