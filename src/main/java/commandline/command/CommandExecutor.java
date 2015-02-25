package commandline.command;

import commandline.command.help.HelpExecutableCommand;
import commandline.exception.ArgumentNullException;
import commandline.language.CommandLineLanguage;
import commandline.language.parser.CommandNotFoundException;
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
		CommandDefinitionList editCommandDefinitions;
		String similarCommandsString;
		String message;

		if (cliArguments == null) {
			throw new ArgumentNullException();
		}
		if (commandDefinitions == null) {
			throw new ArgumentNullException();
		}

		editCommandDefinitions = new CommandDefinitionList();
		editCommandDefinitions.addAll(commandDefinitions.getCollection());
		editCommandDefinitions.add(HelpExecutableCommand.readDefinitionFromAnnotations(commandDefinitions));

		commandParser = new CommandParser(getCommandLineLanguage(), editCommandDefinitions);
		try {
			command = commandParser.parse(cliArguments);
		} catch (CommandNotFoundException e) {
			message = "The command \"" + e.getCommandName() + "\" is not defined.";
			similarCommandsString = createSimilarCommandsString(editCommandDefinitions, e.getCommandName());
			if (!similarCommandsString.isEmpty()) {
				message += " Did you mean: " + similarCommandsString;
			}
			throw new CommandNotFoundException(message, e, e.getCommandName());
		}
		command.execute();

		return command;
	}

	public Command execute(@NotNull String[] cliArguments, @NotNull List<ExecutableCommand> commands) {
		CommandDefinition definition;
		CommandDefinitionList definitions;
		CommandDefinitionReader reader;
		Command command;

		if (cliArguments == null) {
			throw new ArgumentNullException();
		}
		if (commands == null) {
			throw new ArgumentNullException();
		}

		reader = new CommandDefinitionReader();
		definitions = new CommandDefinitionList();
		for (ExecutableCommand executableCommand : commands) {
			definition = reader.readCommandDefinition(executableCommand);
			definitions.add(definition);
		}
		definitions.add(HelpExecutableCommand.readDefinitionFromAnnotations(definitions));
		command = execute(cliArguments, definitions);

		return command;
	}

	private String createSimilarCommandsString(CommandDefinitionList editCommandDefinitions, String commandName) {
		SimilarCommandFinder similarCommandFinder;
		List<CommandDefinition> similarDefinitions;
		StringBuilder similarDefinitionsBuilder;

		if (commandName == null) {
			throw new ArgumentNullException();
		}
		similarCommandFinder = new SimilarCommandFinder(editCommandDefinitions);
		similarDefinitions = similarCommandFinder.findSimilarCommands(commandName);
		similarDefinitionsBuilder = new StringBuilder();
		for (CommandDefinition definition : similarDefinitions) {
			similarDefinitionsBuilder.append(definition.getName());
			similarDefinitionsBuilder.append(", ");
		}
		if (similarDefinitionsBuilder.length() > 0) {
			similarDefinitionsBuilder.setLength(similarDefinitionsBuilder.length() - ", ".length());
		}

		return similarDefinitionsBuilder.toString().trim();
	}
}