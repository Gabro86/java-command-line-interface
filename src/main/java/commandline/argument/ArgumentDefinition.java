package commandline.argument;

import commandline.annotation.CliArgument;
import commandline.argument.validator.ArgumentValidator;
import commandline.argument.validator.DefaultArgumentValidator;
import commandline.command.CommandLineException;
import commandline.exception.ArgumentNullException;
import commandline.language.parser.ArgumentParser;
import commandline.language.parser.MockArgumentParser;
import commandline.language.parser.specific.BooleanArgumentParser;
import commandline.language.parser.specific.CharacterArgumentParser;
import commandline.language.parser.specific.DoubleArgumentParser;
import commandline.language.parser.specific.FileArgumentParser;
import commandline.language.parser.specific.FloatArgumentParser;
import commandline.language.parser.specific.IntegerArgumentParser;
import commandline.language.parser.specific.LongArgumentParser;
import commandline.language.parser.specific.ShortArgumentParser;
import commandline.language.parser.specific.StringArgumentParser;
import commandline.language.parser.specific.UrlArgumentParser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;

public class ArgumentDefinition implements Comparable<ArgumentDefinition> {
	@Nullable
	private final String shortName;
	@NotNull
	private final String longName;
	@NotNull
	private final Class<?> valueClass;
	@NotNull
	private final ArgumentParser<?> parser;
	@NotNull
	private final ArgumentValidator<?> validator;
	private final boolean obligatory;
	@Nullable
	private final String defaultValue;
	@NotNull
	private final String description;
	@NotNull
	private final String[] examples;

	public ArgumentDefinition(@NotNull CliArgument definition, @NotNull Class<?> valueClass) {
		this(definition.longName(), definition.shortName(), valueClass, createCompatibleParser(definition.parser(), valueClass),
				createValidator(definition.validator()), definition.obligatory(), getDefaultValueFromAnnotation(definition),
				definition.description(), definition.examples());
	}

	public ArgumentDefinition(@NotNull String longName, @NotNull String shortName, @NotNull Class<?> valueClass,
			@NotNull ArgumentParser<?> parser, @NotNull ArgumentValidator<?> validator, boolean obligatory,
			@Nullable String defaultValue, @NotNull String description, @NotNull String[] examples) {
		super();

		//Validates and sets the short name. The short name is can be null, because it's not obligatory.
		this.shortName = processShortName(shortName);

		//Validates and sets the long name
		if (longName == null) {
			throw new CommandLineException();
		}
		this.longName = longName.trim();
		if (this.longName.isEmpty()) {
			throw new CommandLineException(
					"The long name could not been processed, because the passed long name doesn't contain any character.");
		}

		//Validates and sets the value type
		if (valueClass == null) {
			throw new ArgumentNullException();
		}
		this.valueClass = valueClass;

		//Validates and sets the parser class
		if (parser == null) {
			throw new ArgumentNullException();
		}
		if (!parser.isCompatibleWithOutputClass(valueClass)) {
			throw new IllegalArgumentException("The argument definition could not been created, because the passed " +
					"parser \"" + parser.getClass() + "\" is not compatible with the passed value class \"" + valueClass + "\"");
		}
		this.parser = parser;

		if (validator == null) {
			throw new ArgumentNullException();
		}
		//Tests if the validator is capable of validating the values of the passed value type.
		if (!validator.isCompatible(valueClass)) {
			throw new CommandLineException("The argument definition could not been created, because the passed validator class " +
					"can only validate values of the class \"" + validator.getSupportedClass().getSimpleName() + "\" and does not " +
					"validate instances of the passed value class \"" + valueClass.getSimpleName() + "\"");
		}
		this.validator = validator;

		//Validates and sets the default value and it's obligatory flag
		if (obligatory && defaultValue != null) {
			throw new CommandLineException("The argument definition could not been created, because it's marked as obligatory, " +
					"but nevertheless a default value was passed, too. No default value should be set on obligatory arguments.");
		}
		this.obligatory = obligatory;

		//defaultValue can be null
		this.defaultValue = defaultValue;

		//Validates and sets the description
		if (description == null) {
			throw new CommandLineException();
		}
		this.description = description.trim();
		if (this.description.isEmpty()) {
			throw new CommandLineException(
					"The description could not been processed, because the passed description doesn't contain any character.");
		}

		//Validates and sets the examples
		this.examples = processExamples(examples);
	}

