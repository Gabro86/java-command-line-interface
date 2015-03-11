package commandline.command.help;

import commandline.annotation.CliArgument;
import commandline.annotation.CliCommand;
import commandline.argument.validator.DefaultArgumentValidator;
import commandline.command.Command;
import commandline.command.CommandDefinition;
import commandline.command.CommandDefinitionList;
import commandline.command.CommandDefinitionReader;
import commandline.command.CommandLineException;
import commandline.command.ExecutableCommand;
import commandline.command.help.print.CommandHelpPrinter;
import commandline.command.help.print.CommandListHelpPrinter;
import commandline.command.help.print.HelpPrinter;
import commandline.exception.ArgumentNullException;
import commandline.language.parser.specific.StringArgumentParser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@CliCommand(name = HelpExecutableCommand.COMMAND_NAME, description = HelpExecutableCommand.COMMAND_DESCRIPTION)
public class HelpExecutableCommand extends ExecutableCommand {
	public static final String COMMAND_NAME = "help";
	public static final String COMMAND_DESCRIPTION = "Shows the applications help";
	public static final String ARGUMENT_COMMAND_NAME_SHORT_NAME = "c";
	public static final String ARGUMENT_COMMAND_NAME_LONG_NAME = "command";
	public static final String ARGUMENT_COMMAND_NAME_DESCRIPTION = "The command to show the help for";
	public static final boolean ARGUMENT_COMMAND_NAME_OBLIGATORY = false;
	public static final String ARGUMENT_COMMAND_NAME_DEFAULT_VALUE = null;
	public static final Class<StringArgumentParser> ARGUMENT_COMMAND_NAME_PARSER_CLASS = StringArgumentParser.class;
	public static final Class<DefaultArgumentValidator> ARGUMENT_COMMAND_NAME_VALIDATOR_CLASS = DefaultArgumentValidator.class;
	public static final Class<String> ARGUMENT_COMMAND_NAME_VALUE_CLASS = String.class;
	public static final String ARGUMENT_COMMAND_NAME_EXAMPLE1 = "false";
	public static final String ARGUMENT_COMMAND_NAME_EXAMPLE2 = "true";
	public static final String[] ARGUMENT_COMMAND_NAME_EXAMPLES =
			new String[] {ARGUMENT_COMMAND_NAME_EXAMPLE1, ARGUMENT_COMMAND_NAME_EXAMPLE2};

	@NotNull
	private final CommandDefinitionList commandDefinitions;
	@Nullable
	private String commandToShowHelpFor;

	public HelpExecutableCommand(@NotNull CommandDefinitionList commandDefinitions) {
		if (commandDefinitions == null) {
			throw new ArgumentNullException();
		}
		this.commandDefinitions = commandDefinitions;
		this.commandToShowHelpFor = null;
	}

	@NotNull
	public CommandDefinitionList getCommandDefinitions() {
		return this.commandDefinitions;
	}

	@Nullable
	public String getCommandToShowHelpFor() {
		return this.commandToShowHelpFor;
	}

	@CliArgument(shortName = ARGUMENT_COMMAND_NAME_SHORT_NAME, longName = ARGUMENT_COMMAND_NAME_LONG_NAME,
			obligatory = ARGUMENT_COMMAND_NAME_OBLIGATORY,
			examples = {ARGUMENT_COMMAND_NAME_EXAMPLE1, ARGUMENT_COMMAND_NAME_EXAMPLE2},
			description = ARGUMENT_COMMAND_NAME_DESCRIPTION)
	public void setCommandToShowHelpFor(@Nullable String commandToShowHelpFor) {
		this.commandToShowHelpFor = commandToShowHelpFor;
	}

	@Override
	public void execute(@NotNull Command command) {
		HelpPrinter printer;
		String commandToShowHelpFor;
		CommandDefinition commandDefinitionToPrint;

		commandToShowHelpFor = getCommandToShowHelpFor();
		if (commandToShowHelpFor == null) {
			printer = new CommandListHelpPrinter(getCommandDefinitions(), System.out);
		} else {
			commandDefinitionToPrint = getCommandDefinitions().get(commandToShowHelpFor);
			if (commandDefinitionToPrint == null) {
				throw new CommandLineException("The help for the command name \"" + commandToShowHelpFor + "\" could " +
						"not been showed, because the command is not defined.");
			}
			printer = new CommandHelpPrinter(commandDefinitionToPrint, System.out);
		}
		printer.print();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof HelpExecutableCommand)) {
			return false;
		}
		if (!super.equals(o)) {
			return false;
		}

		HelpExecutableCommand that = (HelpExecutableCommand) o;

		if (!this.commandDefinitions.equals(that.commandDefinitions)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + this.commandDefinitions.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "HelpExecutableCommand{" +
				"commandDefinitions=" + this.commandDefinitions +
				"} " + super.toString();
	}

	public static CommandDefinition readDefinitionFromAnnotations(CommandDefinitionList commandsToShowHelpFor) {
		CommandDefinitionReader reader;
		CommandDefinition commandDefinition;
		HelpExecutableCommand command;

		if (commandsToShowHelpFor == null) {
			throw new ArgumentNullException();
		}
		reader = new CommandDefinitionReader();
		command = new HelpExecutableCommand(commandsToShowHelpFor);
		commandDefinition = reader.readCommandDefinition(command);

		return commandDefinition;
	}
}
