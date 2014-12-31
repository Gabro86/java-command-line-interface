package commandline.argument;

import commandline.exception.ArgumentNullException;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * User: gno, Date: 02.08.13 - 17:04
 */
public class StringArgumentMap {
	private final HashMap<String, String> arguments;
	private final String commandName;

	public StringArgumentMap(String commandName) {
		super();
		this.arguments = new HashMap<>();
		this.commandName = commandName;
	}

	public HashMap<String, String> getArguments() {
		return this.arguments;
	}

	public String getCommandName() {
		return this.commandName;
	}

	public int getSize() {
		return this.arguments.size();
	}

	public boolean containsValue(String value) {
		if (value == null) {
			throw new ArgumentNullException();
		}
		return this.arguments.containsValue(value);
	}

	public Set<String> keySet() {
		return this.arguments.keySet();
	}

	public String get(String key) {
		if (key == null) {
			throw new ArgumentNullException();
		}
		if (key.isEmpty()) {
			throw new IllegalArgumentException("The key could not been retrieved, because the passed key does not contain any char.");
		}
		return this.arguments.get(key);
	}

	public String remove(String key) {
		if (key == null) {
			throw new ArgumentNullException();
		}
		if (key.isEmpty()) {
			throw new IllegalArgumentException("The value could not been removed, because the passed key does not contain any char.");
		}
		return this.arguments.remove(key);
	}

	public Set<Map.Entry<String, String>> entrySet() {
		return this.arguments.entrySet();
	}

	public String put(String key, String value) {
		if (key == null) {
			throw new ArgumentNullException();
		}
		if (key.isEmpty()) {
			throw new IllegalArgumentException("The key could not been added, because the passed key does not contain any char.");
		}
		if (value == null) {
			throw new ArgumentNullException();
		}
		return this.arguments.put(key, value);
	}

	public boolean isEmpty() {
		return this.arguments.isEmpty();
	}

	public boolean containsKey(String key) {
		if (key.isEmpty()) {
			throw new IllegalArgumentException(
					"The key existence could not been tested, because the passed key does not contain any char.");
		}
		return this.arguments.containsKey(key);
	}

	public void clear() {
		this.arguments.clear();
	}

	public Collection<String> values() {
		return this.arguments.values();
	}

	@NotNull
	public String[] toArray() {
		String[] arguments;
		LinkedList<String> argumentList;
		String key;
		Object value;

		argumentList = new LinkedList<>();
		argumentList.add(getCommandName());
		for (Map.Entry<String, String> entry : getArguments().entrySet()) {
			key = entry.getKey();
			value = entry.getValue();
			argumentList.add("--" + key);
			argumentList.add(value.toString());
		}
		arguments = argumentList.toArray(new String[getSize()]);

		return arguments;
	}
}