	@NotNull
	public String getLongName() {
		return this.longName;
	}

	@Nullable
	public String getShortName() {
		return this.shortName;
	}

	@NotNull
	public Class<?> getValueClass() {
		return this.valueClass;
	}

	@NotNull
	public ArgumentParser<?> getParser() {
		return this.parser;
	}

	@NotNull
	public ArgumentValidator<?> getValidator() {
		return this.validator;
	}

	public boolean isObligatory() {
		return this.obligatory;
	}

	@Nullable
	public String getDefaultValue() {
		return this.defaultValue;
	}

	@NotNull
	public String getDescription() {
		return this.description;
	}

	@NotNull
	public String[] getExamples() {
		return this.examples;
	}

	@Override
	public int compareTo(@NotNull ArgumentDefinition definition) {
		if (definition == null) {
			throw new ArgumentNullException();
		}
		return getLongName().compareTo(definition.getLongName());
	}

	@Override
	public String toString() {
		return "ArgumentDefinition{" +
				"shortName='" + this.shortName + '\'' +
				", longName='" + this.longName + '\'' +
				", valueClass=" + this.valueClass +
				", parser=" + this.parser +
				", validator=" + this.validator +
				", obligatory=" + this.obligatory +
				", defaultValue='" + this.defaultValue + '\'' +
				", description='" + this.description + '\'' +
				", examples=" + Arrays.toString(this.examples) +
				'}';
	}

	/*
	 * This equals method was modified
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof ArgumentDefinition)) {
			return false;
		}

		ArgumentDefinition that = (ArgumentDefinition) o;

		if (this.obligatory != that.obligatory) {
			return false;
		}
		if (this.defaultValue != null ? !this.defaultValue.equals(that.defaultValue) : that.defaultValue != null) {
			return false;
		}
		if (!this.description.equals(that.description)) {
			return false;
		}
		if (!Arrays.equals(this.examples, that.examples)) {
			return false;
		}
		if (!this.longName.equals(that.longName)) {
			return false;
		}
		if (!this.parser.getClass().equals(that.parser.getClass())) {
			return false;
		}
		if (this.shortName != null ? !this.shortName.equals(that.shortName) : that.shortName != null) {
			return false;
		}
		if (!this.validator.getClass().equals(that.validator.getClass())) {
			return false;
		}
		if (!this.valueClass.equals(that.valueClass)) {
			return false;
		}

		return true;
	}

	/*
	 * This equals method was modified
	 */
	@Override
	public int hashCode() {
		int result = this.shortName != null ? this.shortName.hashCode() : 0;
		result = 31 * result + this.longName.hashCode();
		result = 31 * result + this.valueClass.hashCode();
		result = 31 * result + this.parser.getClass().hashCode();
		result = 31 * result + this.validator.getClass().hashCode();
		result = 31 * result + (this.obligatory ? 1 : 0);
		result = 31 * result + (this.defaultValue != null ? this.defaultValue.hashCode() : 0);
		result = 31 * result + this.description.hashCode();
		result = 31 * result + Arrays.hashCode(this.examples);
		return result;
	}

	@NotNull
	static String[] processExamples(@NotNull String[] examples) {
		LinkedList<String> processedExamples;
		String processedExample;

		if (examples == null) {
			throw new CommandLineException();
		}
		if (examples.length == 0) {
			throw new CommandLineException("The examples could not been processed, because no example was passed.");
		}

		processedExamples = new LinkedList<>();
		for (String example : examples) {
			if (example == null) {
				throw new CommandLineException(
						"The examples could not been processed, because at least one null example string was passed.");
			}
			processedExample = example.trim();
			if (processedExample.isEmpty()) {
				throw new CommandLineException(
						"The examples could not been processed, because at least one example with no character was passed.");
			}
			processedExamples.add(processedExample);
		}

		return processedExamples.toArray(new String[examples.length]);
	}

