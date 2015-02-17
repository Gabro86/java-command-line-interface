package commandline.command;

import commandline.argument.ArgumentDefinition;
import commandline.argument.ArgumentDefinitionList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Iterator;

/**
 * User: gno, Date: 26.01.2015 - 16:33
 */
public class CommandDefinitionBuilder implements Iterable<ArgumentDefinition> {
	@NotNull
	private String name;
	@NotNull
	private String description;
	@NotNull
	private ExecutableCommand commandToExecute;
	private boolean argumentInjectionEnabled;
	@NotNull
	private ArgumentDefinitionList arguments;

	public CommandDefinitionBuilder() {
		super();
		setName("mock-command");
		setDescription("mock-description");
		setCommandToExecute(new MockExecutableCommand());
		setArguments(new ArgumentDefinitionList());
		setArgumentInjectionEnabled(false);
	}

	@NotNull
	public String getName() {
		return this.name;
	}

	public void setName(@NotNull String name) {
		this.name = name;
	}

	@NotNull
	public String getDescription() {
		return this.description;
	}

	public void setDescription(@NotNull String description) {
		this.description = description;
	}

	@NotNull
	public ExecutableCommand getCommandToExecute() {
		return this.commandToExecute;
	}

	public void setCommandToExecute(@NotNull ExecutableCommand commandToExecute) {
		this.commandToExecute = commandToExecute;
	}

	public boolean isArgumentInjectionEnabled() {
		return this.argumentInjectionEnabled;
	}

	public void setArgumentInjectionEnabled(boolean argumentInjectionEnabled) {
		this.argumentInjectionEnabled = argumentInjectionEnabled;
	}

	@NotNull
	public ArgumentDefinitionList getArguments() {
		return this.arguments;
	}

	public void setArguments(@NotNull ArgumentDefinitionList arguments) {
		this.arguments = arguments;
	}

	public int getSize() {
		return getArguments().getSize();
	}

	@NotNull
	public ArgumentDefinition getArgument(String argumentLongName) {
		return getArguments().get(argumentLongName);
	}

	public boolean isEmpty() {
		return getArguments().isEmpty();
	}

	public boolean containsArgument(@NotNull String argumentLongName) {
		return getArguments().contains(argumentLongName);
	}

	public void addArgument(@NotNull ArgumentDefinition argument) {
		getArguments().add(argument);
	}

	public void clearArguments() {
		getArguments().clear();
	}

	public boolean containsArgument(@NotNull ArgumentDefinition argument) {
		return getArguments().contains(argument);
	}

	@Nullable
	public ArgumentDefinition removeArgument(@NotNull String argumentLongName) {
		return getArguments().remove(argumentLongName);
	}

	public void addAllArguments(@NotNull Collection<ArgumentDefinition> arguments) {
		getArguments().addAll(arguments);
	}

	@NotNull
	public Collection<ArgumentDefinition> getArgumentsList() {
		return getArguments().getCollection();
	}

	@Override
	public Iterator<ArgumentDefinition> iterator() {
		return getArguments().iterator();
	}

	@NotNull
	public CommandDefinition create() {
		CommandDefinition definition;

		definition = new CommandDefinition(getName(), getDescription(), getCommandToExecute());
		definition.addAllArgumentDefinitions(getArgumentsList());

		return definition;
	}
}
