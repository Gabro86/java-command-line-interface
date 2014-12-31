package commandline.argument;

import commandline.argument.metainfo.ArgumentMetaInfo;
import commandline.argument.validator.ArgumentValidator;
import commandline.command.CommandLineException;
import commandline.language.parser.argument.ArgumentParser;
import commandline.exception.ArgumentNullException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * User: gno Date: 21.06.13 Time: 15:12
 */
public class Argument<T> {
	@NotNull
	private final ArgumentMetaInfo metaInfo;
	@Nullable
	private final T value;

	@SuppressWarnings({"unchecked", "rawtypes"})
	public Argument(@NotNull ArgumentMetaInfo metaInfo, @Nullable T value) {
		super();

		Class<?> valueClass;
		Class<?> typeClass;
		ArgumentValidator validator;

		if (metaInfo == null) {
			throw new ArgumentNullException();
		}
		//value can be null. A null value means that the user did not pass the argument to the cli. There is no other way for the
		//user to pass a null as value for an argument.
		//Value is not directly the cli value that was passed by the user. Instead it's the value that was parsed from the value the
		//user passed.
		if (value != null) {
			valueClass = value.getClass();
			typeClass = metaInfo.getValueType();
			if (!typeClass.isAssignableFrom(valueClass)) {
				throw new CommandLineException("The validation of the argument \"" + metaInfo.getLongName() + "\" failed, " +
						"because the value with the type \"" + value.getClass().getSimpleName() + "\" is not an instance of the " +
						"type \"" + typeClass.getSimpleName() + "\" defined in the meta info.");
			}
		}
		//The value have to be validated with the passed validator, but since it's from the type defined in the passed
		//ArgumentMetaInfo it does not have to be parsed.
		validator = ArgumentMetaInfo.createValidator(metaInfo.getValidator());
		validator.validate(value);
		this.metaInfo = metaInfo;
		this.value = value;
	}

	@NotNull
	public ArgumentMetaInfo getMetaInfo() {
		return this.metaInfo;
	}

	@Nullable
	public Object getValue() {
		return this.value;
	}

	@NotNull
	public String getLongName() {
		return getMetaInfo().getLongName();
	}

	@NotNull
	public String getShortName() {
		return getMetaInfo().getShortName();
	}

	@NotNull
	public Class<?> getType() {
		return getMetaInfo().getValueType();
	}

	@NotNull
	public Class<? extends ArgumentParser<?>> getParser() {
		return getMetaInfo().getParser();
	}

	public boolean isObligatory() {
		return getMetaInfo().isObligatory();
	}

	@Nullable
	public String getDefaultValue() {
		return getMetaInfo().getDefaultValue();
	}

	@NotNull
	public String getDescription() {
		return getMetaInfo().getDescription();
	}

	@NotNull
	public String[] getExamples() {
		return getMetaInfo().getExamples();
	}

	@NotNull
	@SuppressWarnings("unchecked")
	public static <T> Argument<T> parse(@Nullable ArgumentMetaInfo metaInfo, String value) {
		ArgumentParser<?> parser;
		Argument<T> argument;
		Object parsedValue;
		String valueToParse;

		if (metaInfo == null) {
			throw new ArgumentNullException();
		}
		valueToParse = value;
		// A null value indicates that the user did not pass the argument to the cli, because there is no other way a user can pass a
		// null value to an argument. In this case the default value will be used if the argument is optional.
		if (valueToParse == null) {
			if (metaInfo.isObligatory()) {
				throw new CommandLineException("The parsing of the argument \"" + metaInfo.getLongName() + "\" failed, " +
						"because the passed obligatory value is null.");
			}
			valueToParse = metaInfo.getDefaultValue();
		}
		if (valueToParse == null) {
			parsedValue = null;
		} else {
			parser = ArgumentMetaInfo.createParser(metaInfo.getParser());
			parsedValue = parser.parse(valueToParse);
		}
		argument = (Argument<T>) new Argument<>(metaInfo, parsedValue);

		return argument;
	}
}
