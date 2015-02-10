package commandline.command;

import commandline.annotation.CliArgument;
import commandline.annotation.CliCommand;
import commandline.argument.validator.DefaultArgumentValidator;
import commandline.command.help.print.CommandHelpPrinter;
import commandline.language.parser.specific.StringArgumentParser;
import org.jetbrains.annotations.NotNull;

@CliCommand(name = "executable-command", description = "This is a command description.")
public class ExecutableCommand {
	public static final String ARGUMENT_HELP_SHORT_NAME = "h";
	public static final String ARGUMENT_HELP_LONG_NAME = "help";
	public static final String ARGUMENT_HELP_DESCRIPTION = "Shows the help for this command.";
	public static final boolean ARGUMENT_HELP_OBLIGATORY = false;
	public static final String ARGUMENT_HELP_DEFAULT_VALUE = "false";
	public static final Class<StringArgumentParser> ARGUMENT_PARSER_CLASS = StringArgumentParser.class;
	public static final Class<DefaultArgumentValidator> ARGUMENT_VALIDATOR_CLASS = DefaultArgumentValidator.class;
	public static final Class<String> ARGUMENT_VALUE_CLASS = String.class;
	public static final String ARGUMENT_HELP_EXAMPLE1 = "false";
	public static final String ARGUMENT_HELP_EXAMPLE2 = "true";
	public static final String[] ARGUMENT_HELP_EXAMPLES = new String[] {ARGUMENT_HELP_EXAMPLE1, ARGUMENT_HELP_EXAMPLE2};

	private boolean showHelp;

	protected ExecutableCommand() {
		super();
		setShowHelp(false);
	}

	public boolean isShowHelp() {
		return this.showHelp;
	}

	@CliArgument(shortName = ARGUMENT_HELP_SHORT_NAME, longName = ARGUMENT_HELP_LONG_NAME,
			obligatory = ARGUMENT_HELP_OBLIGATORY,
			defaultValue = ARGUMENT_HELP_DEFAULT_VALUE,
			examples = {ARGUMENT_HELP_EXAMPLE1, ARGUMENT_HELP_EXAMPLE2},
			description = ARGUMENT_HELP_DESCRIPTION)
	public void setShowHelp(boolean showHelp) {
		this.showHelp = showHelp;
	}

	public void execute(@NotNull Command command) {
		CommandHelpPrinter printer;

		if (isShowHelp()) {
			printer = new CommandHelpPrinter(command.getDefinition(), System.out);
			printer.print();
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof ExecutableCommand)) {
			return false;
		}

		ExecutableCommand that = (ExecutableCommand) o;

		if (this.showHelp != that.showHelp) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return (this.showHelp ? 1 : 0);
	}

	@Override
	public String toString() {
		return "ExecutableCommand{" +
				"showHelp=" + this.showHelp +
				'}';
	}
}