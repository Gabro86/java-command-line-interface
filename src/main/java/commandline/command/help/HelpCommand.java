package commandline.command.help;

import commandline.argument.metainfo.CommandArgument;
import commandline.command.Command;
import commandline.command.CommandLineException;
import commandline.command.CommandList;
import commandline.exception.ArgumentNullException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("SameParameterValue")
public class HelpCommand extends Command {
	@NotNull
	private final String applicationName;
	@NotNull
	private final CommandList commands;
	private String askedCommandName;

	public HelpCommand(@NotNull String applicationName, @NotNull CommandList commands) {
		super("help", "Shows the applications help.");
		if (applicationName == null) {
			throw new ArgumentNullException();
		}
		if (commands == null) {
			throw new ArgumentNullException();
		}
		this.applicationName = applicationName;
		this.commands = commands;
		setAskedCommandName(null);
	}

	@NotNull
	public String getApplicationName() {
		return this.applicationName;
	}

	@NotNull
	public CommandList getCommands() {
		return this.commands;
	}

	@Nullable
	public String getAskedCommandName() {
		return this.askedCommandName;
	}

	@CommandArgument(shortName = "c", longName = "command", obligatory = false, defaultToNull = true,
			examples = {"login", "add", "edit"}, description = "Shows help information for a specific command.")
	public void setAskedCommandName(@Nullable String askedCommandName) {
		this.askedCommandName = askedCommandName;
	}

	@Override
	public void execute() {
		HelpPrinter printer;
		String applicationName;
		String commandName;
		Command command;

		applicationName = getApplicationName();
		commandName = getAskedCommandName();
		if (commandName == null) {
			printer = new CommandListHelpPrinter(getCommands(), System.out, getApplicationName());
		} else {
			command = getCommands().get(commandName);
			if (command == null) {
				throw new CommandLineException(String.format(
						"The help for the command name \"%s\" could not been showed, because the command is not defined.",
						commandName));
			}
			printer = new CommandHelpPrinter(command, System.out, applicationName);
		}
		printer.print();
	}

	public static String getCommandName() {
		return "help";
	}
}
