package commandline.argument;

import commandline.exception.ArgumentNullException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * User: gno Date: 25.06.13 Time: 14:02
 */
public class ArgumentList {
	@NotNull
	private final HashMap<String, Argument<?>> arguments;

	public ArgumentList() {
		super();
		this.arguments = new HashMap<>();
	}

	@NotNull
	private HashMap<String, Argument<?>> getArguments() {
		return this.arguments;
	}

	@NotNull
	public List<Argument<?>> getArgumentList() {
		return Collections.unmodifiableList(new LinkedList<>(getArguments().values()));
	}

	public int getSize() {
		return getArguments().size();
	}

	public boolean contains(String value) {
		return getArguments().containsKey(value);
	}

	public void put(@NotNull Argument<?> argument) {
		//noinspection ConstantConditions
		if (argument == null) {
			throw new ArgumentNullException();
		}
		getArguments().put(argument.getLongName(), argument);
	}

	@Nullable
	public Argument<?> get(@NotNull String longName) {
		//noinspection ConstantConditions
		if (longName == null) {
			throw new ArgumentNullException();
		}
		return getArguments().get(longName);
	}

	@Nullable
	public Argument<?> remove(@NotNull String longName) {
		//noinspection ConstantConditions
		if (longName == null) {
			throw new ArgumentNullException();
		}
		return getArguments().remove(longName);
	}

	public boolean isEmpty() {
		return getArguments().isEmpty();
	}

	public void clear() {
		getArguments().clear();
	}

	@NotNull
	public String[] toArray() {
		String[] arguments;
		LinkedList<String> argumentList;
		String key;
		Object value;
		String valueString;

		argumentList = new LinkedList<>();
		for (Map.Entry<String, Argument<?>> entry : getArguments().entrySet()) {
			//Boolean argument haben keinen Wert
			key = entry.getKey();
			value = entry.getValue().getValue();
			argumentList.add(key);
			if (value == null) {
				valueString = "null";
			} else {
				valueString = value.toString();
			}
			argumentList.add(valueString);
		}
		arguments = argumentList.toArray(new String[getSize()]);

		return arguments;
	}
}
