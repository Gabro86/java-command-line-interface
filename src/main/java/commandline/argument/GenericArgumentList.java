package commandline.argument;

import commandline.exception.ArgumentNullException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

/**
 * User: gno, Date: 06.01.2015 - 15:27
 */
public class GenericArgumentList implements Iterable<GenericArgument> {
	@NotNull
	private final HashMap<String, GenericArgument> arguments;

	public GenericArgumentList() {
		super();
		this.arguments = new HashMap<>();
	}

	@NotNull
	private HashMap<String, GenericArgument> getArguments() {
		return this.arguments;
	}

	public int getSize() {
		return getArguments().size();
	}

	public boolean isEmpty() {
		return getArguments().isEmpty();
	}

	@Nullable
	public GenericArgument get(@NotNull String name) {
		if (name == null) {
			throw new ArgumentNullException();
		}
		if (name.trim().isEmpty()) {
			throw new IllegalArgumentException(
					"The argument could not been returned, because the passed name doesn't contain any character.");
		}
		return getArguments().get(name);
	}

	@NotNull
	public Collection<GenericArgument> getCollection() {
		return Collections.unmodifiableCollection(getArguments().values());
	}

	public boolean contains(@NotNull String name) {
		if (name == null) {
			throw new ArgumentNullException();
		}
		if (name.trim().isEmpty()) {
			throw new IllegalArgumentException(
					"The argument existence could not been tested, because the passed name doesn't contain any character.");
		}
		return getArguments().containsKey(name);
	}

	public boolean contains(@NotNull GenericArgument argument) {
		if (argument == null) {
			throw new ArgumentNullException();
		}
		return getArguments().containsValue(argument);
	}

	public void add(@NotNull GenericArgument argument) {
		if (argument == null) {
			throw new ArgumentNullException();
		}
		getArguments().put(argument.getName(), argument);
	}

	@SuppressWarnings("Convert2streamapi")
	public void addAll(@NotNull Collection<? extends GenericArgument> arguments) {
		for (GenericArgument argument : arguments) {
			add(argument);
		}
	}

	@Nullable
	public GenericArgument remove(@NotNull String name) {
		if (name == null) {
			throw new ArgumentNullException();
		}
		if (name.trim().isEmpty()) {
			throw new IllegalArgumentException(
					"The argument could not been removed, because the passed name doesn't contain any character.");
		}
		return getArguments().remove(name);
	}

	@Nullable
	public GenericArgument remove(@NotNull GenericArgument argument) {
		if (argument == null) {
			throw new ArgumentNullException();
		}
		return getArguments().remove(argument.getName());
	}

	public void clear() {
		getArguments().clear();
	}

	@NotNull
	@Override
	public Iterator<GenericArgument> iterator() {
		return getArguments().values().iterator();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof GenericArgumentList)) {
			return false;
		}

		GenericArgumentList that = (GenericArgumentList) o;

		if (!this.arguments.equals(that.arguments)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return this.arguments.hashCode();
	}
}
