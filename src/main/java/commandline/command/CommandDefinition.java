package commandline.command;

import commandline.annotation.CliCommand;
import commandline.argument.ArgumentDefinition;
import commandline.argument.ArgumentDefinitionList;
import commandline.exception.ArgumentNullException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Iterator;

public class CommandDefinition implements Comparable<CommandDefinition>, Iterable<ArgumentDefinition> {
	@NotNull
	private final String name;
	@NotNull
	private final String description;
	@NotNull
	private final ExecutableCommand commandToExecute;
	private final boolean argumentInjectionEnabled;
	@NotNull
	private final ArgumentDefinitionList arguments;

	public CommandDefinition(@NotNull CliCommand commandAnnotation, @NotNull ExecutableCommand commandToExecute) {
		this(commandAnnotation.name(), commandAnnotation.description(), commandToExecute, true);
	}

	public CommandDefinition(@NotNull String name, @NotNull String description, @NotNull ExecutableCommand commandToExecute,
			boolean argumentInjectionEnabled) {
		super();

		String editName;

		//commandToExecute can be null
		if (name == null) {
			throw new ArgumentNullException();
		}
		editName = name.trim();
		if (editName.isEmpty()) {
			throw new CommandLineException(
					"The command definition could not been created, because the passed name doesn't contain any character.");
		}
		if (description == null) {
			throw new ArgumentNullException();
		}
		if (commandToExecute == null) {
			throw new ArgumentNullException();
		}
		this.name = editName;
		this.description = description;
		this.arguments = new ArgumentDefinitionList();
		this.commandToExecute = commandToExecute;
		this.argumentInjectionEnabled = argumentInjectionEnabled;
	}

	@NotNull
	public String getName() {
		return this.name;
	}

	@NotNull
	public String getDescription() {
		return this.description;
	}

	@NotNull
	public ExecutableCommand getCommandToExecute() {
		return this.commandToExecute;
	}

	public boolean isArgumentInjectionEnabled() {
		return this.argumentInjectionEnabled;
	}

	@NotNull
	private ArgumentDefinitionList getArguments() {
		return this.arguments;
	}

	public int getArgumentSize() {
		return getArguments().getSize();
	}

	public void addArgumentDefinition(@NotNull ArgumentDefinition argument) {
		getArguments().add(argument);
	}

	public void clearArgumentDefinitions() {
		getArguments().clear();
	}

	public boolean containsArgumentDefinition(@NotNull ArgumentDefinition argument) {
		return getArguments().contains(argument);
	}

	public boolean containsArgumentDefinition(@NotNull String argumentLongName) {
		return getArguments().contains(argumentLongName);
	}

	@Nullable
	public ArgumentDefinition getArgumentDefinition(@NotNull String argumentLongName) {
		return getArguments().get(argumentLongName);
	}

	@Nullable
	public ArgumentDefinition removeArgumentDefinition(@NotNull String argumentLongName) {
		return getArguments().remove(argumentLongName);
	}

	@Nullable
	public ArgumentDefinition removeArgumentDefinition(@NotNull ArgumentDefinition definition) {
		return getArguments().remove(definition);
	}

	public boolean isEmpty() {
		return getArguments().isEmpty();
	}

	@NotNull
	public Collection<ArgumentDefinition> getArgumentDefinitionCollection() {
		return getArguments().getCollection();
	}

	public void addAllArgumentDefinitions(@NotNull Collection<ArgumentDefinition> arguments) {
		getArguments().addAll(arguments);
	}

	@NotNull
	@Override
	public Iterator<ArgumentDefinition> iterator() {
		return getArguments().iterator();
	}

	@Override
	public int compareTo(@NotNull CommandDefinition command) {
		return getName().compareTo(command.getName());
	}

	@Override
	public String toString() {
		return "CommandDefinition{" +
				"name='" + this.name + '\'' +
				", description='" + this.description + '\'' +
				", commandToExecute=" + this.commandToExecute +
				", argumentInjectionEnabled=" + this.argumentInjectionEnabled +
				", arguments=" + this.arguments +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof CommandDefinition)) {
			return false;
		}

		CommandDefinition that = (CommandDefinition) o;

		if (this.argumentInjectionEnabled != that.argumentInjectionEnabled) {
			return false;
		}
		if (!this.arguments.equals(that.arguments)) {
			return false;
		}
		if (!this.commandToExecute.equals(that.commandToExecute)) {
			return false;
		}
		if (!this.description.equals(that.description)) {
			return false;
		}
		if (!this.name.equals(that.name)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = this.name.hashCode();
		result = 31 * result + this.description.hashCode();
		result = 31 * result + this.commandToExecute.hashCode();
		result = 31 * result + (this.argumentInjectionEnabled ? 1 : 0);
		result = 31 * result + this.arguments.hashCode();
		return result;
	}

	@NotNull
	public static CommandDefinition createMock() {
		return new CommandDefinition("test-command", "This is a description.", new MockExecutableCommand(), false);
	}
}
