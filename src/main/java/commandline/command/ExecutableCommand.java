package commandline.command;

import commandline.annotation.CliArgument;
import commandline.annotation.CliCommand;
import commandline.argument.Argument;
import commandline.argument.validator.DefaultArgumentValidator;
import commandline.command.help.print.CommandHelpPrinter;
import commandline.exception.ArgumentNullException;
import commandline.language.parser.specific.BooleanArgumentParser;
import org.jetbrains.annotations.NotNull;

@CliCommand(name = "executable-command", description = "This is a command description.")
public abstract class ExecutableCommand {
	public static final String ARGUMENT_HELP_SHORT_NAME = "h";
	public static final String ARGUMENT_HELP_LONG_NAME = "help";
	public static final String ARGUMENT_HELP_DESCRIPTION = "Shows the help for this command";
	public static final boolean ARGUMENT_HELP_OBLIGATORY = false;
	public static final String ARGUMENT_HELP_DEFAULT_VALUE = "false";
	public static final Class<BooleanArgumentParser> ARGUMENT_PARSER_CLASS = BooleanArgumentParser.class;
	public static final BooleanArgumentParser ARGUMENT_PARSER = new BooleanArgumentParser();
	public static final Class<DefaultArgumentValidator> ARGUMENT_VALIDATOR_CLASS = DefaultArgumentValidator.class;
	public static final DefaultArgumentValidator ARGUMENT_VALIDATOR = new DefaultArgumentValidator();
	public static final Class<Boolean> ARGUMENT_VALUE_CLASS = Boolean.class;
	public static final String ARGUMENT_HELP_EXAMPLE = "true";
	public static final String[] ARGUMENT_HELP_EXAMPLES = new String[] {ARGUMENT_HELP_EXAMPLE};

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
			examples = {ARGUMENT_HELP_EXAMPLE},
			description = ARGUMENT_HELP_DESCRIPTION)
	public void setShowHelp(boolean showHelp) {
		this.showHelp = showHelp;
	}

	void baseExecute(@NotNull Command command) {
		Argument<?> helpArgument;
		CommandHelpPrinter printer;
		Boolean showHelp;
		CommandInjector injector;

		if (command == null) {
			throw new ArgumentNullException();
		}

		/*
		 * It's important to test if the command uses annotations and therefore supports argument injection, because there are
		 * some commands that don't support argument injection. The command class itself decides if it supports argument injection
		 * or not. There is no global field that defines if argument injection is enabled or disabled for the whole cli.
		 */
		if (getClass().getAnnotation(CliCommand.class) != null) {
			injector = new CommandInjector();
			injector.inject(command, this);
		}

		/*
		 * If the user passed the cli argument "--help" or "-h" the help for the command is printed. The printing is executed in this
		 * method and not in the method execute(...), because the command should not been executed,
		 * when the user requested the command help. If the help printing would be done inside the method execute(...) the sub
		 * classes could override the method execute(...) and accidentally override the routines responsible for the command help
		 * printing. It must not be possible to remove the command help printing in the subclasses of ExecutableCommand.
		 */
		showHelp = false;
		helpArgument = command.getArgument(ExecutableCommand.ARGUMENT_HELP_LONG_NAME);
		if (helpArgument != null) {
			showHelp = (Boolean) helpArgument.getValue();
		}
		if (showHelp) {
			printer = new CommandHelpPrinter(command.getDefinition(), System.out);
			printer.print();
		} else {
			execute(command);
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

	public abstract void execute(@NotNull Command command);
}