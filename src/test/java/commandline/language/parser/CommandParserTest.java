package commandline.language.parser;

import commandline.argument.Argument;
import commandline.argument.ArgumentDefinition;
import commandline.argument.ArgumentDefinitionBuilder;
import commandline.argument.validator.DefaultArgumentValidator;
import commandline.command.Command;
import commandline.command.CommandDefinition;
import commandline.command.CommandDefinitionBuilder;
import commandline.command.CommandDefinitionList;
import commandline.command.MockExecutableCommand;
import commandline.command.help.HelpCommandDefinition;
import commandline.language.gnu.GnuCommandLineLanguage;
import commandline.language.parser.specific.StringArgumentParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandParserTest {
	@Test
	public void testParse() throws Exception {
		CommandParser parser;
		CommandDefinitionList commandDefinitions;
		String[] stringArguments;
		CommandDefinitionBuilder commandDefinitionBuilder;
		ArgumentDefinitionBuilder argumentDefinitionBuilder;
		ArgumentDefinition argumentDefinition;
		CommandDefinition commandDefinition;
		Command commandBefore;
		Command commandAfter;
		Argument<String> argument;

		argumentDefinitionBuilder = new ArgumentDefinitionBuilder();
		argumentDefinitionBuilder.setLongName("test-key");
		argumentDefinitionBuilder.setShortName("t");
		argumentDefinitionBuilder.setDescription("This is a test argument.");
		argumentDefinitionBuilder.setValueClass(String.class);
		argumentDefinitionBuilder.setValidatorClass(DefaultArgumentValidator.class);
		argumentDefinitionBuilder.setParserClass(StringArgumentParser.class);
		argumentDefinitionBuilder.setDefaultValue("test-default-value");
		argumentDefinitionBuilder.setExamples(new String[] {"example"});
		argumentDefinitionBuilder.setObligatory(false);
		argumentDefinition = argumentDefinitionBuilder.create();

		commandDefinitionBuilder = new CommandDefinitionBuilder();
		commandDefinitionBuilder.setName("test-command");
		commandDefinitionBuilder.setDescription("This is a test command description.");
		commandDefinitionBuilder.setCommandToExecute(new MockExecutableCommand());
		commandDefinitionBuilder.setArgumentInjectionEnabled(false);
		commandDefinition = commandDefinitionBuilder.create();
		commandDefinition.addArgumentDefinition(argumentDefinition);
		commandDefinitions = new CommandDefinitionList();
		commandDefinitions.add(commandDefinition);
		commandDefinitions.add(new HelpCommandDefinition(commandDefinitions));
		commandBefore = new Command(commandDefinition);

		argument = new Argument<>(argumentDefinition, "test-value");
		commandBefore.addArgument(argument);

		stringArguments = "test-command --test-key test-value".split(" ");
		parser = new CommandParser(new GnuCommandLineLanguage(), commandDefinitions);
		commandAfter = parser.parse(stringArguments);
		assertEquals(commandBefore, commandAfter);
	}

	@Test
	public void testParse_ArgumentNotPassedUseDefaultValue() throws Exception {
		CommandParser parser;
		CommandDefinitionList commandDefinitions;
		String[] stringArguments;
		CommandDefinitionBuilder commandDefinitionBuilder;
		ArgumentDefinitionBuilder argumentDefinitionBuilder;
		ArgumentDefinition argumentDefinition;
		CommandDefinition commandDefinition;
		Command commandBefore;
		Command commandAfter;
		Argument<String> argument;
		String expectedValue;

		argumentDefinitionBuilder = new ArgumentDefinitionBuilder();
		argumentDefinitionBuilder.setLongName("test-key");
		argumentDefinitionBuilder.setShortName("t");
		argumentDefinitionBuilder.setDescription("This is a test argument.");
		argumentDefinitionBuilder.setValueClass(String.class);
		argumentDefinitionBuilder.setValidatorClass(DefaultArgumentValidator.class);
		argumentDefinitionBuilder.setParserClass(StringArgumentParser.class);
		expectedValue = "test-default-value";
		argumentDefinitionBuilder.setDefaultValue(expectedValue);
		argumentDefinitionBuilder.setExamples(new String[] {"example"});
		argumentDefinitionBuilder.setObligatory(false);
		argumentDefinition = argumentDefinitionBuilder.create();

		commandDefinitionBuilder = new CommandDefinitionBuilder();
		commandDefinitionBuilder.setName("test-command");
		commandDefinitionBuilder.setDescription("This is a test command description.");
		commandDefinitionBuilder.setCommandToExecute(new MockExecutableCommand());
		commandDefinitionBuilder.setArgumentInjectionEnabled(false);
		commandDefinition = commandDefinitionBuilder.create();
		commandDefinition.addArgumentDefinition(argumentDefinition);
		commandDefinitions = new CommandDefinitionList();
		commandDefinitions.add(commandDefinition);
		commandDefinitions.add(new HelpCommandDefinition(commandDefinitions));
		commandBefore = new Command(commandDefinition);

		argument = new Argument<>(argumentDefinition, expectedValue);
		commandBefore.addArgument(argument);

		stringArguments = new String[] {"test-command"};
		parser = new CommandParser(new GnuCommandLineLanguage(), commandDefinitions);
		commandAfter = parser.parse(stringArguments);
		assertEquals(commandBefore, commandAfter);
	}

	@Test
	public void testParse_HelpCommand() throws Exception {
		CommandParser parser;
		CommandDefinitionList commandDefinitions;
		String[] cliArguments;
		Command commandBefore;
		Command commandAfter;
		HelpCommandDefinition helpCommandDefinition;
		String helpArgumentName;
		Argument<?> helpArgument;
		String helpArgumentValue;
		String cliCommand;
		ArgumentDefinition helpArgumentDefinition;
		GnuCommandLineLanguage language;

		commandDefinitions = new CommandDefinitionList();
		helpCommandDefinition = new HelpCommandDefinition(commandDefinitions);

		helpArgumentDefinition = helpCommandDefinition.getArgumentDefinition("c");
		helpArgument = new Argument<>(helpArgumentDefinition, "test-value");
		commandBefore = new Command(helpCommandDefinition);
		commandBefore.addArgument(helpArgument);

		helpArgumentName = "c";
		helpArgumentValue = "test-value";
		cliCommand = helpCommandDefinition.getName() + " -" + helpArgumentName + " " + helpArgumentValue;
		cliArguments = cliCommand.split(" ");

		language = new GnuCommandLineLanguage();
		parser = new CommandParser(language, commandDefinitions);
		commandAfter = parser.parse(cliArguments);
		assertEquals(commandBefore, commandAfter);
	}

	@Test(expected = CommandParseException.class)
	public void testParse_ArgumentIsNotDefined() throws Exception {
		CommandParser parser;
		CommandDefinitionList commandDefinitions;
		String[] stringArguments;
		CommandDefinitionBuilder commandDefinitionBuilder;
		CommandDefinition commandDefinition;
		Command commandBefore;
		Command commandAfter;

		commandDefinitionBuilder = new CommandDefinitionBuilder();
		commandDefinitionBuilder.setName("test-command");
		commandDefinitionBuilder.setDescription("This is a test command description.");
		commandDefinitionBuilder.setCommandToExecute(new MockExecutableCommand());
		commandDefinitionBuilder.setArgumentInjectionEnabled(false);
		commandDefinition = commandDefinitionBuilder.create();
		commandDefinitions = new CommandDefinitionList();
		commandDefinitions.add(commandDefinition);
		commandDefinitions.add(new HelpCommandDefinition(commandDefinitions));
		commandBefore = new Command(commandDefinition);

		stringArguments = "test-command --test-key test-value".split(" ");
		parser = new CommandParser(new GnuCommandLineLanguage(), commandDefinitions);
		commandAfter = parser.parse(stringArguments);
		assertEquals(commandBefore, commandAfter);
	}

	@Test(expected = CommandParseException.class)
	public void testParse_ObligatoryArgumentMissing() throws Exception {
		CommandParser parser;
		CommandDefinitionList commandDefinitions;
		String[] stringArguments;
		CommandDefinitionBuilder commandDefinitionBuilder;
		ArgumentDefinitionBuilder argumentDefinitionBuilder;
		ArgumentDefinition argumentDefinition;
		CommandDefinition commandDefinition;
		Command commandBefore;
		Command commandAfter;
		Argument<String> argument;

		argumentDefinitionBuilder = new ArgumentDefinitionBuilder();
		argumentDefinitionBuilder.setLongName("test-key");
		argumentDefinitionBuilder.setShortName("t");
		argumentDefinitionBuilder.setDescription("This is a test argument.");
		argumentDefinitionBuilder.setValueClass(String.class);
		argumentDefinitionBuilder.setValidatorClass(DefaultArgumentValidator.class);
		argumentDefinitionBuilder.setParserClass(StringArgumentParser.class);
		argumentDefinitionBuilder.setDefaultValue(null);
		argumentDefinitionBuilder.setExamples(new String[] {"example"});
		argumentDefinitionBuilder.setObligatory(true);
		argumentDefinition = argumentDefinitionBuilder.create();

		commandDefinitionBuilder = new CommandDefinitionBuilder();
		commandDefinitionBuilder.setName("test-command");
		commandDefinitionBuilder.setDescription("This is a test command description.");
		commandDefinitionBuilder.setCommandToExecute(new MockExecutableCommand());
		commandDefinitionBuilder.setArgumentInjectionEnabled(false);
		commandDefinition = commandDefinitionBuilder.create();
		commandDefinition.addArgumentDefinition(argumentDefinition);
		commandDefinitions = new CommandDefinitionList();
		commandDefinitions.add(commandDefinition);
		commandDefinitions.add(new HelpCommandDefinition(commandDefinitions));
		commandBefore = new Command(commandDefinition);

		argument = new Argument<>(argumentDefinition, "test-value");
		commandBefore.addArgument(argument);

		stringArguments = new String[] {"test-command"};
		parser = new CommandParser(new GnuCommandLineLanguage(), commandDefinitions);
		commandAfter = parser.parse(stringArguments);
		assertEquals(commandBefore, commandAfter);
	}

	@Test(expected = CommandParseException.class)
	public void testParse_LongAndShortArgumentNameUsedAtTheSameTime() throws Exception {
		CommandParser parser;
		CommandDefinitionList commandDefinitions;
		String[] stringArguments;
		CommandDefinitionBuilder commandDefinitionBuilder;
		ArgumentDefinitionBuilder argumentDefinitionBuilder;
		ArgumentDefinition argumentDefinition;
		CommandDefinition commandDefinition;
		Command commandBefore;
		Command commandAfter;
		Argument<String> argument;

		argumentDefinitionBuilder = new ArgumentDefinitionBuilder();
		argumentDefinitionBuilder.setLongName("test-key");
		argumentDefinitionBuilder.setShortName("t");
		argumentDefinitionBuilder.setDescription("This is a test argument.");
		argumentDefinitionBuilder.setValueClass(String.class);
		argumentDefinitionBuilder.setValidatorClass(DefaultArgumentValidator.class);
		argumentDefinitionBuilder.setParserClass(StringArgumentParser.class);
		argumentDefinitionBuilder.setDefaultValue(null);
		argumentDefinitionBuilder.setExamples(new String[] {"example"});
		argumentDefinitionBuilder.setObligatory(true);
		argumentDefinition = argumentDefinitionBuilder.create();

		commandDefinitionBuilder = new CommandDefinitionBuilder();
		commandDefinitionBuilder.setName("test-command");
		commandDefinitionBuilder.setDescription("This is a test command description.");
		commandDefinitionBuilder.setCommandToExecute(new MockExecutableCommand());
		commandDefinitionBuilder.setArgumentInjectionEnabled(false);
		commandDefinition = commandDefinitionBuilder.create();
		commandDefinition.addArgumentDefinition(argumentDefinition);
		commandDefinitions = new CommandDefinitionList();
		commandDefinitions.add(commandDefinition);
		commandDefinitions.add(new HelpCommandDefinition(commandDefinitions));
		commandBefore = new Command(commandDefinition);

		argument = new Argument<>(argumentDefinition, "test-value");
		commandBefore.addArgument(argument);

		stringArguments = "test-command --test-key test-value -t test-value".split(" ");
		parser = new CommandParser(new GnuCommandLineLanguage(), commandDefinitions);
		commandAfter = parser.parse(stringArguments);
		assertEquals(commandBefore, commandAfter);
	}

	@Test(expected = CommandParseException.class)
	public void testParse_NoCliArgument() throws Exception {
		CommandParser parser;

		parser = new CommandParser(new GnuCommandLineLanguage(), new CommandDefinitionList());
		parser.parse(new String[0]);
	}

	@Test(expected = CommandParseException.class)
	public void testParse_NullCliArgument() throws Exception {
		CommandParser parser;
		String[] arguments;

		parser = new CommandParser(new GnuCommandLineLanguage(), new CommandDefinitionList());
		arguments = new String[] {null};
		parser.parse(arguments);
	}

	@Test(expected = CommandParseException.class)
	public void testParse_EmptyCliArgument() throws Exception {
		CommandParser parser;
		String[] arguments;

		parser = new CommandParser(new GnuCommandLineLanguage(), new CommandDefinitionList());
		arguments = new String[] {" "};
		parser.parse(arguments);
	}

	@Test(expected = CommandParseException.class)
	public void testParse_CommandNotDefined() throws Exception {
		CommandParser parser;
		String[] arguments;

		parser = new CommandParser(new GnuCommandLineLanguage(), new CommandDefinitionList());
		arguments = "test-command --test-key test-value".split(" ");
		parser.parse(arguments);
	}
}