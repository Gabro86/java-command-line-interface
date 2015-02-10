package commandline.command;

import commandline.argument.GenericArgument;
import commandline.argument.GenericArgumentList;
import commandline.exception.ArgumentNullException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Iterator;

/**
 * User: gno, Date: 06.01.2015 - 15:27
 */
public class GenericCommand implements Iterable<GenericArgument> {
	@NotNull
	private final String name;
	@NotNull
	private final GenericArgumentList argumentList;

	public GenericCommand(@NotNull String name) {
		this(name, new GenericArgumentList());
	}

	public GenericCommand(@NotNull String name, @NotNull GenericArgumentList argumentList) {
		super();
		if (name == null) {
			throw new ArgumentNullException();
		}
		if (name.trim().isEmpty()) {
			throw new IllegalArgumentException(
					"The generic command could not been created, because the passed name doesn't contain any character.");
		}
		if (argumentList == null) {
			throw new ArgumentNullException();
		}
		this.name = name;
		this.argumentList = argumentList;
	}

	@NotNull
	public String getName() {
		return this.name;
	}

	@NotNull
	private GenericArgumentList getArgumentList() {
		return this.argumentList;
	}

	public int getArgumentCount() {
		return getArgumentList().getSize();
	}

	public boolean containsArgument(@NotNull GenericArgument argument) {
		return getArgumentList().contains(argument);
	}

	@NotNull
	public Collection<GenericArgument> getArguments() {
		return getArgumentList().getCollection();
	}

	public void removeAllArguments() {
		getArgumentList().clear();
	}

	@Nullable
	public GenericArgument removeArgument(@NotNull GenericArgument argument) {
		return getArgumentList().remove(argument);
	}

	public boolean isArgumentListEmpty() {
		return getArgumentList().isEmpty();
	}

	@Nullable
	public GenericArgument removeArgument(@NotNull String name) {
		return getArgumentList().remove(name);
	}

	public void addArgument(@NotNull GenericArgument value) {
		getArgumentList().add(value);
	}

	public boolean containsArgument(@NotNull String name) {
		return getArgumentList().contains(name);
	}

	public void addAll(@NotNull Collection<? extends GenericArgument> arguments) {
		getArguments().addAll(arguments);
	}

	@Nullable
	public GenericArgument getArgument(@NotNull String name) {
		return getArgumentList().get(name);
	}

	@NotNull
	@Override
	public Iterator<GenericArgument> iterator() {
		return getArgumentList().iterator();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof GenericCommand)) {
			return false;
		}

		GenericCommand that = (GenericCommand) o;

		if (!this.argumentList.equals(that.argumentList)) {
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
		result = 31 * result + this.argumentList.hashCode();
		return result;
	}
}
