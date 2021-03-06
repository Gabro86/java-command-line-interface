package commandline.command.help.print;

import commandline.argument.ArgumentDefinition;
import commandline.command.CommandDefinition;
import commandline.exception.ArgumentNullException;
import org.jetbrains.annotations.NotNull;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.Table;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
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

		//FIXME Für den unicode style muss unicode in der windows console freigeschaltet werden
		//        borderStyle = BorderStyle.UNICODE_BOX;
		borderStyle = createArgumentsBorderStyle();
		table = new Table(5, borderStyle);
		table.addCell(LONG_NAME_COLUMN);
		table.addCell(SHORT_NAME_COLUMN);
		table.addCell(TYPE_COLUMN);
		table.addCell(OBLIGATORY_COLUMN);
		table.addCell(DEFAULT_VALUE_COLUMN);

		arguments = new LinkedList<>(getCommandDefinition().getArgumentDefinitionCollection());
		Collections.sort(arguments);
		for (ArgumentDefinition argument : arguments) {
			table.addCell(createLongNameString(argument));
			table.addCell(createShortNameString(argument));
			table.addCell(getArgumentValueTypeName(argument.getValueClass()));
			table.addCell(String.valueOf(argument.isObligatory()));
			if (argument.getDefaultValue() == null) {
				table.addCell("<none>");
			} else if (argument.getDefaultValue().isEmpty()) {
				table.addCell("<empty-string>");
			} else {
				table.addCell(argument.getDefaultValue());
			}
		}

		return table;
	}

	private String getArgumentValueTypeName(Class<?> clazz) {
		String className;

		if (clazz == null) {
			throw new ArgumentNullException();
		}
		if (clazz.equals(String.class) || clazz.equals(Short.class) || clazz.equals(short.class) || clazz.equals(Integer.class) ||
				clazz.equals(int.class) || clazz.equals(Long.class) || clazz.equals(long.class) || clazz.equals(Character.class) ||
				clazz.equals(char.class) || clazz.equals(Boolean.class) || clazz.equals(boolean.class) || clazz.equals(Float.class) ||
				clazz.equals(float.class) || clazz.equals(Double.class) || clazz.equals(double.class) ||
				clazz.equals(File.class) || (clazz.equals(URL.class))) {
			className = clazz.getSimpleName();
		} else {
			/*
			 * The class names of custom classes are hidden from the user, because it's of no use for the user to see the class name
			 * custom classes. Instead of the class name "String" is printed to the terminal.
			 */
			className = "String";
		}

		return className;
	}

	@NotNull
	private Table createArgumentsTablePart2() {
		Table table;
		BorderStyle borderStyle;
		List<ArgumentDefinition> arguments;

		//FIXME Für den unicode style muss unicode in der windows console freigeschaltet werden
		//        borderStyle = BorderStyle.UNICODE_BOX;
		borderStyle = createArgumentsBorderStyle();
		table = new Table(3, borderStyle);
		table.addCell(LONG_NAME_COLUMN);
		table.addCell(SHORT_NAME_COLUMN);
		table.addCell(EXAMPLES_COLUMN);

		arguments = new LinkedList<>(getCommandDefinition().getArgumentDefinitionCollection());
		Collections.sort(arguments);
		for (ArgumentDefinition argument : arguments) {
			table.addCell(createLongNameString(argument));
			table.addCell(createShortNameString(argument));
			table.addCell(createStringFromArray(argument.getExamples()));
		}

		return table;
	}

	@NotNull
	private Table createArgumentsTablePart3() {
		Table table;
		BorderStyle borderStyle;
		List<ArgumentDefinition> arguments;

		//FIXME Für den unicode style muss unicode in der windows console freigeschaltet werden
		//        borderStyle = BorderStyle.UNICODE_BOX;
		borderStyle = createArgumentsBorderStyle();
		table = new Table(3, borderStyle);
		table.addCell(LONG_NAME_COLUMN);
		table.addCell(SHORT_NAME_COLUMN);
		table.addCell(DESCRIPTION_COLUMN);

		arguments = new LinkedList<>(getCommandDefinition().getArgumentDefinitionCollection());
		Collections.sort(arguments);
		for (ArgumentDefinition argument : arguments) {
			table.addCell(createLongNameString(argument));
			table.addCell(createShortNameString(argument));
			table.addCell(argument.getDescription());
		}

		return table;
	}

	private String createShortNameString(ArgumentDefinition argument) {
		return SHORT_COMMAND_PREFIX + argument.getShortName();
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

		return builder.toString();
	}

	@Override
	public void print() {
		Table headerTable;
		Table argumentsTable1;
		Table argumentsTable2;
		Table argumentsTable3;
		PrintWriter writer;

		headerTable = createHeaderTable();
		argumentsTable1 = createArgumentsTablePart1();
		argumentsTable2 = createArgumentsTablePart2();
		argumentsTable3 = createArgumentsTablePart3();
		writer = new PrintWriter(getOutputStream());
		writer.println(headerTable.render());
		writer.println(argumentsTable1.render());
		writer.println(argumentsTable2.render());
		writer.println(argumentsTable3.render());
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
