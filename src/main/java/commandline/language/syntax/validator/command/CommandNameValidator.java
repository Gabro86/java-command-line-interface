package commandline.language.syntax.validator.command;

/**
 * User: gno Date: 28.06.13 Time: 15:51
 */
public abstract class CommandNameValidator {
	public CommandNameValidator() {
		super();
	}

	public abstract void validate(String name);
}
