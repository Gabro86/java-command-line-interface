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
	private ArgumentParser<?> parser;
	@NotNull
	private ArgumentValidator<?> validator;
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
		setParser(new StringArgumentParser());
		setValidator(new DefaultArgumentValidator());
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
	public ArgumentParser<?> getParser() {
		return this.parser;
	}

	public void setParser(@NotNull ArgumentParser<?> parser) {
		this.parser = parser;
	}

	@NotNull
	public ArgumentValidator<?> getValidator() {
		return this.validator;
	}

	public void setValidator(@NotNull ArgumentValidator<?> validator) {
		this.validator = validator;
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
		return new ArgumentDefinition(getLongName(), getShortName(), getValueClass(), getParser(), getValidator(), isObligatory(),
				getDefaultValue(), getDescription(), getExamples());
	}
}
