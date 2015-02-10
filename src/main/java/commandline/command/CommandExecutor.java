package commandline.command;

import commandline.exception.ArgumentNullException;
import commandline.language.CommandLineLanguage;
import commandline.language.parser.CommandParser;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CommandExecutor {
	@NotNull
	private final CommandLineLanguage commandLineLanguage;

	public CommandExecutor(@NotNull CommandLineLanguage commandLineLanguage) {
		super();
		if (commandLineLanguage == null) {
			throw new ArgumentNullException();
		}
		this.commandLineLanguage = commandLineLanguage;
	}

	@NotNull
	public CommandLineLanguage getCommandLineLanguage() {
		return this.commandLineLanguage;
	}

	public Command execute(@NotNull String[] cliArguments, @NotNull CommandDefinitionList commandDefinitions) {
		Command command;
		CommandParser commandParser;

		if (cliArguments == null) {
			throw new ArgumentNullException();
		}
		if (commandDefinitions == null) {
			throw new ArgumentNullException();
		}

		commandParser = new CommandParser(getCommandLineLanguage(), commandDefinitions);
		command = commandParser.parse(cliArguments);
		command.execute();

		return command;
	}

	public Command execute(@NotNull String[] cliArguments, @NotNull List<ExecutableCommand> commands) {
		CommandDefinition definition;
		CommandDefinitionList definitions;
		CommandDefinitionReader reader;
		Command command;
		CommandParser commandParser;

		if (cliArguments == null) {
			throw new ArgumentNullException();
		}
		if (commands == null) {
			throw new ArgumentNullException();
		}

		reader = new CommandDefinitionReader();
		definitions = new CommandDefinitionList();
		for (ExecutableCommand executableCommand : commands) {
			definition = reader.readCommandDefinition(executableCommand.getClass(), executableCommand);
			definitions.add(definition);
		}

		commandParser = new CommandParser(getCommandLineLanguage(), definitions);
		command = commandParser.parse(cliArguments);
		command.execute();

		return command;
	}
}