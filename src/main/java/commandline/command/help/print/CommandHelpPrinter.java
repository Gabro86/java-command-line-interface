package commandline.command.help.print;

import commandline.argument.ArgumentDefinition;
import commandline.command.CommandDefinition;
import commandline.exception.ArgumentNullException;
import org.jetbrains.annotations.NotNull;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.Table;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class CommandHelpPrinter extends HelpPrinter {
	protected static final String DESCRIPTION_COLUMN = "Description";
	protected static final String LONG_NAME_COLUMN = "Long Name";
	protected static final String SHORT_NAME_COLUMN = "Short Name";
	protected static final String SHORT_COMMAND_PREFIX = "-";
	private static final String EXAMPLES_COLUMN = "Examples";
	private static final String DEFAULT_VALUE_COLUMN = "Default Value";
	private static final String OBLIGATORY_COLUMN = "Obligatory";
	private static final String TYPE_COLUMN = "Type";
	@NotNull
	private final CommandDefinition commandDefinition;

	@SuppressWarnings("SameParameterValue")
	public CommandHelpPrinter(@NotNull CommandDefinition commandDefinition, @NotNull OutputStream outputStream) {
		super(outputStream);
		if (commandDefinition == null) {
			throw new ArgumentNullException();
		}
		this.commandDefinition = commandDefinition;
	}

	@NotNull
	public CommandDefinition getCommandDefinition() {
		return this.commandDefinition;
	}

	@NotNull
	private Table createArgumentsTablePart1() {
		Table table;
		BorderStyle borderStyle;
		LinkedList<ArgumentDefinition> arguments;

		arguments = new LinkedList<>(getCommandDefinition().getArgumentDefinitionCollection());
		//FIXME Für den unicode style muss unicode in der windows console freigeschaltet werden
		//        borderStyle = BorderStyle.UNICODE_BOX;
		borderStyle = createArgumentsBorderStyle();
		table = new Table(5, borderStyle);
		table.addCell(LONG_NAME_COLUMN);
		table.addCell(SHORT_NAME_COLUMN);
		table.addCell(TYPE_COLUMN);
		table.addCell(OBLIGATORY_COLUMN);
		table.addCell(DEFAULT_VALUE_COLUMN);
		Collections.sort(arguments);
		for (ArgumentDefinition argument : arguments) {
			table.addCell(createLongNameString(argument));
			table.addCell(SHORT_COMMAND_PREFIX + argument.getShortName());
			table.addCell(argument.getValueClass().getSimpleName());
			table.addCell(String.valueOf(argument.isObligatory()));
			if (argument.getDefaultValue() == null) {
				table.addCell("<none>");
			} else {
				table.addCell(argument.getDefaultValue());
			}
		}

		return table;
	}

	@NotNull
	private Table createArgumentsTablePart2() {
		Table table;
		BorderStyle borderStyle;
		List<ArgumentDefinition> arguments;

		arguments = new LinkedList<>(getCommandDefinition().getArgumentDefinitionCollection());
		//FIXME Für den unicode style muss unicode in der windows console freigeschaltet werden
		//        borderStyle = BorderStyle.UNICODE_BOX;
		borderStyle = createArgumentsBorderStyle();
		table = new Table(3, borderStyle);
		table.addCell(LONG_NAME_COLUMN);
		table.addCell(EXAMPLES_COLUMN);
		table.addCell(DESCRIPTION_COLUMN);
		Collections.sort(arguments);
		for (ArgumentDefinition argument : arguments) {
			table.addCell(createLongNameString(argument));
			table.addCell(createStringFromArray(argument.getExamples()));
			table.addCell(argument.getDescription());
		}

		return table;
	}

	@NotNull
	private String createStringFromArray(@NotNull String[] values) {
		StringBuilder builder;

		if (values == null) {
			throw new ArgumentNullException();
		}

		builder = new StringBuilder(0);
		for (String value : values) {
			builder.append(value);
			builder.append(", ");
		}
		if (values.length > 0) {
			builder.setLength(builder.length() - 2);
		}

		return builder.toString();
	}

	@NotNull
	private Table createHeaderTable() {
		Table table;
		BorderStyle borderStyle;

		//FIXME Für den unicode style muss unicode in der windows console freigeschaltet werden
		//        borderStyle = BorderStyle.UNICODE_BOX;
		borderStyle = createHeaderBorderStyle();
		table = new Table(1, borderStyle);
		table.addCell(getCommandDefinition().getName() + ": " + getCommandDefinition().getDescription());

		return table;
	}

	@NotNull
	private String createLongNameString(@NotNull ArgumentDefinition argument) {
		StringBuilder builder;

		if (argument == null) {
			throw new ArgumentNullException();
		}

		builder = new StringBuilder(20);
		builder.append("--");
		builder.append(argument.getLongName());
		builder.append(", ");
		if (!argument.getLongName().isEmpty()) {
			builder.setLength(builder.length() - 2);
		}

		return builder.toString();
	}

	@Override
	public void print() {
		Table headerTable;
		Table argumentsTable1;
		Table argumentsTable2;
		PrintWriter writer;

		headerTable = createHeaderTable();
		argumentsTable1 = createArgumentsTablePart1();
		argumentsTable2 = createArgumentsTablePart2();
		writer = new PrintWriter(getOutputStream());
		writer.println(headerTable.render());
		writer.println(argumentsTable1.render());
		writer.println(argumentsTable2.render());
		writer.flush();
		writer.close();
	}

	@NotNull
	private BorderStyle createArgumentsBorderStyle() {
		return new BorderStyle("", "", "", "", "", "", "", "", "", "", "", "", "", "   ", "", "", "");
	}

	@NotNull
	private BorderStyle createHeaderBorderStyle() {
		return new BorderStyle("", "-", "-", "", "", "-", "-", "", "", "-", "-", "", "", "", "", "", "");
	}
}