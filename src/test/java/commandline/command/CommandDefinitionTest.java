package commandline.command;

import commandline.argument.ArgumentDefinition;
import commandline.argument.ArgumentDefinitionBuilder;
import commandline.argument.validator.DefaultArgumentValidator;
import commandline.command.mock.NoCliCommandAnnotationTestCommand;
import commandline.command.mock.ValidTestCommand;
import commandline.language.parser.specific.StringArgumentParser;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CommandDefinitionTest {
	@Test
	public void testIsArgumentsInjectionEnabled_InjectionIsEnabled() {
		ArgumentDefinition argumentDefinition;
		ArgumentDefinitionBuilder argumentDefinitionBuilder;
		CommandDefinitionBuilder commandDefinitionBuilder;
		CommandDefinition commandDefinition;

		argumentDefinitionBuilder = new ArgumentDefinitionBuilder();
		argumentDefinitionBuilder.setShortName(ValidTestCommand.ARGUMENT_1_SHORT_NAME);
		argumentDefinitionBuilder.setLongName(ValidTestCommand.ARGUMENT_1_LONG_NAME);
		argumentDefinitionBuilder.setObligatory(true);
		argumentDefinitionBuilder.setParser(new StringArgumentParser());
		argumentDefinitionBuilder.setValidator(new DefaultArgumentValidator());
		argumentDefinitionBuilder.setDescription(ValidTestCommand.COMMAND_DESCRIPTION);
		argumentDefinitionBuilder.setExamples(ValidTestCommand.ARGUMENT_HELP_EXAMPLES);
		argumentDefinition = argumentDefinitionBuilder.create();

		commandDefinitionBuilder = new CommandDefinitionBuilder();
		commandDefinitionBuilder.setName(ValidTestCommand.COMMAND_NAME);
		commandDefinitionBuilder.setDescription(ValidTestCommand.COMMAND_DESCRIPTION);
		commandDefinitionBuilder.setCommandToExecute(new ValidTestCommand());
		commandDefinitionBuilder.addArgument(argumentDefinition);
		commandDefinition = commandDefinitionBuilder.create();

		assertTrue(commandDefinition.isArgumentsInjectionEnabled());
	}

	@Test
	public void testIsArgumentsInjectionEnabled_InjectionIsDisabled() {
		CommandDefinitionBuilder commandDefinitionBuilder;
		CommandDefinition commandDefinition;

		commandDefinitionBuilder = new CommandDefinitionBuilder();
		commandDefinitionBuilder.setName(NoCliCommandAnnotationTestCommand.COMMAND_NAME);
		commandDefinitionBuilder.setDescription(NoCliCommandAnnotationTestCommand.COMMAND_DESCRIPTION);
		commandDefinitionBuilder.setCommandToExecute(new NoCliCommandAnnotationTestCommand());
		commandDefinition = commandDefinitionBuilder.create();
		assertFalse(commandDefinition.isArgumentsInjectionEnabled());
	}
}