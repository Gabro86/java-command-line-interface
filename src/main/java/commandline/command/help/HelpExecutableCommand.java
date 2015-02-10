package commandline.command.help;

import commandline.annotation.CliArgument;
import commandline.annotation.CliCommand;
import commandline.command.Command;
import commandline.command.CommandDefinition;
import commandline.command.CommandDefinitionList;
import commandline.command.CommandLineException;
import commandline.command.ExecutableCommand;
import commandline.command.help.print.CommandHelpPrinter;
import commandline.command.help.print.CommandListHelpPrinter;
import commandline.command.help.print.HelpPrinter;
import commandline.exception.ArgumentNullException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("SameParameterValue")
@CliCommand(name = HelpCommandDefinition.COMMAND_NAME, description = HelpCommandDefinition.COMMAND_DESCRIPTION)
public class HelpExecutableCommand extends ExecutableCommand {
	@NotNull
	private final CommandDefinitionList commandDefinitions;
	@Nullable
	private String askedCommandName;

	public HelpExecutableCommand(@NotNull CommandDefinitionList commandDefinitions) {
		if (commandDefinitions == null) {
			throw new ArgumentNullException();
		}
		this.commandDefinitions = commandDefinitions;
		setAskedCommandName(null);
	}

	@NotNull
	public CommandDefinitionList getCommandDefinitions() {
		return this.commandDefinitions;
	}

	@Nullable
	public String getAskedCommandName() {
		return this.askedCommandName;
	}

	@CliArgument(shortName = "c", longName = "command", obligatory = false, isDefaultValueNull = true,
			examples = {"login", "add", "edit"}, description = "Shows help information for a specific command.")
	public void setAskedCommandName(@Nullable String askedCommandName) {
		this.askedCommandName = askedCommandName;
	}

	@Override
	public void execute(@NotNull Command command) {
		HelpPrinter printer;
		String commandName;
		CommandDefinition commandDefinitionToPrint;

		commandName = getAskedCommandName();
		if (commandName == null) {
			printer = new CommandListHelpPrinter(getCommandDefinitions(), System.out);
		} else {
			commandDefinitionToPrint = getCommandDefinitions().get(commandName);
			if (commandDefinitionToPrint == null) {
				throw new CommandLineException("The help for the command name \"" + commandName + "\" could not been showed, " +
						"because the command is not defined.");
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

		if (this.askedCommandName != null ? !this.askedCommandName.equals(that.askedCommandName) : that.askedCommandName != null) {
			return false;
		}
		if (!this.commandDefinitions.equals(that.commandDefinitions)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + this.commandDefinitions.hashCode();
		result = 31 * result + (this.askedCommandName != null ? this.askedCommandName.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "HelpExecutableCommand{" +
				"commandDefinitions=" + this.commandDefinitions +
				", askedCommandName='" + this.askedCommandName + '\'' +
				"} " + super.toString();
	}

	public static String getCommandName() {
		return HelpCommandDefinition.COMMAND_NAME;
	}
}
