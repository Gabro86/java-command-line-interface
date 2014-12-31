package commandline.command;

import commandline.argument.ArgumentList;
import commandline.argument.metainfo.ArgumentMetaInfo;
import commandline.argument.metainfo.ArgumentMetaInfoExtractor;
import commandline.language.CommandLineLanguage;
import commandline.language.parser.argument.ArgumentsParser;
import commandline.language.syntax.validator.command.CommandSyntaxValidator;
import commandline.exception.ArgumentNullException;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CommandExecutor {
	@NotNull
	private final CommandList commands;
	@NotNull
	private final CommandLineLanguage language;

	public CommandExecutor(@NotNull CommandList commands, @NotNull CommandLineLanguage language) {
		super();
		if (commands == null) {
			throw new ArgumentNullException();
		}
		if (language == null) {
			throw new ArgumentNullException();
		}
		this.commands = commands;
		this.language = language;
	}

	@NotNull
	public CommandList getCommands() {
		return this.commands;
	}

	@NotNull
	public CommandLineLanguage getLanguage() {
		return this.language;
	}

	public void execute(@NotNull String[] cliArguments) {
		Command command;
		String name;
		ArgumentList arguments;
		List<ArgumentMetaInfo> metaInfos;
		CommandSyntaxValidator syntaxValidator;
		ArgumentsParser parser;
		CommandInjector injector;
		ArgumentMetaInfoExtractor extractor;

		if (cliArguments == null) {
			throw new ArgumentNullException();
		}
		if (cliArguments.length == 0) {
			throw new CommandLineException("The command could not been executed, because no command name was passed.");
		}

		syntaxValidator = getLanguage().getSyntaxValidator();
		syntaxValidator.validate(cliArguments);

		name = cliArguments[0];
		if (name == null || name.isEmpty()) {
			throw new CommandLineException("The command could not been executed, because no command name was passed.");
		}
		command = getCommands().get(name);
		if (command == null) {
			throw new CommandLineException(
					"The command \"" + name + "\" could not been executed, because it's not defined in the command list.");
		}

		extractor = new ArgumentMetaInfoExtractor();
		metaInfos = extractor.extract(command.getClass());
		parser = getLanguage().getArgumentsParser();
		arguments = parser.parse(cliArguments, metaInfos);

		injector = new CommandInjector();
		injector.inject(arguments, command);

		command.execute();
	}
}
