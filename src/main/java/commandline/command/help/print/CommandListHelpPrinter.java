package commandline.command.help.print;

import commandline.command.CommandDefinition;
import commandline.command.CommandDefinitionList;
import commandline.exception.ArgumentNullException;
import org.jetbrains.annotations.NotNull;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.Table;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.TreeSet;

public class CommandListHelpPrinter extends HelpPrinter {
	@NotNull
	private final CommandDefinitionList commandDefinitions;

	public CommandListHelpPrinter(@NotNull CommandDefinitionList commandDefinitions, @NotNull OutputStream outputStream) {
		super(outputStream);
		if (commandDefinitions == null) {
			throw new ArgumentNullException();
		}
		this.commandDefinitions = commandDefinitions;
	}

	@NotNull
	public CommandDefinitionList getCommandDefinitions() {
		return this.commandDefinitions;
	}

	@NotNull
	private Table createArgumentsTable() {
		Table table;
		BorderStyle borderStyle;
		TreeSet<CommandDefinition> sortedDefinitions;

		//FIXME Für den unicode style muss unicode in der windows console freigeschaltet werden
		//        borderStyle = BorderStyle.UNICODE_BOX;
		borderStyle = createArgumentsBorderStyle();
		table = new Table(2, borderStyle);
		table.addCell("Command");
		table.addCell("Description");
		sortedDefinitions = new TreeSet<>();
		//Sorts the command list before printing ist.
		for (CommandDefinition definition : getCommandDefinitions().getMap().values()) {
			sortedDefinitions.add(definition);
		}
		for (CommandDefinition definition : sortedDefinitions) {
			table.addCell(definition.getName());
			table.addCell(definition.getDescription());
		}

		return table;
	}

	@NotNull
	private Table createHeaderTable() {
		Table table;
		BorderStyle borderStyle;

		//FIXME Für den unicode style muss unicode in der windows console freigeschaltet werden
		//        borderStyle = BorderStyle.UNICODE_BOX;
		borderStyle = createHeaderBorderStyle();
		table = new Table(2, borderStyle);
		table.addCell("Command List");

		return table;
	}

	@Override
	public void print() {
		Table headerTable;
		Table argumentsTable;
		PrintWriter writer;

		headerTable = createHeaderTable();
		argumentsTable = createArgumentsTable();
		writer = new PrintWriter(getOutputStream());
		writer.println(headerTable.render());
		writer.println(argumentsTable.render());
		writer.println("Execute \"help -c <command-name>\" to show more information of a specific command or use the " +
				"flag \"--help\" or \"-h\" when executing the command, e.g. \"add-browser -h\"");
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
