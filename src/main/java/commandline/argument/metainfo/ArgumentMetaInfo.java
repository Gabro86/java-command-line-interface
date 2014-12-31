package commandline.argument.metainfo;

import commandline.argument.validator.ArgumentValidator;
import commandline.command.CommandLineException;
import commandline.language.parser.argument.*;
import commandline.exception.ArgumentNullException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.net.URL;
import java.util.Arrays;

public class ArgumentMetaInfo implements Comparable<ArgumentMetaInfo> {
	@NotNull
	private final String shortName;
	@NotNull
	private final String longName;
	@NotNull
	private final Class<?> valueType;
	@NotNull
	private final Class<? extends ArgumentParser<?>> parser;
	@NotNull
	private final Class<? extends ArgumentValidator<?>> validator;
	private final boolean obligatory;
	@Nullable
	private final String defaultValue;
	@NotNull
	private final String description;
	@NotNull
	private final String[] examples;

	public ArgumentMetaInfo(@NotNull CommandArgument metaInfo, @NotNull Class<?> valueType) {
		this(metaInfo.longName(), metaInfo.shortName(), valueType, getCompatibleParser(metaInfo, valueType), metaInfo.validator(),
				metaInfo.obligatory(), getDefaultValue(metaInfo), metaInfo.description(), metaInfo.examples());
	}

	public ArgumentMetaInfo(@NotNull String longName, @NotNull String shortName, @NotNull Class<?> valueType,
			@NotNull Class<? extends ArgumentParser<?>> parserClass, @NotNull Class<? extends ArgumentValidator<?>> validatorClass,
			boolean obligatory, @Nullable String defaultValue, @NotNull String description, @NotNull String[] examples) {
		super();

		ArgumentParser<?> parser;
		ArgumentValidator<?> validator;

		//defaultValue can be null
		if (longName == null) {
			throw new ArgumentNullException();
		}
		if (longName.isEmpty()) {
			throw new CommandLineException("The meta info could not been created, because the passed long name is empty.");
		}
		if (shortName == null) {
			throw new ArgumentNullException();
		}
		if (shortName.isEmpty()) {
			throw new CommandLineException("The meta info could not been created, because the passed short name is empty.");
		}
		if (shortName.length() > 1) {
			throw new CommandLineException(
					"The meta info could not been created, because the passed short name contains more than one char.");
		}
		if (valueType == null) {
			throw new ArgumentNullException();
		}
		if (validatorClass == null) {
			throw new ArgumentNullException();
		}
		validator = createValidator(validatorClass);
		if (! validator.isCompatible(valueType)) {
			throw new CommandLineException(String.format("The meta info could not been created, " +
					"because the passed validator can only validate values of the type \"%s\" and does not validate instances of " +
					"the passed argument type \"%s\"", validator.getSupportedClass().getSimpleName(), valueType.getSimpleName()));
		}
		parser = createParser(parserClass);
		if (! parser.isCompatible(valueType)) {
			throw new CommandLineException(String.format("The meta info could not been created, " +
					"because the passed parser creates instances from the type \"%s\" that are not compatible with the argument " +
					"type \"%s\"", parser.getValueType().getSimpleName(), valueType.getSimpleName()));
		}
		if (description == null) {
			throw new ArgumentNullException();
		}
		if (description.isEmpty()) {
			throw new CommandLineException("The meta info could not been created, because the passed description is empty.");
		}
		if (examples == null) {
			throw new ArgumentNullException();
		}
		if (examples.length == 0) {
			throw new CommandLineException("The meta info could not been created, because no example was passed.");
		}
		for (String example : examples) {
			if (example == null || example.isEmpty()) {
				throw new CommandLineException(
						"The meta info could not been created, because at least one example with no value was passed.");
			}
		}
		if (obligatory && defaultValue != null) {
			throw new CommandLineException(
					"The meta info could not been created, because it's marked as obligatory, but nevertheless a default value was " +
							"passed, too. No default value should be set on obligatory arguments.");
		}
		this.longName = longName;
		this.shortName = shortName;
		this.valueType = valueType;
		this.validator = validatorClass;
		this.parser = parserClass;
		this.obligatory = obligatory;
		this.defaultValue = defaultValue;
		this.description = description;
		this.examples = examples;
	}

	private static Class<? extends ArgumentParser<?>> getCompatibleParser(CommandArgument metaInfo, Class<?> valueType) {
		Class<? extends ArgumentParser<?>> parser;
		parser = metaInfo.parser();

		if (parser.equals(MockArgumentParser.class)) {
			parser = findCompatibleParser(valueType);
			if (parser == null) {
				throw new CommandLineException(String.format("The meta information could not been created, " +
						"because no compatible parser is defined for the argument \"%s\" and no default compatible parser for " +
						"the type \"%s\" is available.", metaInfo.longName(), valueType.getSimpleName()));
			}
		}

		return parser;
	}

	@NotNull
	public String getLongName() {
		return this.longName;
	}

	@NotNull
	public String getShortName() {
		return this.shortName;
	}

