package commandline.command.help;

import commandline.argument.ArgumentDefinition;
import commandline.argument.ArgumentDefinitionBuilder;
import commandline.argument.validator.DefaultArgumentValidator;
import commandline.command.CommandDefinition;
import commandline.command.CommandDefinitionList;
import commandline.language.parser.specific.StringArgumentParser;
import org.jetbrains.annotations.NotNull;

/**
 * User: gno, Date: 27.01.2015 - 16:19
 */
public class HelpCommandDefinition extends CommandDefinition {
	public static final String COMMAND_NAME = "help";
	public static final String COMMAND_DESCRIPTION = "Shows the applications help.";
	public static final String ARGUMENT_HELP_SHORT_NAME = "c";
	public static final String ARGUMENT_HELP_LONG_NAME = "command";
	public static final String ARGUMENT_HELP_DESCRIPTION = "The command to show the help for.";
	public static final boolean ARGUMENT_HELP_OBLIGATORY = false;
	public static final String ARGUMENT_HELP_DEFAULT_VALUE = null;
	public static final Class<StringArgumentParser> ARGUMENT_PARSER_CLASS = StringArgumentParser.class;
	public static final Class<DefaultArgumentValidator> ARGUMENT_VALIDATOR_CLASS = DefaultArgumentValidator.class;
	public static final Class<String> ARGUMENT_VALUE_CLASS = String.class;
	public static final String ARGUMENT_HELP_EXAMPLE1 = "false";
	public static final String ARGUMENT_HELP_EXAMPLE2 = "true";
	public static final String[] ARGUMENT_HELP_EXAMPLES = new String[] {ARGUMENT_HELP_EXAMPLE1, ARGUMENT_HELP_EXAMPLE2};

	public HelpCommandDefinition(@NotNull CommandDefinitionList commands) {
		super(COMMAND_NAME, COMMAND_DESCRIPTION, new HelpExecutableCommand(commands), false);

		ArgumentDefinitionBuilder builder;
		ArgumentDefinition argumentDefinition;

		builder = new ArgumentDefinitionBuilder();
		builder.setLongName(ARGUMENT_HELP_LONG_NAME);
		builder.setShortName(ARGUMENT_HELP_SHORT_NAME);
		builder.setDescription(ARGUMENT_HELP_DESCRIPTION);
		builder.setObligatory(ARGUMENT_HELP_OBLIGATORY);
		builder.setDefaultValue(ARGUMENT_HELP_DEFAULT_VALUE);
		builder.setExamples(ARGUMENT_HELP_EXAMPLES);
		builder.setParserClass(ARGUMENT_PARSER_CLASS);
		builder.setValidatorClass(ARGUMENT_VALIDATOR_CLASS);
		builder.setValueClass(ARGUMENT_VALUE_CLASS);
		argumentDefinition = builder.create();
		addArgumentDefinition(argumentDefinition);
	}
}
