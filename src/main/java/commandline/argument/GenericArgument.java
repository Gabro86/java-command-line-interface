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

	@Nullable
	public String getValue() {
		return this.value;
	}

	@NotNull
	public static GenericArgument createMock() {
		return new GenericArgument("MockGenericArgument", "MockValue");
	}
}
