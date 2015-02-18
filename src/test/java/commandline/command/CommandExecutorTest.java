package commandline.command;

import commandline.argument.Argument;
import commandline.argument.ArgumentDefinition;
import commandline.argument.ArgumentDefinitionBuilder;
import commandline.argument.validator.DefaultArgumentValidator;
import commandline.command.mock.SingleArgumentTestCommand;
import commandline.language.gnu.GnuCommandLineLanguage;
import commandline.language.parser.specific.BooleanArgumentParser;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * User: gno, Date: 30.07.13 - 15:31
 */
public class CommandExecutorTest extends ExecutableCommand {
	public CommandExecutorTest() {
		super();
	}

	@Override
	public void execute(@NotNull Command command) {
	}

	@Test
	public void testExecuteByCommandDefinition() throws Exception {
		String cliCommand;
		String[] cliCommandTokens;
		Command commandBefore;
		Command commandAfter;
		CommandExecutor manager;
		CommandDefinitionList commandDefinitions;
		CommandDefinition commandDefinition;
		GnuCommandLineLanguage language;
		SingleArgumentTestCommand commandToExecute;
		ArgumentDefinition argumentDefinition;
		Argument<Boolean> argument;
		String argumentValue;
		CommandDefinitionBuilder commandDefinitionBuilder;
		ArgumentDefinitionBuilder argumentDefinitionBuilder;

		argumentDefinitionBuilder = new ArgumentDefinitionBuilder();
		argumentDefinitionBuilder.setLongName(ExecutableCommand.ARGUMENT_HELP_LONG_NAME);
		argumentDefinitionBuilder.setShortName(ExecutableCommand.ARGUMENT_HELP_SHORT_NAME);
		argumentDefinitionBuilder.setValueClass(Boolean.class);
		argumentDefinitionBuilder.setParser(new BooleanArgumentParser());
		argumentDefinitionBuilder.setValidator(new DefaultArgumentValidator());
		argumentDefinitionBuilder.setObligatory(ExecutableCommand.ARGUMENT_HELP_OBLIGATORY);
		argumentDefinitionBuilder.setDefaultValue(ExecutableCommand.ARGUMENT_HELP_DEFAULT_VALUE);
		argumentDefinitionBuilder.setDescription(ExecutableCommand.ARGUMENT_HELP_DESCRIPTION);
		argumentDefinitionBuilder.setExamples(ExecutableCommand.ARGUMENT_HELP_EXAMPLES);
		argumentDefinition = argumentDefinitionBuilder.create();

		commandToExecute = new SingleArgumentTestCommand();
		commandDefinitionBuilder = new CommandDefinitionBuilder();
		commandDefinitionBuilder.setName(SingleArgumentTestCommand.COMMAND_NAME);
		commandDefinitionBuilder.setDescription(SingleArgumentTestCommand.COMMAND_DESCRIPTION);
		commandDefinitionBuilder.setCommandToExecute(commandToExecute);
		commandDefinitionBuilder.addArgument(argumentDefinition);
		commandDefinition = commandDefinitionBuilder.create();

		commandDefinitions = new CommandDefinitionList();
		commandDefinitions.add(commandDefinition);

		argumentValue = "false";
		cliCommand = commandDefinition.getName() + " --" + argumentDefinition.getLongName() + " " + argumentValue;
		cliCommandTokens = cliCommand.split(" ");
		language = new GnuCommandLineLanguage();
		manager = new CommandExecutor(language);
		commandAfter = manager.execute(cliCommandTokens, commandDefinitions);

		argument = new Argument<>(argumentDefinition, false);
		commandBefore = new Command(commandDefinition);
		commandBefore.addArgument(argument);

		assertTrue(commandToExecute.isExecuted());
		assertEquals(commandBefore, commandAfter);
	}

	@Test
	public void testExecuteByExecutableCommand() throws Exception {
		String cliCommand;
		String[] cliCommandTokens;
		Command commandBefore;
		Command commandAfter;
		CommandExecutor manager;
		CommandDefinitionList commandDefinitions;
		CommandDefinition commandDefinition;
		GnuCommandLineLanguage language;
		SingleArgumentTestCommand commandToExecute;
		ArgumentDefinition argumentDefinition;
		Argument<Boolean> argument;
		String argumentValue;
		LinkedList<ExecutableCommand> executableCommands;
		CommandDefinitionBuilder commandDefinitionBuilder;
		ArgumentDefinitionBuilder argumentDefinitionBuilder;

		argumentDefinitionBuilder = new ArgumentDefinitionBuilder();
		argumentDefinitionBuilder.setLongName(ExecutableCommand.ARGUMENT_HELP_LONG_NAME);
		argumentDefinitionBuilder.setShortName(ExecutableCommand.ARGUMENT_HELP_SHORT_NAME);
		argumentDefinitionBuilder.setValueClass(Boolean.class);
		argumentDefinitionBuilder.setParser(new BooleanArgumentParser());
		argumentDefinitionBuilder.setValidator(new DefaultArgumentValidator());
		argumentDefinitionBuilder.setObligatory(ExecutableCommand.ARGUMENT_HELP_OBLIGATORY);
		argumentDefinitionBuilder.setDefaultValue(ExecutableCommand.ARGUMENT_HELP_DEFAULT_VALUE);
		argumentDefinitionBuilder.setDescription(ExecutableCommand.ARGUMENT_HELP_DESCRIPTION);
		argumentDefinitionBuilder.setExamples(ExecutableCommand.ARGUMENT_HELP_EXAMPLES);
		argumentDefinition = argumentDefinitionBuilder.create();

		commandToExecute = new SingleArgumentTestCommand();
		commandDefinitionBuilder = new CommandDefinitionBuilder();
		commandDefinitionBuilder.setName(SingleArgumentTestCommand.COMMAND_NAME);
		commandDefinitionBuilder.setDescription(SingleArgumentTestCommand.COMMAND_DESCRIPTION);
		commandDefinitionBuilder.setCommandToExecute(commandToExecute);
		commandDefinitionBuilder.addArgument(argumentDefinition);
		commandDefinition = commandDefinitionBuilder.create();

		commandDefinitions = new CommandDefinitionList();
		commandDefinitions.add(commandDefinition);

		argumentValue = "false";
		cliCommand = commandDefinition.getName() + " --" + argumentDefinition.getLongName() + " " + argumentValue;
		cliCommandTokens = cliCommand.split(" ");
		executableCommands = new LinkedList<>();
		executableCommands.add(commandToExecute);

		language = new GnuCommandLineLanguage();
		manager = new CommandExecutor(language);
		commandAfter = manager.execute(cliCommandTokens, executableCommands);

		argument = new Argument<>(argumentDefinition, false);
		commandBefore = new Command(commandDefinition);
		commandBefore.addArgument(argument);

		assertTrue(commandToExecute.isExecuted());
		assertEquals(commandBefore, commandAfter);
	}
}