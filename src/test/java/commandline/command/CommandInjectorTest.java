package commandline.command;

import commandline.argument.Argument;
import commandline.argument.ArgumentDefinition;
import commandline.argument.ArgumentDefinitionBuilder;
import commandline.argument.ArgumentList;
import commandline.argument.validator.DefaultArgumentValidator;
import commandline.language.parser.specific.IntegerArgumentParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: gno, Date: 02.08.13 - 11:21
 */
public class CommandInjectorTest {
	public CommandInjectorTest() {
		super();
	}

	@Test
	public void testInjectByArgumentList() throws Exception {
		MockExecutableCommand command;
		ArgumentList arguments;
		Argument<String> argument;
		String value;
		CommandInjector injector;
		ArgumentDefinitionBuilder argumentDefinitionBuilder;
		ArgumentDefinition argumentDefinition;

		argumentDefinitionBuilder = new ArgumentDefinitionBuilder();
		argumentDefinitionBuilder.setLongName(MockExecutableCommand.ARGUMENT_TEST_LONG_NAME);
		argumentDefinitionBuilder.setShortName(MockExecutableCommand.ARGUMENT_TEST_SHORT_NAME);
		argumentDefinitionBuilder.setValueClass(MockExecutableCommand.ARGUMENT_TEST_VALUE_CLASS);
		argumentDefinitionBuilder.setParserClass(MockExecutableCommand.ARGUMENT_TEST_PARSER);
		argumentDefinitionBuilder.setValidatorClass(MockExecutableCommand.ARGUMENT_TEST_VALIDATOR);
		argumentDefinitionBuilder.setObligatory(MockExecutableCommand.ARGUMENT_TEST_OBLIGATORY);
		argumentDefinitionBuilder.setDefaultValue(MockExecutableCommand.ARGUMENT_TEST_DEFAULT_VALUE);
		argumentDefinitionBuilder.setDescription(MockExecutableCommand.ARGUMENT_TEST_DESCRIPTION);
		argumentDefinitionBuilder.setExamples(MockExecutableCommand.ARGUMENT_TEST_EXAMPLES);
		argumentDefinition = argumentDefinitionBuilder.create();

		//Creates the argument list with the only argument of TestCommand with the argument value
		value = "value1";
		argument = new Argument<>(argumentDefinition, value);
		arguments = new ArgumentList();
		arguments.add(argument);

		command = new MockExecutableCommand();
		injector = new CommandInjector();
		injector.inject(arguments, command);
		assertEquals(value, command.getTestValue());
	}

	@Test(expected = CommandLineException.class)
	public void testInjectByArgumentList_WrongArgumentValueClass() throws Exception {
		MockExecutableCommand command;
		ArgumentList arguments;
		Argument<Integer> argument;
		ArgumentDefinition argumentDefinition;
		CommandInjector injector;
		ArgumentDefinitionBuilder argumentDefinitionBuilder;

		argumentDefinitionBuilder = new ArgumentDefinitionBuilder();
		argumentDefinitionBuilder.setLongName(MockExecutableCommand.ARGUMENT_TEST_LONG_NAME);
		argumentDefinitionBuilder.setShortName(MockExecutableCommand.ARGUMENT_TEST_SHORT_NAME);
		argumentDefinitionBuilder.setValueClass(MockExecutableCommand.ARGUMENT_TEST_VALUE_CLASS);
		argumentDefinitionBuilder.setParserClass(MockExecutableCommand.ARGUMENT_TEST_PARSER);
		argumentDefinitionBuilder.setValidatorClass(MockExecutableCommand.ARGUMENT_TEST_VALIDATOR);
		argumentDefinitionBuilder.setObligatory(MockExecutableCommand.ARGUMENT_TEST_OBLIGATORY);
		argumentDefinitionBuilder.setDefaultValue(MockExecutableCommand.ARGUMENT_TEST_DEFAULT_VALUE);
		argumentDefinitionBuilder.setDescription(MockExecutableCommand.ARGUMENT_TEST_DESCRIPTION);
		argumentDefinitionBuilder.setExamples(MockExecutableCommand.ARGUMENT_TEST_EXAMPLES);
		argumentDefinition = argumentDefinitionBuilder.create();

		argument = new Argument<>(argumentDefinition, 42);
		arguments = new ArgumentList();
		arguments.add(argument);

		command = new MockExecutableCommand();
		injector = new CommandInjector();
		injector.inject(arguments, command);
	}