	@NotNull
	public Class<?> getValueType() {
		return this.valueType;
	}

	@NotNull
	public Class<? extends ArgumentParser<?>> getParser() {
		return this.parser;
	}

	@NotNull
	public Class<? extends ArgumentValidator<?>> getValidator() {
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
	public int compareTo(@NotNull ArgumentMetaInfo info) {
		String longNameA;
		String longNameB;

		longNameA = getLongName();
		longNameB = info.getLongName();

		return longNameA.compareTo(longNameB);
	}

	public static ArgumentParser<?> createParser(@NotNull Class<? extends ArgumentParser<?>> parserClass) {
		ArgumentParser<?> parser;

		try {
			parser = parserClass.newInstance();
		} catch (@NotNull IllegalAccessException | InstantiationException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		return parser;
	}

	public static ArgumentValidator<?> createValidator(@NotNull Class<? extends ArgumentValidator<?>> validatorClass) {
		ArgumentValidator<?> validator;

		try {
			validator = validatorClass.newInstance();
		} catch (@NotNull IllegalAccessException | InstantiationException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		return validator;
	}

	@Override
	@SuppressWarnings("AccessingNonPublicFieldOfAnotherObject")
	public int hashCode() {
		int result = this.shortName.hashCode();
		result = 31 * result + this.longName.hashCode();
		result = 31 * result + this.valueType.hashCode();
		result = 31 * result + this.parser.hashCode();
		result = 31 * result + this.validator.hashCode();
		result = 31 * result + (this.obligatory ? 1 : 0);
		result = 31 * result + (this.defaultValue != null ? this.defaultValue.hashCode() : 0);
		result = 31 * result + this.description.hashCode();
		result = 31 * result + Arrays.hashCode(this.examples);
		return result;
	}

	@SuppressWarnings("AccessingNonPublicFieldOfAnotherObject")
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		ArgumentMetaInfo that = (ArgumentMetaInfo) o;

		if (this.obligatory != that.obligatory) {
			return false;
		}
		if (this.defaultValue != null ? ! this.defaultValue.equals(that.defaultValue) : that.defaultValue != null) {
			return false;
		}
		if (! this.description.equals(that.description)) {
			return false;
		}
		if (! Arrays.equals(this.examples, that.examples)) {
			return false;
		}
		if (! this.longName.equals(that.longName)) {
			return false;
		}
		if (! this.parser.equals(that.parser)) {
			return false;
		}
		if (! this.shortName.equals(that.shortName)) {
			return false;
		}
		if (! this.validator.equals(that.validator)) {
			return false;
		}
		if (! this.valueType.equals(that.valueType)) {
			return false;
		}

		return true;
	}

	@Nullable
	static Class<? extends ArgumentParser<?>> findCompatibleParser(@NotNull Class<?> typeToParse) {
		Class<? extends ArgumentParser<?>> parserClass;

		if (typeToParse == null) {
			throw new ArgumentNullException();
		}

		parserClass = null;
		if (typeToParse.equals(String.class)) {
			parserClass = StringArgumentParser.class;
		} else if (typeToParse.equals(Short.class) || typeToParse.equals(short.class)) {
			parserClass = ShortArgumentParser.class;
		} else if (typeToParse.equals(Integer.class) || typeToParse.equals(int.class)) {
			parserClass = IntegerArgumentParser.class;
		} else if (typeToParse.equals(Long.class) || typeToParse.equals(long.class)) {
			parserClass = LongArgumentParser.class;
		} else if (typeToParse.equals(Character.class) || typeToParse.equals(char.class)) {
			parserClass = CharacterArgumentParser.class;
		} else if (typeToParse.equals(Boolean.class) || typeToParse.equals(boolean.class)) {
			parserClass = BooleanArgumentParser.class;
		} else if (typeToParse.equals(Float.class) || typeToParse.equals(float.class)) {
			parserClass = FloatArgumentParser.class;
		} else if (typeToParse.equals(Double.class) || typeToParse.equals(double.class)) {
			parserClass = DoubleArgumentParser.class;
		} else if (typeToParse.equals(File.class)) {
			parserClass = FileArgumentParser.class;
		} else if (typeToParse.equals(URL.class)) {
			parserClass = UrlArgumentParser.class;
		}

		return parserClass;
	}

	@Nullable
	static String getDefaultValue(@NotNull CommandArgument annotation) {
		String defaultValue;
		boolean defaultValueNull;
		String value;

		if (annotation == null) {
			throw new ArgumentNullException();
		}
		defaultValue = annotation.defaultValue();
		if (defaultValue.equals(CommandArgument.nullValue)) {
			defaultValue = null;
		}
		defaultValueNull = annotation.defaultToNull();
		if (defaultValue != null && ! defaultValue.isEmpty() && defaultValueNull) {
			throw new CommandLineException("The default value could not been retrieved, because the passed annotation " +
					"contains a default value and is set to use null as default value at the same time.");
		}
		if (defaultValueNull) {
			value = null;
		} else {
			value = defaultValue;
		}

		return value;
	}
}
