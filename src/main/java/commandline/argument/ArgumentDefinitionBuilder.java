package commandline.argument;

import commandline.argument.validator.ArgumentValidator;
import commandline.argument.validator.DefaultArgumentValidator;
import commandline.language.parser.ArgumentParser;
import commandline.language.parser.specific.StringArgumentParser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * User: gno, Date: 26.01.2015 - 16:29
 */
public class ArgumentDefinitionBuilder {
	@Nullable
	private String shortName;
	@NotNull
	private String longName;
	@NotNull
	private Class<?> valueClass;
	@NotNull
	private Class<? extends ArgumentParser<?>> parserClass;
	@NotNull
	private Class<? extends ArgumentValidator<?>> validatorClass;
	private boolean obligatory;
	@Nullable
	private String defaultValue;
	@NotNull
	private String description;
	@NotNull
	private String[] examples;

	public ArgumentDefinitionBuilder() {
		super();
		setShortName(null);
		setLongName("mock-name");
		setValueClass(String.class);
		setParserClass(StringArgumentParser.class);
		setValidatorClass(DefaultArgumentValidator.class);
		setObligatory(true);
		setDefaultValue(null);
		setDescription("mock-description");
		setExamples(new String[0]);
	}

	@Nullable
	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(@Nullable String shortName) {
		this.shortName = shortName;
	}

	@NotNull
	public String getLongName() {
		return this.longName;
	}

	public void setLongName(@NotNull String longName) {
		this.longName = longName;
	}

	@NotNull
	public Class<?> getValueClass() {
		return this.valueClass;
	}

	public void setValueClass(@NotNull Class<?> valueClass) {
		this.valueClass = valueClass;
	}

	@NotNull
	public Class<? extends ArgumentParser<?>> getParserClass() {
		return this.parserClass;
	}

	public void setParserClass(@NotNull Class<? extends ArgumentParser<?>> parserClass) {
		this.parserClass = parserClass;
	}

	@NotNull
	public Class<? extends ArgumentValidator<?>> getValidatorClass() {
		return this.validatorClass;
	}

	public void setValidatorClass(@NotNull Class<? extends ArgumentValidator<?>> validatorClass) {
		this.validatorClass = validatorClass;
	}

	public boolean isObligatory() {
		return this.obligatory;
	}

	public void setObligatory(boolean obligatory) {
		this.obligatory = obligatory;
	}

	@Nullable
	public String getDefaultValue() {
		return this.defaultValue;
	}

	public void setDefaultValue(@Nullable String defaultValue) {
		this.defaultValue = defaultValue;
	}

	@NotNull
	public String getDescription() {
		return this.description;
	}

	public void setDescription(@NotNull String description) {
		this.description = description;
	}

	@NotNull
	public String[] getExamples() {
		return this.examples;
	}

	public void setExamples(@NotNull String[] examples) {
		this.examples = examples;
	}

	@NotNull
	public ArgumentDefinition create() {
		return new ArgumentDefinition(getLongName(), getShortName(), getValueClass(), getParserClass(), getValidatorClass(),
				isObligatory(), getDefaultValue(), getDescription(), getExamples());
	}
}
