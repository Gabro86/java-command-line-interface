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
public class ArgumentList implements Iterable<Argument<?>> {
	/*
	 * The arguments must be accessible using the short and the long name. To improve the performance while retrieving the arguments
	 * two maps are used. One with argument short name keys and one with argument long name keys.
	 * Don't put both the short and long names into one map, because the arguments would be added multiple times to the map, since
	 * an argument usually has both a long and a short name. Some method would behave in a strange manner, e.g. the iterator()
	 * method would return the same argument twice, because the map contains both the short and the long name. The
	 * getSize() would return an wrong argument count.
	 */

	@NotNull
	private final HashMap<String, Argument<?>> longNameArguments;
	@NotNull
	private final HashMap<String, Argument<?>> shortNameArguments;

	public ArgumentList() {
		super();
		this.longNameArguments = new HashMap<>();
		this.shortNameArguments = new HashMap<>();
	}

	@NotNull
	private HashMap<String, Argument<?>> getLongNameArguments() {
		return this.longNameArguments;
	}

	@NotNull
	private HashMap<String, Argument<?>> getShortNameArguments() {
		return this.shortNameArguments;
	}

	public int getSize() {
		return getLongNameArguments().size();
	}

	public boolean isEmpty() {
		return getLongNameArguments().isEmpty();
	}

	/**
	 * Returns the argument associated with the name.
	 *
	 * @param name The short or long name of the argument.
	 * @return The argument if it was found and null otherwise.
	 */
	@Nullable
	public Argument<?> get(@NotNull String name) {
		Argument<?> argument;

		if (name == null) {
			throw new ArgumentNullException();
		}
		if (name.trim().isEmpty()) {
			throw new IllegalArgumentException(
					"The argument could not been returned, because the passed name doesn't contain any character.");
		}
		/*
		 * Retrieves the argument by using the passed name as long name. If the argument could not been found the passed name is
		 * used as short name.
		 */
		argument = getLongNameArguments().get(name);
		if (argument == null) {
			argument = getShortNameArguments().get(name);
		}

		return argument;
	}

	/**
	 * Returns a unique and unmodifiable list of the arguments in this list.
	 *
	 * @return Unmodifiable argument collection
	 */
	@NotNull
	public Collection<Argument<?>> getCollection() {
		return Collections.unmodifiableCollection(getLongNameArguments().values());
	}

	/**
	 * Tests if an argument with a specific name exists.
	 *
	 * @param name The short or long name of the argument
	 * @return Returns true if the argument exists and false otherwise.
	 */
	public boolean contains(@NotNull String name) {
		if (name == null) {
			throw new ArgumentNullException();
		}
		if (name.trim().isEmpty()) {
			throw new IllegalArgumentException(
					"The argument existence could not been tested, because the passed name doesn't contain any character.");
		}
		return getLongNameArguments().containsKey(name) || getShortNameArguments().containsKey(name);
	}

	public boolean contains(@NotNull Argument<?> argument) {
		String longName;
		String shortName;
		boolean containsArgument;

		if (argument == null) {
			throw new ArgumentNullException();
		}
		longName = argument.getLongName();
		shortName = argument.getShortName();
		containsArgument = getLongNameArguments().containsKey(longName) || getShortNameArguments().containsKey(shortName);

		return containsArgument;
	}

	public void add(@NotNull Argument<?> argument) {
		if (argument == null) {
			throw new ArgumentNullException();
		}
		/*
		 * Puts the argument into the argument maps with both the argument short and long name.
		 */
		getLongNameArguments().put(argument.getLongName(), argument);
		if (argument.getShortName() != null) {
			getShortNameArguments().put(argument.getShortName(), argument);
		}
	}

	@SuppressWarnings("Convert2streamapi")
	public void addAll(@NotNull Collection<? extends Argument<?>> arguments) {
		for (Argument<?> argument : arguments) {
			add(argument);
		}
	}

	@Nullable
	public Argument<?> remove(@NotNull String name) {
		Argument<?> longNameArgument;
		Argument<?> shortNameArgument;
		HashMap<String, Argument<?>> longNameArguments;
		HashMap<String, Argument<?>> shortNameArguments;

		if (name == null) {
			throw new ArgumentNullException();
		}
		if (name.trim().isEmpty()) {
			throw new IllegalArgumentException(
					"The argument could not been removed, because the passed name doesn't contain any character.");
		}
		shortNameArguments = getShortNameArguments();
		longNameArguments = getLongNameArguments();
		longNameArgument = longNameArguments.remove(name);
		if (longNameArgument == null) {
			shortNameArgument = shortNameArguments.remove(name);
			if (shortNameArgument == null) {
				return null;
			} else {
				longNameArguments.remove(shortNameArgument.getLongName());
				return shortNameArgument;
			}
		} else {
			shortNameArguments.remove(longNameArgument.getShortName());
			return longNameArgument;
		}
	}

	@Nullable
	public Argument<?> remove(@NotNull Argument<?> argument) {
		return remove(argument.getLongName());
	}

	public void clear() {
		getLongNameArguments().clear();
		getShortNameArguments().clear();
	}

	/**
	 * Returns the iterator for all arguments. The arguments returned are unique.
	 *
	 * @return Iterator for all arguments contained in this argument list.
	 */
	@NotNull
	@Override
	public Iterator<Argument<?>> iterator() {
		return getLongNameArguments().values().iterator();
	}

	@NotNull
	public Argument<?>[] toArray() {
		return (Argument<?>[]) getLongNameArguments().values().toArray();
	}

	@NotNull
	@Override
	public String toString() {
		return "ArgumentList{" +
				"arguments=" + this.longNameArguments +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof ArgumentList)) {
			return false;
		}

		ArgumentList that = (ArgumentList) o;

		if (!this.longNameArguments.equals(that.longNameArguments)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return this.longNameArguments.hashCode();
	}
}