	static ArgumentParser<?> createCompatibleParser(Class<? extends ArgumentParser<?>> parserClass, Class<?> valueClass) {
		Class<? extends ArgumentParser<?>> compatibleParserClass;
		String message;
		ArgumentParser<?> parser;

		if (parserClass == null) {
			throw new ArgumentNullException();
		}
		if (valueClass == null) {
			throw new ArgumentNullException();
		}

		if (parserClass == MockArgumentParser.class) {
			compatibleParserClass = findCompatibleParser(valueClass);
			if (compatibleParserClass == null) {
				message = "The parser class could not been processed, because no compatible parser could been found for " +
						"the passed value class \"" + valueClass.getSimpleName() + "\".";
				throw new CommandLineException(message);
			}
		} else {
			compatibleParserClass = parserClass;
		}
		//Tests if the parser is capable of parsing the values of the passed value type.
		try {
			parser = compatibleParserClass.newInstance();
		} catch (@NotNull IllegalAccessException | InstantiationException e) {
			throw new CommandLineException(e.getMessage(), e);
		}

		return parser;
	}

	static ArgumentValidator<?> createValidator(Class<? extends ArgumentValidator<?>> validatorClass) {
		ArgumentValidator<?> validator;

		if (validatorClass == null) {
			throw new CommandLineException();
		}
		try {
			validator = validatorClass.newInstance();
		} catch (IllegalAccessException | InstantiationException e) {
			throw new CommandLineException(e.getMessage(), e);
		}

		return validator;
	}

	@Nullable
	static String processShortName(@Nullable String shortName) {
		String editShortName;
		String message;

		editShortName = shortName;
		if (editShortName != null && !editShortName.equals(CliArgument.nullValue)) {
			editShortName = editShortName.trim();
			if (editShortName.isEmpty()) {
				message = "The short name could not been processed, because the passed short name doesn't contain any character.";
				throw new CommandLineException(message);
			}
			if (editShortName.length() > 1) {
				message = "The short name could not been processed, because the passed short name contains more than one character.";
				throw new CommandLineException(message);
			}
		}

		return editShortName;
	}

	@Nullable
	static Class<? extends ArgumentParser<?>> findCompatibleParser(@NotNull Class<?> classToParse) {
		Class<? extends ArgumentParser<?>> parserClass;

		if (classToParse == null) {
			throw new ArgumentNullException();
		}

		parserClass = null;
		if (classToParse.equals(String.class)) {
			parserClass = StringArgumentParser.class;
		} else if (classToParse.equals(Short.class) || classToParse.equals(short.class)) {
			parserClass = ShortArgumentParser.class;
		} else if (classToParse.equals(Integer.class) || classToParse.equals(int.class)) {
			parserClass = IntegerArgumentParser.class;
		} else if (classToParse.equals(Long.class) || classToParse.equals(long.class)) {
			parserClass = LongArgumentParser.class;
		} else if (classToParse.equals(Character.class) || classToParse.equals(char.class)) {
			parserClass = CharacterArgumentParser.class;
		} else if (classToParse.equals(Boolean.class) || classToParse.equals(boolean.class)) {
			parserClass = BooleanArgumentParser.class;
		} else if (classToParse.equals(Float.class) || classToParse.equals(float.class)) {
			parserClass = FloatArgumentParser.class;
		} else if (classToParse.equals(Double.class) || classToParse.equals(double.class)) {
			parserClass = DoubleArgumentParser.class;
		} else if (classToParse.equals(File.class)) {
			parserClass = FileArgumentParser.class;
		} else if (classToParse.equals(URL.class)) {
			parserClass = UrlArgumentParser.class;
		}

		return parserClass;
	}

	@Nullable
	static String getDefaultValueFromAnnotation(@NotNull CliArgument annotation) {
		String defaultValue;
		String value;

		if (annotation == null) {
			throw new ArgumentNullException();
		}
		defaultValue = annotation.defaultValue();
		if (defaultValue.equals(CliArgument.nullValue)) {
			value = null;
		} else {
			value = defaultValue;
		}

		return value;
	}

	@NotNull
	public static ArgumentDefinition createMock() {
		return new ArgumentDefinition("test-argument", "t", String.class, new StringArgumentParser(), new DefaultArgumentValidator(),
				false, null, "This is a test argument.", new String[] {"Test example"});
	}
}
