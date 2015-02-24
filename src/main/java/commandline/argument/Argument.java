package commandline.argument;

import commandline.argument.validator.ArgumentValidator;
import commandline.argument.validator.DefaultArgumentValidator;
import commandline.command.CommandLineException;
import commandline.exception.ArgumentNullException;
import commandline.language.parser.ArgumentParser;
import commandline.language.parser.specific.StringArgumentParser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * User: gno Date: 21.06.13 Time: 15:12
 */
public class Argument<ValueType> {
	@NotNull
	private final ArgumentDefinition definition;
	@Nullable
	private final ValueType value;

	public Argument(@NotNull String longName, @NotNull String shortName, @NotNull Class<?> valueClass,
			@NotNull ArgumentParser<?> parser, @NotNull ArgumentValidator<?> validator, boolean obligatory,
			@Nullable String defaultValue, @NotNull String description, @NotNull String[] examples, @Nullable ValueType value) {
		this(new ArgumentDefinition(longName, shortName, valueClass, parser, validator, obligatory, defaultValue, description,
				examples), value);
	}

	public Argument(@NotNull ArgumentDefinition definition, @Nullable ValueType value) {
		super();
		if (definition == null) {
			throw new ArgumentNullException();
		}
		this.definition = definition;
		validateValue(definition, value);
		this.value = value;
	}

	@NotNull
	public ArgumentDefinition getDefinition() {
		return this.definition;
	}

	@Nullable
	public Object getValue() {
		return this.value;
	}

	@NotNull
	public String getLongName() {
		return getDefinition().getLongName();
	}

	@NotNull
	public String getShortName() {
		return getDefinition().getShortName();
	}

	@NotNull
	public Class<?> getValueClass() {
		return getDefinition().getValueClass();
	}

	@NotNull
	public ArgumentParser<?> getParser() {
		return getDefinition().getParser();
	}

	public boolean isObligatory() {
		return getDefinition().isObligatory();
	}

	@Nullable
	public String getDefaultValue() {
		return getDefinition().getDefaultValue();
	}

	@NotNull
	public String getDescription() {
		return getDefinition().getDescription();
	}

	@NotNull
	public String[] getExamples() {
		return getDefinition().getExamples();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Argument)) {
			return false;
		}

		Argument argument = (Argument) o;

		if (!this.definition.equals(argument.definition)) {
			return false;
		}
		if (this.value != null ? !this.value.equals(argument.value) : argument.value != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = this.definition.hashCode();
		result = 31 * result + (this.value != null ? this.value.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Argument{" +
				"definition=" + this.definition +
				", value=" + this.value +
				'}';
	}

	@SuppressWarnings("unchecked")
	static <T> void validateValue(@NotNull ArgumentDefinition definition, @Nullable T value) {
		Class<?> valueClass;
		Class<?> valueClassFromDefinition;
		ArgumentValidator validator;

		/*
		 * Value can be null. A null value means that the user did not pass the argument to the cli. There is no other way for the
		 * user to pass a null as value for an argument.
		 *
		 * Value is not the cli value that was passed by the user. Instead it's the value that was parsed from the value the user
		 * passed.
		 */
		if (value == null) {
			if (definition.isObligatory()) {
				throw new CommandLineException("The validation of the argument \"" + definition.getLongName() + "\" failed, " +
						"because the value is obligatory but no value was passed.");
			}
		} else {
			//Tests if the value is from a class that is defined in the argument definition.
			valueClass = value.getClass();
			valueClassFromDefinition = definition.getValueClass();
			if (!valueClassFromDefinition.isAssignableFrom(valueClass)) {
				throw new CommandLineException("The validation of the argument \"" + definition.getLongName() + "\" failed, " +
						"because the value with the class \"" + valueClass.getSimpleName() + "\" is not an instance of the " +
						"class \"" + valueClassFromDefinition.getSimpleName() + "\" defined in the argument definition.");
			}
		}
		validator = definition.getValidator();
		validator.validate(value);
	}

	@NotNull
	public static Argument<String> createMock() {
		Argument<String> argument;
		ArgumentDefinition definition;

		definition = new ArgumentDefinition("longName", "s", String.class, new StringArgumentParser(), new DefaultArgumentValidator(),
				true, null, "description", new String[] {"example"});
		argument = new Argument<>(definition, "test-value");

		return argument;
	}
}
