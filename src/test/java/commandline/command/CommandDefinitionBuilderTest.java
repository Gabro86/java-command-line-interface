package commandline.command;

import commandline.argument.ArgumentDefinitionList;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandDefinitionBuilderTest {
	@Test
	public void testCreate() throws Exception {
		CommandDefinitionBuilder builder;
		CommandDefinition expectedDefinition;
		CommandDefinition builderDefinition;
		String name;
		String description;
		ExecutableCommand commandToExecute;
		boolean argumentInjectionEnabled;
		ArgumentDefinitionList arguments;

		name = "mock-command";
		description = "mock-description";
		commandToExecute = new MockExecutableCommand();
		argumentInjectionEnabled = true;
		arguments = new ArgumentDefinitionList();
		expectedDefinition = new CommandDefinition(name, description, commandToExecute, argumentInjectionEnabled);

		builder = new CommandDefinitionBuilder();
		builder.setName(name);
		builder.setDescription(description);
		builder.setCommandToExecute(commandToExecute);
		builder.setArgumentInjectionEnabled(argumentInjectionEnabled);
		builder.setArguments(arguments);
		builderDefinition = builder.create();

		assertEquals(expectedDefinition, builderDefinition);
	}
}