	@Test(expected = CommandLineException.class)
	public void testInjectByArgumentList_ArgumentDefinitionAndCliAnnotationMissmatch() throws Exception {
		MockExecutableCommand command;
		ArgumentList arguments;
		Argument<Integer> argument;
		ArgumentDefinition argumentDefinition;
		CommandInjector injector;
		ArgumentDefinitionBuilder argumentDefinitionBuilder;

		argumentDefinitionBuilder = new ArgumentDefinitionBuilder();
		argumentDefinitionBuilder.setLongName(MockExecutableCommand.ARGUMENT_TEST_LONG_NAME);
		argumentDefinitionBuilder.setShortName(MockExecutableCommand.ARGUMENT_TEST_SHORT_NAME);
		argumentDefinitionBuilder.setValueClass(Integer.class);
		argumentDefinitionBuilder.setParserClass(IntegerArgumentParser.class);
		argumentDefinitionBuilder.setValidatorClass(DefaultArgumentValidator.class);
		argumentDefinitionBuilder.setObligatory(false);
		argumentDefinitionBuilder.setDefaultValue("100");
		argumentDefinitionBuilder.setDescription("This is some random description.");
		argumentDefinitionBuilder.setExamples(new String[] {"42", "111"});
		argumentDefinition = argumentDefinitionBuilder.create();

		argument = new Argument<>(argumentDefinition, 42);
		arguments = new ArgumentList();
		arguments.add(argument);

		command = new MockExecutableCommand();
		injector = new CommandInjector();
		injector.inject(arguments, command);
	}

	@Test
	public void testInjectByArgumentList_NoMatchingMethodForArgumentDefinition() throws Exception {
		MockExecutableCommand command;
		ArgumentList arguments;
		Argument<Integer> argument;
		ArgumentDefinition argumentDefinition;
		CommandInjector injector;
		ArgumentDefinitionBuilder argumentDefinitionBuilder;

		argumentDefinitionBuilder = new ArgumentDefinitionBuilder();
		argumentDefinitionBuilder.setLongName("random-long-name");
		argumentDefinitionBuilder.setShortName("r");
		argumentDefinitionBuilder.setValueClass(Integer.class);
		argumentDefinitionBuilder.setParserClass(IntegerArgumentParser.class);
		argumentDefinitionBuilder.setValidatorClass(DefaultArgumentValidator.class);
		argumentDefinitionBuilder.setObligatory(false);
		argumentDefinitionBuilder.setDefaultValue("100");
		argumentDefinitionBuilder.setDescription("This is some random description.");
		argumentDefinitionBuilder.setExamples(new String[] {"42", "111"});
		argumentDefinition = argumentDefinitionBuilder.create();

		argument = new Argument<>(argumentDefinition, 42);
		arguments = new ArgumentList();
		arguments.add(argument);

		command = new MockExecutableCommand();
		injector = new CommandInjector();
		injector.inject(arguments, command);
	}

	@Test
	public void testInjectByCommand_WrongArgumentValueClass() throws Exception {
		MockExecutableCommand commandToInject;
		Argument<String> argument;
		Command command;
		ArgumentDefinition argumentDefinition;
		CommandInjector injector;
		ArgumentDefinitionBuilder argumentDefinitionBuilder;
		CommandDefinitionBuilder commandDefinitionBuilder;
		CommandDefinition commandDefinition;
		String argumentValue;

		argumentDefinitionBuilder = new ArgumentDefinitionBuilder();
		argumentDefinitionBuilder.setLongName(MockExecutableCommand.ARGUMENT_TEST_LONG_NAME);
		argumentDefinitionBuilder.setShortName(MockExecutableCommand.ARGUMENT_TEST_SHORT_NAME);
		argumentDefinitionBuilder.setValueClass(MockExecutableCommand.ARGUMENT_TEST_VALUE_CLASS);
		argumentDefinitionBuilder.setParserClass(MockExecutableCommand.ARGUMENT_TEST_PARSER);
		argumentDefinitionBuilder.setValidatorClass(MockExecutableCommand.ARGUMENT_TEST_VALIDATOR);
		argumentDefinitionBuilder.setObligatory(MockExecutableCommand.ARGUMENT_TEST_OBLIGATORY);
		argumentDefinitionBuilder.setDefaultValue(MockExecutableCommand.ARGUMENT_TEST_DEFAULT_VALUE);
		argumentDefinitionBuilder.setDescription(MockExecutableCommand.ARGUMENT_TEST_DESCRIPTION);
		argumentDefinitionBuilder.setExamples(MockExecutableCommand.ARGUMENT_TEST_EXAMPLES);
		argumentDefinition = argumentDefinitionBuilder.create();
		argumentValue = "bazinga";
		argument = new Argument<>(argumentDefinition, argumentValue);

		commandToInject = new MockExecutableCommand();
		commandDefinitionBuilder = new CommandDefinitionBuilder();
		commandDefinitionBuilder.setName(MockExecutableCommand.COMMAND_NAME);
		commandDefinitionBuilder.setDescription(MockExecutableCommand.COMMAND_DESCRIPTION);
		commandDefinitionBuilder.setArgumentInjectionEnabled(true);
		commandDefinitionBuilder.setCommandToExecute(commandToInject);
		commandDefinitionBuilder.addArgument(argumentDefinition);
		commandDefinition = commandDefinitionBuilder.create();
		command = new Command(commandDefinition);
		command.addArgument(argument);

		injector = new CommandInjector();
		injector.inject(command, commandToInject);
		assertEquals(argumentValue, commandToInject.getTestValue());
	}
}