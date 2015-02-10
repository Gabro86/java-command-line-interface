package commandline.argument;

import commandline.exception.ArgumentNullException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class ArgumentDefinitionList implements Iterable<ArgumentDefinition> {
	@NotNull
	private final HashMap<String, ArgumentDefinition> longNameDefinitions;
	@NotNull
	private final HashMap<String, ArgumentDefinition> shortNameDefinitions;

	public ArgumentDefinitionList() {
		super();
		this.longNameDefinitions = new HashMap<>();
		this.shortNameDefinitions = new HashMap<>();
	}

	@NotNull
	private HashMap<String, ArgumentDefinition> getLongNameDefinitions() {
		return this.longNameDefinitions;
	}

	@NotNull
	private HashMap<String, ArgumentDefinition> getShortNameDefinitions() {
		return this.shortNameDefinitions;
	}

	public int getSize() {
		return getLongNameDefinitions().size();
	}

	public boolean isEmpty() {
		return getLongNameDefinitions().isEmpty();
	}

	/**
	 * Returns the argument definition associated with the name.
	 *
	 * @param name The short or long name of the argument definition.
	 * @return Returns the argument definition if it was found and null otherwise.
	 */
	@Nullable
	public ArgumentDefinition get(@NotNull String name) {
		ArgumentDefinition definition;

		if (name == null) {
			throw new ArgumentNullException();
		}
		if (name.trim().isEmpty()) {
			throw new IllegalArgumentException(
					"The argument could not been retrieved, because the passed argument name doesn't contain any character.");
		}
		/*
		 * Retrieves the argument by using the passed name as long name. If the argument could not been found the passed name is
		 * used as short name.
		 */
		definition = getLongNameDefinitions().get(name);
		if (definition == null) {
			definition = getShortNameDefinitions().get(name);
		}

		return definition;
	}

	@NotNull
	public Collection<ArgumentDefinition> getCollection() {
		return Collections.unmodifiableCollection(getLongNameDefinitions().values());
	}

	/**
	 * Tests if an argument definition with a specific name exists.
	 *
	 * @param name The short or long name of the argument definition.
	 * @return Returns true if the argument definition exists and false otherwise.
	 */
	public boolean contains(@NotNull String name) {
		if (name == null) {
			throw new ArgumentNullException();
		}
		if (name.trim().isEmpty()) {
			throw new IllegalArgumentException(
					"The argument existence could not been tested, because the passed argument name doesn't contain any character.");
		}
		return getLongNameDefinitions().containsKey(name) || getShortNameDefinitions().containsKey(name);
	}

	public boolean contains(@NotNull ArgumentDefinition definition) {
		String longName;
		String shortName;
		boolean containsDefinition;

		if (definition == null) {
			throw new ArgumentNullException();
		}
		longName = definition.getLongName();
		shortName = definition.getShortName();
		containsDefinition = getLongNameDefinitions().containsKey(longName) || getShortNameDefinitions().containsKey(shortName);

		return containsDefinition;
	}

	public void add(@NotNull ArgumentDefinition definition) {
		if (definition == null) {
			throw new ArgumentNullException();
		}
		/*
		 * Puts the argument definition into the argument definition maps with both the argument definition short and long name.
		 */
		getLongNameDefinitions().put(definition.getLongName(), definition);
		if (definition.getShortName() != null) {
			getShortNameDefinitions().put(definition.getShortName(), definition);
		}
	}

	public void addAll(@NotNull Collection<ArgumentDefinition> definitions) {
		if (definitions == null) {
			throw new ArgumentNullException();
		}
		for (ArgumentDefinition definition : definitions) {
			add(definition);
		}
	}

	@Nullable
	public ArgumentDefinition remove(@NotNull String name) {
		ArgumentDefinition longNameDefinition;
		ArgumentDefinition shortNameDefinition;
		HashMap<String, ArgumentDefinition> longNameDefinitions;
		HashMap<String, ArgumentDefinition> shortNameDefinitions;

		if (name == null) {
			throw new ArgumentNullException();
		}
		if (name.trim().isEmpty()) {
			throw new IllegalArgumentException(
					"The argument could not been removed, because the passed name doesn't contain any character.");
		}
		shortNameDefinitions = getShortNameDefinitions();
		longNameDefinitions = getLongNameDefinitions();
		longNameDefinition = longNameDefinitions.remove(name);
		if (longNameDefinition == null) {
			shortNameDefinition = shortNameDefinitions.remove(name);
			if (shortNameDefinition == null) {
				return null;
			} else {
				longNameDefinitions.remove(shortNameDefinition.getLongName());
				return shortNameDefinition;
			}
		} else {
			shortNameDefinitions.remove(longNameDefinition.getShortName());
			return longNameDefinition;
		}
	}

	@Nullable
	public ArgumentDefinition remove(@NotNull ArgumentDefinition definition) {
		return remove(definition.getLongName());
	}

	public void clear() {
		getLongNameDefinitions().clear();
		getShortNameDefinitions().clear();
	}

	/**
	 * Returns the iterator for all argument definitions. The arguments definitions returned are unique.
	 *
	 * @return Iterator for all argument definitions contained in this argument definition list.
	 */
	@NotNull
	@Override
	public Iterator<ArgumentDefinition> iterator() {
		return getLongNameDefinitions().values().iterator();
	}

	@NotNull
	@Override
	public String toString() {
		return "ArgumentDefinitionList{" +
				"definitions=" + this.longNameDefinitions +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof ArgumentDefinitionList)) {
			return false;
		}

		ArgumentDefinitionList that = (ArgumentDefinitionList) o;

		if (this.longNameDefinitions != null ? !this.longNameDefinitions.equals(that.longNameDefinitions) :
				that.longNameDefinitions != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return this.longNameDefinitions != null ? this.longNameDefinitions.hashCode() : 0;
	}
}
