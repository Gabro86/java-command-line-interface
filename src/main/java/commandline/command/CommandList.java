package commandline.command;

import commandline.exception.ArgumentNullException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CommandList {
	@NotNull
	private final HashMap<String, Command> commands;

	public CommandList() {
		super();
		this.commands = new HashMap<>(50);
	}

	@NotNull
	private HashMap<String, Command> getCommands() {
		return this.commands;
	}

	@NotNull
	public Map<String, Command> getCommandMap() {
		return Collections.unmodifiableMap(this.commands);
	}

	@Nullable
	public Command get(@NotNull String commandName) {
		if (commandName == null) {
			throw new ArgumentNullException();
		}
		return getCommands().get(commandName);
	}

	public void add(@NotNull Command command) {
		if (command == null) {
			throw new ArgumentNullException();
		}
		getCommands().put(command.getName(), command);
	}

	@SuppressWarnings("SameParameterValue")
	public void remove(@NotNull String commandName) {
		if (commandName == null) {
			throw new ArgumentNullException();
		}
		getCommands().remove(commandName);
	}

	public int size() {
		return getCommands().size();
	}

	public boolean isEmpty() {
		return getCommands().isEmpty();
	}

	@SuppressWarnings("SameParameterValue")
	public boolean contains(@NotNull String commandName) {
		return getCommands().containsKey(commandName);
	}

	public void clear() {
		getCommands().clear();
	}
}
