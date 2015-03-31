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
 * This class represents an argument of a command line command. The argument is composed of the argument name and the argument
 * value.<p>Example:</p><p>git-status <b>--branch master</b></p><p>The bold text represents the command line argument</p>
 * @param <ValueType> The type of the values this argument should be restricted to.
 */
public class Argument<ValueType> {
	/**
	 * Definition of this command line argument
	 */
	@NotNull
	private final ArgumentDefinition definition;
	/**
	 * Value of this command line argument
	 */
	@Nullable
	private final ValueType value;

	/**
	 * Creates a command line argument instance.
	 * @param longName The long name of this command line argument
	 * @param shortName The short name of this command line argument. The short name is exactly one character long. It can be used by
	 * the user instead of the long name. This saves time when entering the command.
	 * @param valueClass The type of the value of this command line argument.
	 * @param parser The parser to use to parse the string argument value passed by the user throw the command line. The parser parses
	 * the string value and converts it into the type needed by this command line argument.
	 * @param validator The validator to use to validate the value passed by the user throw the command line .
	 * @param obligatory Defines if this command line argument is obligatory or optional. If this argument is optional, then the user
	 * doesn't need to pass it to the command line and a default value will be used.
	 * @param defaultValue The default value to use when this command line argument is optional and the user didn't pass a value for
	 * this argument. If the command line argument is marked as obligatory the default value must be null.
	 * @param description The description to show in the help of the command line interface for this command line argument.
	 * @param examples The example values to show in the help of the command line interface for this command line argument.
	 * @param value The value of this command line argument.
	 */
	public Argument(@NotNull String longName, @NotNull String shortName, @NotNull Class<?> valueClass,
			@NotNull ArgumentParser<?> parser, @NotNull ArgumentValidator<?> validator, boolean obligatory,
			@Nullable String defaultValue, @NotNull String description, @NotNull String[] examples, @Nullable ValueType value) {
		this(new ArgumentDefinition(longName, shortName, valueClass, parser, validator, obligatory, defaultValue, description,
				examples), value);
	}

	/**
	 * Creates a command line argument instance
	 * @param definition The definition of this command line argument.
	 * @param value The value of this command line argument.
	 */
	public Argument(@NotNull ArgumentDefinition definition, @Nullable ValueType value) {
		super();
		if (definition == null) {
			throw new ArgumentNullException();
		}
		this.definition = definition;
		validateValue(definition, value);
		this.value = value;
	}

	/**
	 * Returns the definition of this command line argument.
	 */
	@NotNull
	public ArgumentDefinition getDefinition() {
		return this.definition;
	}

	/**
	 * Return the value of this command line argument.
	 */
	@Nullable
	public Object getValue() {
		return this.value;
	}

	/**
	 * Returns the long name.
	 */
	@NotNull
	public String getLongName() {
		return getDefinition().getLongName();
	}

	/**
	 * Returns the short name
	 */
	@NotNull
	public String getShortName() {
		return getDefinition().getShortName();
	}

	/**
	 * Returns the type of the values of this command line argument.
	 */
	@NotNull
	public Class<?> getValueClass() {
		return getDefinition().getValueClass();
	}

	/**
	 * Returns the parser to use to parse the string argument value passed by the user throw the command line.
	 */
	@NotNull
	public ArgumentParser<?> getParser() {
		return getDefinition().getParser();
	}

	/**
	 * Returns the validator class to use to validate the argument value after is has been parsed.
	 */
	@NotNull
	public ArgumentValidator<?> getValidator() {
		return getDefinition().getValidator();
	}

	/**
	 * Defines if this argument must be passed throw command line.
	 * @return Returns true if this command line argument is obligatory and otherwise false.
	 */
	public boolean isObligatory() {
		return getDefinition().isObligatory();
	}

	/**
	 * Returns the default value to use when this command line argument is optional and the user didn't pass a value for this argument
	 * @return Returns the default value or null if no default value is set.
	 */
	@Nullable
	public String getDefaultValue() {
		return getDefinition().getDefaultValue();
	}

	/**
	 * Returns the description to show in the help of the command line interface for this command line argument.
	 */
	@NotNull
	public String getDescription() {
		return getDefinition().getDescription();
	}

	/**
	 * Returns the example values to show in the help of the command line interface for this command line argument.
	 */
	@NotNull
	public String[] getExamples() {
		return getDefinition().getExamples();
	}

	/**
	 * Compares this command line argument with another.
	 * @param o The other command line argument to compare this argument with.
	 * @return Returns true if the arguments are equal and false otherwise.
	 */
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

	/**
	 * Calculates the hash code
	 * @return Returns the calculated hash code.
	 */
	@Override
	public int hashCode() {
		int result = this.definition.hashCode();
		result = 31 * result + (this.value != null ? this.value.hashCode() : 0);
		return result;
	}

	/**
	 * Creates a text representation of this command line argument.
	 * @return Returns a text representation of this command line argument.
	 */
	@Override
	public String toString() {
		return "Argument{" +
				"definition=" + this.definition +
				", value=" + this.value +
				'}';
	}

	/**
	 * Validates a value by testing if it's compatible with an argument definition.
	 * @param definition The argument definition to use for the validation of the value.
	 * @param value The value to validate
	 */
	@SuppressWarnings("unchecked")
	static void validateValue(@NotNull ArgumentDefinition definition, @Nullable Object value) {
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

	/**
	 * Creates a mock command line argument.
	 * @return Returns a new mock command line argument.
	 */
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
