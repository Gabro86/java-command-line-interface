package commandline.language.parser;

import commandline.argument.Argument;
import commandline.argument.ArgumentDefinition;
import commandline.argument.ArgumentDefinitionBuilder;
import commandline.argument.validator.DefaultArgumentValidator;
import commandline.command.Command;
import commandline.command.CommandDefinition;
import commandline.command.CommandDefinitionBuilder;
import commandline.command.CommandDefinitionList;
import commandline.command.CommandDefinitionReader;
import commandline.command.CommandLineException;
import commandline.command.ExecutableCommand;
import commandline.command.MockExecutableCommand;
import commandline.command.help.HelpExecutableCommand;
import commandline.command.mock.ValidTestCommand;
import commandline.language.gnu.GnuCommandLineLanguage;
import commandline.language.parser.specific.BooleanArgumentParser;
import commandline.language.parser.specific.StringArgumentParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandParserTest {
	@Test
	public void testParse() throws Exception {
		CommandParser parser;
		CommandDefinitionList commandDefinitions;
		String[] cliCommandArguments;
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
		argumentDefinitionBuilder.setValidator(new DefaultArgumentValidator());
		argumentDefinitionBuilder.setParser(new StringArgumentParser());
		argumentDefinitionBuilder.setDefaultValue("test-default-value");
		argumentDefinitionBuilder.setExamples(new String[] {"example"});
		argumentDefinitionBuilder.setObligatory(false);
		argumentDefinition = argumentDefinitionBuilder.create();

		commandDefinitionBuilder = new CommandDefinitionBuilder();
		commandDefinitionBuilder.setName("test-command");
		commandDefinitionBuilder.setDescription("This is a test command description.");
		commandDefinitionBuilder.setCommandToExecute(new MockExecutableCommand());
		commandDefinition = commandDefinitionBuilder.create();
		commandDefinition.addArgumentDefinition(argumentDefinition);
		commandDefinitions = new CommandDefinitionList();
		commandDefinitions.add(commandDefinition);

		argument = new Argument<>(argumentDefinition, "test-value");
		commandBefore = new Command(commandDefinition);
		commandBefore.addArgument(argument);

		cliCommandArguments = "test-command --test-key test-value".split(" ");
		parser = new CommandParser(new GnuCommandLineLanguage(), commandDefinitions);
		commandAfter = parser.parse(cliCommandArguments);
		assertEquals(commandBefore, commandAfter);
	}

	@Test
	public void testParse_BooleanArgumentAsFlag() throws Exception {
		CommandParser parser;
		CommandDefinitionList commandDefinitions;
		String[] cliCommandArguments;
		CommandDefinitionBuilder commandDefinitionBuilder;
		ArgumentDefinitionBuilder argumentDefinitionBuilder;
		ArgumentDefinition argumentDefinition;
		CommandDefinition commandDefinition;
		Command commandBefore;
		Command commandAfter;
		Argument<Boolean> argument;

		argumentDefinitionBuilder = new ArgumentDefinitionBuilder();
		argumentDefinitionBuilder.setLongName("test-key");
		argumentDefinitionBuilder.setShortName("t");
		argumentDefinitionBuilder.setDescription("This is a test argument.");
		argumentDefinitionBuilder.setValueClass(Boolean.class);
		argumentDefinitionBuilder.setValidator(new DefaultArgumentValidator());
		argumentDefinitionBuilder.setParser(new BooleanArgumentParser());
		argumentDefinitionBuilder.setDefaultValue(null);
		argumentDefinitionBuilder.setExamples(new String[] {"example"});
		argumentDefinitionBuilder.setObligatory(true);
		argumentDefinition = argumentDefinitionBuilder.create();

		commandDefinitionBuilder = new CommandDefinitionBuilder();
		commandDefinitionBuilder.setName("test-command");
		commandDefinitionBuilder.setDescription("This is a test command description.");
		commandDefinitionBuilder.setCommandToExecute(new MockExecutableCommand());
		commandDefinition = commandDefinitionBuilder.create();
		commandDefinition.addArgumentDefinition(argumentDefinition);
		commandDefinitions = new CommandDefinitionList();
		commandDefinitions.add(commandDefinition);

		argument = new Argument<>(argumentDefinition, true);
		commandBefore = new Command(commandDefinition);
		commandBefore.addArgument(argument);

		cliCommandArguments = "test-command --test-key".split(" ");
		parser = new CommandParser(new GnuCommandLineLanguage(), commandDefinitions);
		commandAfter = parser.parse(cliCommandArguments);
		assertEquals(commandBefore, commandAfter);
	}

	@Test
	public void testParse_ArgumentNotPassedUseDefaultValue() throws Exception {
		CommandParser parser;
		CommandDefinitionList commandDefinitions;
		String[] cliCommandArguments;
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
		argumentDefinitionBuilder.setValidator(new DefaultArgumentValidator());
		argumentDefinitionBuilder.setParser(new StringArgumentParser());
		argumentDefinitionBuilder.setDefaultValue("test-default-value");
		argumentDefinitionBuilder.setExamples(new String[] {"example"});
		argumentDefinitionBuilder.setObligatory(false);
		argumentDefinition = argumentDefinitionBuilder.create();

		commandDefinitionBuilder = new CommandDefinitionBuilder();
		commandDefinitionBuilder.setName("test-command");
		commandDefinitionBuilder.setDescription("This is a test command description.");
		commandDefinitionBuilder.setCommandToExecute(new MockExecutableCommand());
		commandDefinition = commandDefinitionBuilder.create();
		commandDefinition.addArgumentDefinition(argumentDefinition);
		commandDefinitions = new CommandDefinitionList();
		commandDefinitions.add(commandDefinition);
		commandDefinitions.add(HelpExecutableCommand.readDefinitionFromAnnotations(commandDefinitions));

		argument = new Argument<>(argumentDefinition, "test-default-value");
		commandBefore = new Command(commandDefinition);
		commandBefore.addArgument(argument);

		cliCommandArguments = new String[] {"test-command"};
		parser = new CommandParser(new GnuCommandLineLanguage(), commandDefinitions);
		commandAfter = parser.parse(cliCommandArguments);
		assertEquals(commandBefore, commandAfter);
	}

	/*
	 * Makes sure that an empty string default value is not converted into a null default value.
	 */
	@Test
	public void testParse_ArgumentNotPassedUseDefaultEmptyStringValue() throws Exception {
		CommandParser parser;
		CommandDefinitionList commandDefinitions;
		String[] cliCommandArguments;
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
		argumentDefinitionBuilder.setValidator(new DefaultArgumentValidator());
		argumentDefinitionBuilder.setParser(new StringArgumentParser());
		argumentDefinitionBuilder.setDefaultValue("");
		argumentDefinitionBuilder.setExamples(new String[] {"example"});
		argumentDefinitionBuilder.setObligatory(false);
		argumentDefinition = argumentDefinitionBuilder.create();

		commandDefinitionBuilder = new CommandDefinitionBuilder();
		commandDefinitionBuilder.setName("test-command");
		commandDefinitionBuilder.setDescription("This is a test command description.");
		commandDefinitionBuilder.setCommandToExecute(new MockExecutableCommand());
		commandDefinition = commandDefinitionBuilder.create();
		commandDefinition.addArgumentDefinition(argumentDefinition);
		commandDefinitions = new CommandDefinitionList();
		commandDefinitions.add(commandDefinition);
		commandDefinitions.add(HelpExecutableCommand.readDefinitionFromAnnotations(commandDefinitions));

		argument = new Argument<>(argumentDefinition, "");
		commandBefore = new Command(commandDefinition);
		commandBefore.addArgument(argument);

		cliCommandArguments = new String[] {"test-command"};
		parser = new CommandParser(new GnuCommandLineLanguage(), commandDefinitions);
		commandAfter = parser.parse(cliCommandArguments);
		assertEquals(commandBefore, commandAfter);
	}

	@Test
	public void testParse_HelpCommand() throws Exception {
		CommandParser parser;
		CommandDefinitionList commandDefinitions;
		String[] cliCommandArguments;
		Command commandBefore;
		Command commandAfter;
		CommandDefinition helpCommandDefinition;
		Argument<?> commandToShowHelpForArgument;
		ArgumentDefinition commandToShowHelpForArgumentDefinition;
		GnuCommandLineLanguage language;
		ArgumentDefinitionBuilder argumentDefinitionBuilder;
		ArgumentDefinition helpArgumentDefinition;
		Argument<Boolean> helpArgument;

		argumentDefinitionBuilder = new ArgumentDefinitionBuilder();
		argumentDefinitionBuilder.setLongName(ExecutableCommand.ARGUMENT_HELP_LONG_NAME);
		argumentDefinitionBuilder.setShortName(ExecutableCommand.ARGUMENT_HELP_SHORT_NAME);
		argumentDefinitionBuilder.setObligatory(ExecutableCommand.ARGUMENT_HELP_OBLIGATORY);
		argumentDefinitionBuilder.setValueClass(ExecutableCommand.ARGUMENT_VALUE_CLASS);
		argumentDefinitionBuilder.setDefaultValue(ExecutableCommand.ARGUMENT_HELP_DEFAULT_VALUE);
		argumentDefinitionBuilder.setParser(ExecutableCommand.ARGUMENT_PARSER);
		argumentDefinitionBuilder.setValidator(ExecutableCommand.ARGUMENT_VALIDATOR);
		argumentDefinitionBuilder.setExamples(ExecutableCommand.ARGUMENT_HELP_EXAMPLES);
		argumentDefinitionBuilder.setDescription(ExecutableCommand.ARGUMENT_HELP_DESCRIPTION);
		helpArgumentDefinition = argumentDefinitionBuilder.create();
		helpArgument = new Argument<>(helpArgumentDefinition, false);

		//The command definition contains the argument "--help" by default.
		helpCommandDefinition = HelpExecutableCommand.readDefinitionFromAnnotations(new CommandDefinitionList());
		commandToShowHelpForArgumentDefinition = helpCommandDefinition.getArgumentDefinition("c");
		commandToShowHelpForArgument = new Argument<>(commandToShowHelpForArgumentDefinition, "test-value");

		commandBefore = new Command(helpCommandDefinition);
		commandBefore.addArgument(commandToShowHelpForArgument);
		commandBefore.addArgument(helpArgument);

		language = new GnuCommandLineLanguage();
		cliCommandArguments = "help -c test-value".split(" ");
		commandDefinitions = new CommandDefinitionList();
		commandDefinitions.add(helpCommandDefinition);
		parser = new CommandParser(language, commandDefinitions);
		commandAfter = parser.parse(cliCommandArguments);
		assertEquals(commandBefore, commandAfter);
	}

	@Test
	public void testParse_HelpArgument_LongName() throws Exception {
		CommandParser parser;
		CommandDefinitionList commandDefinitions;
		String[] cliCommandArguments;
		CommandDefinition commandDefinition;
		Command commandBefore;
		Command commandAfter;
		Argument<Boolean> helpArgument;
		String cliCommand;
		CommandDefinitionReader definitionReader;
		ArgumentDefinition helpArgumentDefinition;

		//Defines the command that is expected after the parsing
		definitionReader = new CommandDefinitionReader();
		//The command definition contains the argument "--help" by default.
		commandDefinition = definitionReader.readCommandDefinition(new ValidTestCommand());
		helpArgumentDefinition = commandDefinition.getArgumentDefinition(HelpExecutableCommand.ARGUMENT_HELP_LONG_NAME);
		helpArgument = new Argument<>(helpArgumentDefinition, true);
		commandBefore = new Command(commandDefinition);
		commandBefore.addArgument(helpArgument);

		//Creates the cli command that will be parsed
		cliCommand = ValidTestCommand.COMMAND_NAME + " --help true --" + ValidTestCommand.ARGUMENT_1_LONG_NAME + " test-value";
		cliCommandArguments = cliCommand.split(" ");

		commandDefinitions = new CommandDefinitionList();
		commandDefinitions.add(commandDefinition);
		parser = new CommandParser(new GnuCommandLineLanguage(), commandDefinitions);
		commandAfter = parser.parse(cliCommandArguments);
		assertEquals(commandBefore, commandAfter);
	}

	@Test
	public void testParse_HelpArgument_ShortName() throws Exception {
		CommandParser parser;
		CommandDefinitionList commandDefinitions;
		String[] cliCommandArguments;
		CommandDefinition commandDefinition;
		Command commandBefore;
		Command commandAfter;
		Argument<Boolean> helpArgument;
		String cliCommand;
		CommandDefinitionReader definitionReader;
		ArgumentDefinition helpArgumentDefinition;

		//Defines the command that is expected after the parsing
		definitionReader = new CommandDefinitionReader();
		//The command definition contains the argument "--help" by default.
		commandDefinition = definitionReader.readCommandDefinition(new ValidTestCommand());
		helpArgumentDefinition = commandDefinition.getArgumentDefinition(HelpExecutableCommand.ARGUMENT_HELP_LONG_NAME);
		helpArgument = new Argument<>(helpArgumentDefinition, true);
		commandBefore = new Command(commandDefinition);
		commandBefore.addArgument(helpArgument);

		//Creates the cli command that will be parsed
		cliCommand = ValidTestCommand.COMMAND_NAME + " -h true --" + ValidTestCommand.ARGUMENT_1_LONG_NAME + " test-value";
		cliCommandArguments = cliCommand.split(" ");

		commandDefinitions = new CommandDefinitionList();
		commandDefinitions.add(commandDefinition);
		parser = new CommandParser(new GnuCommandLineLanguage(), commandDefinitions);
		commandAfter = parser.parse(cliCommandArguments);
		assertEquals(commandBefore, commandAfter);
	}

	@Test
	public void testParse_HelpArgumentAsFlag() throws Exception {
		CommandParser parser;
		CommandDefinitionList commandDefinitions;
		String[] cliCommandArguments;
		CommandDefinition commandDefinition;
		Command commandBefore;
		Command commandAfter;
		Argument<Boolean> helpArgument;
		String cliCommand;
		CommandDefinitionReader definitionReader;
		ArgumentDefinition helpArgumentDefinition;

		//Defines the command that is expected after the parsing
		definitionReader = new CommandDefinitionReader();
		//The command definition contains the argument "--help" by default.
		commandDefinition = definitionReader.readCommandDefinition(new ValidTestCommand());
		helpArgumentDefinition = commandDefinition.getArgumentDefinition(HelpExecutableCommand.ARGUMENT_HELP_LONG_NAME);
		helpArgument = new Argument<>(helpArgumentDefinition, true);
		commandBefore = new Command(commandDefinition);
		commandBefore.addArgument(helpArgument);

		//Creates the cli command that will be parsed
		cliCommand = ValidTestCommand.COMMAND_NAME + " --help --" + ValidTestCommand.ARGUMENT_1_LONG_NAME + " test-value";
		cliCommandArguments = cliCommand.split(" ");

		commandDefinitions = new CommandDefinitionList();
		commandDefinitions.add(commandDefinition);
		parser = new CommandParser(new GnuCommandLineLanguage(), commandDefinitions);
		commandAfter = parser.parse(cliCommandArguments);
		assertEquals(commandBefore, commandAfter);
	}

	@Test(expected = CommandParseException.class)
	public void testParse_ArgumentIsNotDefined() throws Exception {
		CommandParser parser;
		String[] cliCommandArguments;

		cliCommandArguments = "test-command --test-key test-value".split(" ");
		parser = new CommandParser(new GnuCommandLineLanguage(), new CommandDefinitionList());
		parser.parse(cliCommandArguments);
	}

	@Test(expected = CommandParseException.class)
	public void testParse_ObligatoryArgumentMissing() throws Exception {
		CommandParser parser;
		CommandDefinitionList commandDefinitions;
		String[] cliCommandArguments;
		CommandDefinitionBuilder commandDefinitionBuilder;
		ArgumentDefinitionBuilder argumentDefinitionBuilder;
		ArgumentDefinition argumentDefinition;
		CommandDefinition commandDefinition;

		argumentDefinitionBuilder = new ArgumentDefinitionBuilder();
		argumentDefinitionBuilder.setLongName("test-key");
		argumentDefinitionBuilder.setShortName("t");
		argumentDefinitionBuilder.setDescription("This is a test argument.");
		argumentDefinitionBuilder.setValueClass(String.class);
		argumentDefinitionBuilder.setValidator(new DefaultArgumentValidator());
		argumentDefinitionBuilder.setParser(new StringArgumentParser());
		argumentDefinitionBuilder.setDefaultValue(null);
		argumentDefinitionBuilder.setExamples(new String[] {"example"});
		argumentDefinitionBuilder.setObligatory(true);
		argumentDefinition = argumentDefinitionBuilder.create();

		commandDefinitionBuilder = new CommandDefinitionBuilder();
		commandDefinitionBuilder.setName("test-command");
		commandDefinitionBuilder.setDescription("This is a test command description.");
		commandDefinitionBuilder.setCommandToExecute(new MockExecutableCommand());
		commandDefinition = commandDefinitionBuilder.create();
		commandDefinition.addArgumentDefinition(argumentDefinition);
		commandDefinitions = new CommandDefinitionList();
		commandDefinitions.add(commandDefinition);

		cliCommandArguments = new String[] {"test-command"};
		parser = new CommandParser(new GnuCommandLineLanguage(), commandDefinitions);
		parser.parse(cliCommandArguments);
	}

	@Test(expected = CommandLineException.class)
	public void testParse_ObligatoryArgumentValueIsNull() throws Exception {
		CommandParser parser;
		CommandDefinitionList commandDefinitions;
		String[] cliCommandArguments;
		CommandDefinitionBuilder commandDefinitionBuilder;
		ArgumentDefinitionBuilder argumentDefinitionBuilder;
		ArgumentDefinition argumentDefinition;
		CommandDefinition commandDefinition;

		argumentDefinitionBuilder = new ArgumentDefinitionBuilder();
		argumentDefinitionBuilder.setLongName("test-key");
		argumentDefinitionBuilder.setShortName("t");
		argumentDefinitionBuilder.setDescription("This is a test argument.");
		argumentDefinitionBuilder.setValueClass(String.class);
		argumentDefinitionBuilder.setValidator(new DefaultArgumentValidator());
		argumentDefinitionBuilder.setParser(new StringArgumentParser());
		argumentDefinitionBuilder.setDefaultValue(null);
		argumentDefinitionBuilder.setExamples(new String[] {"example"});
		argumentDefinitionBuilder.setObligatory(true);
		argumentDefinition = argumentDefinitionBuilder.create();

		commandDefinitionBuilder = new CommandDefinitionBuilder();
		commandDefinitionBuilder.setName("test-command");
		commandDefinitionBuilder.setDescription("This is a test command description.");
		commandDefinitionBuilder.setCommandToExecute(new MockExecutableCommand());
		commandDefinition = commandDefinitionBuilder.create();
		commandDefinition.addArgumentDefinition(argumentDefinition);
		commandDefinitions = new CommandDefinitionList();
		commandDefinitions.add(commandDefinition);

		cliCommandArguments = "test-command --test-key".split(" ");
		parser = new CommandParser(new GnuCommandLineLanguage(), commandDefinitions);
		parser.parse(cliCommandArguments);
	}

	@Test(expected = CommandParseException.class)
	public void testParse_LongAndShortArgumentNameUsedAtTheSameTime() throws Exception {
		CommandParser parser;
		CommandDefinitionList commandDefinitions;
		String[] cliCommandArguments;
		CommandDefinitionBuilder commandDefinitionBuilder;
		ArgumentDefinitionBuilder argumentDefinitionBuilder;
		ArgumentDefinition argumentDefinition;
		CommandDefinition commandDefinition;

		argumentDefinitionBuilder = new ArgumentDefinitionBuilder();
		argumentDefinitionBuilder.setLongName("test-key");
		argumentDefinitionBuilder.setShortName("t");
		argumentDefinitionBuilder.setDescription("This is a test argument.");
		argumentDefinitionBuilder.setValueClass(String.class);
		argumentDefinitionBuilder.setValidator(new DefaultArgumentValidator());
		argumentDefinitionBuilder.setParser(new StringArgumentParser());
		argumentDefinitionBuilder.setDefaultValue(null);
		argumentDefinitionBuilder.setExamples(new String[] {"example"});
		argumentDefinitionBuilder.setObligatory(true);
		argumentDefinition = argumentDefinitionBuilder.create();

		commandDefinitionBuilder = new CommandDefinitionBuilder();
		commandDefinitionBuilder.setName("test-command");
		commandDefinitionBuilder.setDescription("This is a test command description.");
		commandDefinitionBuilder.setCommandToExecute(new MockExecutableCommand());
		commandDefinition = commandDefinitionBuilder.create();
		commandDefinition.addArgumentDefinition(argumentDefinition);
		commandDefinitions = new CommandDefinitionList();
		commandDefinitions.add(commandDefinition);
		commandDefinitions.add(HelpExecutableCommand.readDefinitionFromAnnotations(commandDefinitions));

		cliCommandArguments = "test-command --test-key test-value -t test-value".split(" ");
		parser = new CommandParser(new GnuCommandLineLanguage(), commandDefinitions);
		parser.parse(cliCommandArguments);
	}

	@Test
	public void testParse_ArgumentDefaultValueIsNull() throws Exception {
		CommandParser parser;
		CommandDefinitionList commandDefinitions;
		String[] cliCommandArguments;
		CommandDefinitionBuilder commandDefinitionBuilder;
		ArgumentDefinitionBuilder argumentDefinitionBuilder;
		ArgumentDefinition argumentDefinition;
		CommandDefinition commandDefinition;

		argumentDefinitionBuilder = new ArgumentDefinitionBuilder();
		argumentDefinitionBuilder.setLongName("test-key");
		argumentDefinitionBuilder.setShortName("t");
		argumentDefinitionBuilder.setDescription("This is a test argument.");
		argumentDefinitionBuilder.setValueClass(String.class);
		argumentDefinitionBuilder.setValidator(new DefaultArgumentValidator());
		argumentDefinitionBuilder.setParser(new StringArgumentParser());
		argumentDefinitionBuilder.setDefaultValue(null);
		argumentDefinitionBuilder.setExamples(new String[] {"example"});
		argumentDefinitionBuilder.setObligatory(false);
		argumentDefinition = argumentDefinitionBuilder.create();

		commandDefinitionBuilder = new CommandDefinitionBuilder();
		commandDefinitionBuilder.setName("test-command");
		commandDefinitionBuilder.setDescription("This is a test command description.");
		commandDefinitionBuilder.setCommandToExecute(new MockExecutableCommand());
		commandDefinition = commandDefinitionBuilder.create();
		commandDefinition.addArgumentDefinition(argumentDefinition);
		commandDefinitions = new CommandDefinitionList();
		commandDefinitions.add(commandDefinition);
		commandDefinitions.add(HelpExecutableCommand.readDefinitionFromAnnotations(commandDefinitions));

		cliCommandArguments = new String[] {"test-command"};
		parser = new CommandParser(new GnuCommandLineLanguage(), commandDefinitions);
		parser.parse(cliCommandArguments);
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
		String[] cliCommandArguments;

		parser = new CommandParser(new GnuCommandLineLanguage(), new CommandDefinitionList());
		cliCommandArguments = new String[] {null};
		parser.parse(cliCommandArguments);
	}

	@Test(expected = CommandParseException.class)
	public void testParse_EmptyCliArgument() throws Exception {
		CommandParser parser;
		String[] cliCommandArguments;

		parser = new CommandParser(new GnuCommandLineLanguage(), new CommandDefinitionList());
		cliCommandArguments = new String[] {" "};
		parser.parse(cliCommandArguments);
	}

	@Test(expected = CommandParseException.class)
	public void testParse_CommandNotDefined() throws Exception {
		CommandParser parser;
		String[] cliCommandArguments;

		parser = new CommandParser(new GnuCommandLineLanguage(), new CommandDefinitionList());
		cliCommandArguments = "test-command --test-key test-value".split(" ");
		parser.parse(cliCommandArguments);
	}
}