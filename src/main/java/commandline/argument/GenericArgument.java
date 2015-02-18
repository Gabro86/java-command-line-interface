package commandline.argument;

import commandline.exception.ArgumentNullException;
import org.jetbrains.annotations.NotNull;

/**
 * User: gno, Date: 06.01.2015 - 15:24
 */
public class GenericArgument {
	@NotNull
	private final String name;
	@NotNull
	private final String value;

	public GenericArgument(@NotNull String name, @NotNull String value) {
		super();

		if (name == null) {
			throw new ArgumentNullException();
		}
		if (value == null) {
			throw new ArgumentNullException();
		}

		String editName;

		if (name == null) {
			throw new ArgumentNullException();
		}
		editName = name.trim();
		if (editName.isEmpty()) {
			throw new IllegalArgumentException(
					"The generic argument could not been created, because the passed name doesn't contain any character.");
		}
		this.name = editName;
		//value can be null
		this.value = value;
	}

	@NotNull
	public String getName() {
		return this.name;
	}

	@NotNull
	public String getValue() {
		return this.value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof GenericArgument)) {
			return false;
		}

		GenericArgument argument = (GenericArgument) o;

		if (!this.name.equals(argument.name)) {
			return false;
		}
		if (!this.value.equals(argument.value)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = this.name.hashCode();
		result = 31 * result + this.value.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "GenericArgument{" +
				"name='" + this.name + '\'' +
				", value='" + this.value + '\'' +
				'}';
	}

	@NotNull
	public static GenericArgument createMock() {
		return new GenericArgument("MockGenericArgument", "MockValue");
	}
}
