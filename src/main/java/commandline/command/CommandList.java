package commandline.command;

import commandline.exception.ArgumentNullException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class CommandList implements Iterable<Command> {
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
	public Collection<Command> getCollection() {
		return Collections.unmodifiableCollection(getCommands().values());
	}

	@Nullable
	public Command get(@NotNull String commandName) {
		if (commandName == null) {
			throw new ArgumentNullException();
		}
		if (commandName.trim().isEmpty()) {
			throw new IllegalArgumentException(
					"The command could not been retrieved, because the passed command name doesn't contain any character.");
		}
		return getCommands().get(commandName.trim());
	}

	public void add(@NotNull Command command) {
		if (command == null) {
			throw new ArgumentNullException();
		}
		getCommands().put(command.getName(), command);
	}

	public void addAll(@NotNull Collection<Command> commands) {
		if (commands == null) {
			throw new ArgumentNullException();
		}
		for (Command command : commands) {
			add(command);
		}
	}

	@Nullable
	public Command remove(@NotNull String commandName) {
		if (commandName == null) {
			throw new ArgumentNullException();
		}
		if (commandName.trim().isEmpty()) {
			throw new IllegalArgumentException(
					"The command could not been removed, because the passed command name doesn't contain any character.");
		}
		return getCommands().remove(commandName.trim());
	}

	@Nullable
	public Command remove(@NotNull Command command) {
		if (command == null) {
			throw new ArgumentNullException();
		}
		return getCommands().remove(command.getName());
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
			throw new IllegalArgumentException(
					"The command existence could not been tested, because the passed command name doesn't contain any character.");
		}
		return getCommands().containsKey(commandName.trim());
	}

	public boolean contains(@NotNull Command command) {
		if (command == null) {
			throw new ArgumentNullException();
		}
		return getCommands().containsKey(command.getName());
	}

	public void clear() {
		getCommands().clear();
	}

	@NotNull
	@Override
	public String toString() {
		return "CommandList{" +
				"commands=" + this.commands +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof CommandList)) {
			return false;
		}

		CommandList that = (CommandList) o;

		if (!this.commands.equals(that.commands)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return this.commands.hashCode();
	}

	@NotNull
	@Override
	public Iterator<Command> iterator() {
		return getCommands().values().iterator();
	}
}
