package commandline.command;

import commandline.argument.Argument;
import commandline.argument.ArgumentList;
import commandline.exception.ArgumentNullException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Iterator;

public class Command implements Comparable<Command>, Iterable<Argument<?>> {
	@NotNull
	private final CommandDefinition definition;
	@NotNull
	private final ArgumentList argumentList;

	public Command(@NotNull CommandDefinition definition) {
		if (definition == null) {
			throw new ArgumentNullException();
		}
		this.definition = definition;
		this.argumentList = new ArgumentList();
	}

	public Command(@NotNull String name, String description, @NotNull ExecutableCommand commandToExecute) {
		super();
		this.argumentList = new ArgumentList();
		this.definition = new CommandDefinition(name, description, commandToExecute);
	}

	@NotNull
	public CommandDefinition getDefinition() {
		return this.definition;
	}

	@NotNull
	public String getName() {
		return getDefinition().getName();
	}

	@NotNull
	public String getDescription() {
		return getDefinition().getDescription();
	}

	@NotNull
	public ExecutableCommand getCommandToExecute() {
		return getDefinition().getCommandToExecute();
	}

	public boolean isArgumentsInjectionEnabled() {
		return getDefinition().isArgumentsInjectionEnabled();
	}

	public void execute() {
		getCommandToExecute().baseExecute(this);
	}

	@NotNull
	public ArgumentList getArgumentList() {
		return this.argumentList;
	}

	public int getArgumentCount() {
		return getArgumentList().getSize();
	}

	public void addArgument(@NotNull Argument<?> argument) {
		getArgumentList().add(argument);
	}

	@Nullable
	public Argument<?> getArgument(@NotNull String longName) {
		return getArgumentList().get(longName);
	}

	public void removeAllArguments() {
		getArgumentList().clear();
	}

	public boolean isArgumentListEmpty() {
		return getArgumentList().isEmpty();
	}

	public boolean containsArgument(@NotNull String longName) {
		return getArgumentList().contains(longName);
	}

	public boolean containsArgument(@NotNull Argument<?> argument) {
		return getArgumentList().contains(argument);
	}

	public void addAllArguments(@NotNull Collection<? extends Argument<?>> arguments) {
		getArgumentList().addAll(arguments);
	}

	@NotNull
	public Collection<Argument<?>> getArguments() {
		return getArgumentList().getCollection();
	}

	@Nullable
	public Argument<?> removeArgument(@NotNull String longName) {
		return getArgumentList().remove(longName);
	}

	@Nullable
	public Argument<?> removeArgument(@NotNull Argument<?> argument) {
		return getArgumentList().remove(argument);
	}

	@NotNull
	@Override
	public Iterator<Argument<?>> iterator() {
		return getArgumentList().iterator();
	}

	@Override
	public int compareTo(@NotNull Command command) {
		return getName().compareTo(command.getName());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Command)) {
			return false;
		}

		Command command = (Command) o;

		if (!this.argumentList.equals(command.argumentList)) {
			return false;
		}
		if (!this.definition.equals(command.definition)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = this.definition.hashCode();
		result = 31 * result + this.argumentList.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "Command{" +
				"commandDefinition=" + this.definition +
				", argumentList=" + this.argumentList +
				'}';
	}

	@NotNull
	public static Command createMock() {
		return new Command("mock-command", "This is a mock command.", new MockExecutableCommand());
	}
}
