package commandline.command;

import commandline.exception.ArgumentNullException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CommandDefinitionList {
	@NotNull
	private final HashMap<String, CommandDefinition> commands;

	public CommandDefinitionList() {
		super();
		this.commands = new HashMap<>();
	}

	@NotNull
	private HashMap<String, CommandDefinition> getCommands() {
		return this.commands;
	}

	@NotNull
	public Map<String, CommandDefinition> getMap() {
		return Collections.unmodifiableMap(this.commands);
	}

	@NotNull
	public Collection<CommandDefinition> getCollection() {
		return Collections.unmodifiableCollection(getCommands().values());
	}

	@Nullable
	public CommandDefinition get(@NotNull String commandName) {
		if (commandName == null) {
			throw new ArgumentNullException();
		}
		if (commandName.trim().isEmpty()) {
			throw new IllegalArgumentException("The command definition could not been retrieved, " +
					"because the passed command name doesn't contain any character.");
		}
		return getCommands().get(commandName);
	}

	public void add(@NotNull CommandDefinition command) {
		if (command == null) {
			throw new ArgumentNullException();
		}
		getCommands().put(command.getName(), command);
	}

	public void addAll(@NotNull Collection<CommandDefinition> definitions) {
		if (definitions == null) {
			throw new ArgumentNullException();
		}
		for (CommandDefinition definition : definitions) {
			add(definition);
		}
	}

	@Nullable
	public CommandDefinition remove(@NotNull String commandName) {
		if (commandName == null) {
			throw new ArgumentNullException();
		}
		if (commandName.trim().isEmpty()) {
			throw new IllegalArgumentException("The command definition could not been removed, " +
					"because the passed command name doesn't contain any character.");
		}
		return getCommands().remove(commandName);
	}

	@Nullable
	public CommandDefinition remove(@NotNull CommandDefinition definition) {
		if (definition == null) {
			throw new ArgumentNullException();
		}
		return getCommands().remove(definition.getName());
	}

	public int getSize() {
		return getCommands().size();
	}

	public boolean isEmpty() {
		return getCommands().isEmpty();
	}

	public boolean contains(@NotNull String commandName) {
		if (commandName == null) {
			throw new ArgumentNullException();
		}
		if (commandName.trim().isEmpty()) {
			throw new IllegalArgumentException("The existence of the command definition could not been tested, " +
					"because the passed command name doesn't contain any character.");
		}
		return getCommands().containsKey(commandName);
	}

	public boolean contains(@NotNull CommandDefinition definition) {
		return getCommands().containsKey(definition.getName());
	}

	public void clear() {
		getCommands().clear();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof CommandDefinitionList)) {
			return false;
		}

		CommandDefinitionList that = (CommandDefinitionList) o;

		if (!this.commands.equals(that.commands)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return this.commands.hashCode();
	}
}
