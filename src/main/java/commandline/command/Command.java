package commandline.command;

import commandline.exception.ArgumentNullException;

public abstract class Command implements Comparable<Command> {
	private final String name;
	private final String description;

	public Command(String name, String description) {
		super();
		if (name == null) {
			throw new ArgumentNullException();
		}
		if (description == null) {
			throw new ArgumentNullException();
		}
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	@Override
	public int compareTo(Command command) {
		return getName().compareTo(command.getName());
	}

	public abstract void execute();
}
