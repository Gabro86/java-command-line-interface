package commandline.argument;

import commandline.exception.ArgumentNullException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * User: gno, Date: 06.01.2015 - 15:24
 */
public class GenericArgument {
	@NotNull
	private final String name;
	@Nullable
	private final String value;

	public GenericArgument(@NotNull String name, @Nullable String value) {
		super();

		String editName;

		//The field value can be null;
		if (name == null) {
			throw new ArgumentNullException();
		}
		editName = name.trim();
		if (editName.isEmpty()) {
			throw new IllegalArgumentException(
					"The generic argument could not been created, because the passed name doesn't contain any character.");
		}
		this.name = editName;
		this.value = value;
	}

	@NotNull
	public String getName() {
		return this.name;
	}

	@Nullable
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
	public String toString() {
		return "GenericArgument{" +
				"name='" + this.name + '\'' +
				", value='" + this.value + '\'' +
				'}';
	}

	@Override
	public int hashCode() {
		int result = this.name.hashCode();
		result = 31 * result + this.value.hashCode();
		return result;
	}

	@NotNull
	public static GenericArgument createMock() {
		return new GenericArgument("MockGenericArgument", "MockValue");
	}
}
