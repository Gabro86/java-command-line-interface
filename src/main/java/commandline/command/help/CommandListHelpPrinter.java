package commandline.command.help;

import commandline.command.Command;
import commandline.command.CommandList;
import commandline.exception.ArgumentNullException;
import org.jetbrains.annotations.NotNull;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.Table;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.TreeSet;

public class CommandListHelpPrinter extends HelpPrinter {
	@NotNull
	private final CommandList commands;

	public CommandListHelpPrinter(@NotNull CommandList commands, @NotNull OutputStream outputStream, @NotNull String applicationName) {
		super(outputStream, applicationName);
		if (commands == null) {
			throw new ArgumentNullException();
		}
		this.commands = commands;
	}

	@NotNull
	public CommandList getCommands() {
		return this.commands;
	}

	@NotNull
	private Table createArgumentsTable() {
		Table table;
		BorderStyle borderStyle;
		TreeSet<Command> sortedCommands;

		//TODO Für den unicode style muss unicode in der windows console freigeschaltet werden
		//        borderStyle = BorderStyle.UNICODE_BOX;
		borderStyle = createArgumentsBorderStyle();
		table = new Table(2, borderStyle);
		table.addCell("Command");
		table.addCell("Description");
		sortedCommands = new TreeSet<>();
		//Sorts the command list before printing ist.
		for (Command command : getCommands().getCommandMap().values()) {
			sortedCommands.add(command);
		}
		for (Command c : sortedCommands) {
			table.addCell(c.getName());
			table.addCell(c.getDescription());
		}

		return table;
	}

	@NotNull
	private Table createHeaderTable() {
		Table table;
		BorderStyle borderStyle;

		//TODO Für den unicode style muss unicode in der windows console freigeschaltet werden
		//        borderStyle = BorderStyle.UNICODE_BOX;
		borderStyle = createHeaderBorderStyle();
		table = new Table(2, borderStyle);
		table.addCell(getApplicationName() + ": Command List");

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
		writer.println("Use help -c <command-name> to show more information of a specific command.");
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
