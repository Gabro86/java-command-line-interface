package commandline.language.parser;

import commandline.argument.Argument;
import commandline.argument.ArgumentDefinition;
import commandline.argument.GenericArgument;
import commandline.command.Command;
import commandline.command.CommandDefinition;
import commandline.command.CommandDefinitionList;
import commandline.command.CommandLineException;
import commandline.command.ExecutableCommand;
import commandline.command.GenericCommand;
import commandline.exception.ArgumentNullException;
import commandline.language.CommandLineLanguage;
import org.jetbrains.annotations.NotNull;

/**
 * User: gno, Date: 06.01.2015 - 17:00
 */
public class CommandParser {
	@NotNull
	private final CommandDefinitionList commandDefinitions;
	@NotNull
	private final CommandLineLanguage commandLineLanguage;

	public CommandParser(@NotNull CommandLineLanguage commandLineLanguage, @NotNull CommandDefinitionList commandDefinitions) {
		super();
		if (commandDefinitions == null) {
			throw new ArgumentNullException();
		}
		if (commandLineLanguage == null) {
			throw new ArgumentNullException();
		}
		this.commandDefinitions = commandDefinitions;
		this.commandLineLanguage = commandLineLanguage;
	}

	@NotNull
	public CommandDefinitionList getCommandDefinitions() {
		return this.commandDefinitions;
	}

	@NotNull
	public CommandLineLanguage getCommandLineLanguage() {
		return this.commandLineLanguage;
	}

	@NotNull
	public Command parse(@NotNull String[] cliArguments) {
		GenericCommandParser parser;
		GenericCommand genericCommand;
		GenericArgument shortGenericArgument;
		GenericArgument longGenericArgument;
		GenericArgument genericArgument;
		String argumentLongName;
		String argumentShortName;
		String argumentName;
		Argument<?> typeSafeArgument;
		ArgumentDefinition argumentDefinition;
		ArgumentDefinition helpArgumentDefinition;
		String commandName;
		Command typeSafeCommand;
		CommandDefinition commandDefinition;
		ArgumentParser<?> argumentParser;
		Object parsedValue;
		String genericArgumentValue;

		if (cliArguments == null) {
			throw new ArgumentNullException();
		}
		if (cliArguments.length == 0 || cliArguments[0] == null || cliArguments[0].trim().isEmpty()) {
			throw new CommandParseException("The cli command could not been parsed, because it doesn't contain a command name.");
		}
		commandName = cliArguments[0].trim();

		/*
		 * Retrieves the command definition for passed the cli arguments. The command definition will be used to validate the cli
		 * arguments.
		 */
		commandDefinition = getCommandDefinitions().get(commandName);
		if (commandDefinition == null) {
			throw new CommandParseException("The cli command \"" + commandName + "\" could not been parsed, because the " +
					"corresponding command definition was not found.");
		}

		/*
		 * Parses the cli arguments into a GenericCommand instance. This is necessary, because the values of the cli arguments must
		 * be from the right data type, before putting them into the type-safe Command instance. Therefore the values will be parsed
		 * into GenericCommand instances. The values in the GenericCommand are validated and if they are valid they will be converted
		 * into a type-safe Command instance. Of course it would be possible to combine the parsing and type checking into one step,
		 * but separating this two steps makes the code more readable and cleaner.
		 */
		parser = getCommandLineLanguage().getGenericCommandParser();
		genericCommand = parser.parse(cliArguments);

		/*
		 * In case the cli arguments contain the help argument, only the help argument is parsed and the other arguments are
		 * ignored, because the help arguments indicates that the user wants to print the help for the command on the console and
		 * doesn't want to execute the command.
		 */
		shortGenericArgument = genericCommand.getArgument(ExecutableCommand.ARGUMENT_HELP_SHORT_NAME);
		longGenericArgument = genericCommand.getArgument(ExecutableCommand.ARGUMENT_HELP_LONG_NAME);
		if (longGenericArgument != null) {
			genericArgument = longGenericArgument;
		} else {
			genericArgument = shortGenericArgument;
		}

		if (genericArgument != null) {
			helpArgumentDefinition = commandDefinition.getArgumentDefinition(genericArgument.getName());
			typeSafeArgument = new Argument<>(helpArgumentDefinition, Boolean.parseBoolean(genericArgument.getValue()));
			typeSafeCommand = new Command(commandDefinition);
			typeSafeCommand.addArgument(typeSafeArgument);
			return typeSafeCommand;
		}

		//Tests if the passed cli arguments contain arguments that are not defined for the command.
		for (GenericArgument argument : genericCommand) {
			argumentName = argument.getName();
			argumentDefinition = commandDefinition.getArgumentDefinition(argumentName);
			if (argumentDefinition == null) {
				throw new CommandParseException("The cli command \"" + commandName + "\" could not been parsed, because the cli " +
						"arguments contain the invalid argument \"" + argumentName + "\" that is not defined for the command");
			}
		}

		//Creates a type-safe Command instance from the GenericCommand instance
		typeSafeCommand = new Command(commandDefinition);
		for (ArgumentDefinition definition : commandDefinition) {
			argumentLongName = definition.getLongName();
			argumentShortName = definition.getShortName();
			shortGenericArgument = genericCommand.getArgument(argumentLongName);
			longGenericArgument = genericCommand.getArgument(argumentShortName);

			/*
			 * Tests if the argument was passed throw cli. If it was not passed and the argument is not obligatory the default value
			 * will be used.
			 */
			if (shortGenericArgument == null && longGenericArgument == null) {
				if (definition.isObligatory()) {
					throw new CommandParseException("The cli command  \"" + commandName + "\" could not been parsed, " +
							"because the cli command \"" + commandName + "\" doesn't contain the obligatory argument \"" +
							definition.getLongName() + " (" + definition.getShortName() + ")\".");
				} else {
					//Since this argument was not passed throw cli the default value will be used.
					longGenericArgument = new GenericArgument(definition.getLongName(), definition.getDefaultValue());
				}
			}

			//Tests if the passed cli argument has a short and a long name. Only one of them can be passed as cli argument.
			if (shortGenericArgument != null && longGenericArgument != null) {
				throw new CommandParseException("The cli command \"" + commandName + "\" could not been parsed, because the cli " +
						"command contains the argument long name \"" + argumentLongName + "\" and the argument short name \"" +
						argumentShortName + "\". Both arguments belong to the same argument. You can use only the long name or the " +
						"short name, but not both in the same command.");
			}

			/*
			 * Converts the GenericArgument instance into a type-safe Argument instance and puts it into the type-safe Command
			 * instance
			 */
			if (longGenericArgument != null) {
				genericArgument = longGenericArgument;
			} else {
				genericArgument = shortGenericArgument;
			}

			/*
			 * The argument value can be omitted on boolean arguments (flags). A missing argument value on a boolean argument is
			 * interpreted as the argument value "true".
			 */
			genericArgumentValue = genericArgument.getValue().trim();
			if (genericArgumentValue.isEmpty() && definition.getValueClass().equals(Boolean.class)) {
				genericArgumentValue = "true";
			}

			try {
				argumentParser = definition.getParserClass().newInstance();
			} catch (IllegalAccessException | InstantiationException e) {
				throw new CommandLineException(e.getMessage(), e);
			}
			parsedValue = argumentParser.parse(genericArgumentValue);
			typeSafeArgument = new Argument<>(definition, parsedValue);
			typeSafeCommand.addArgument(typeSafeArgument);
		}

		return typeSafeCommand;
	}
}
