package commandline.language.syntax.validator.command;

/**
 * User: gno Date: 28.06.13 Time: 12:14
 */
public abstract class CommandSyntaxValidator {
	public CommandSyntaxValidator() {
		super();
	}

	public abstract void validate(String[] cliArguments);
}
