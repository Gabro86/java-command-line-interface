package commandline.annotation;

import commandline.argument.validator.ArgumentValidator;
import commandline.argument.validator.DefaultArgumentValidator;
import commandline.exception.ArgumentNullException;
import commandline.language.parser.ArgumentParser;
import commandline.language.parser.MockArgumentParser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;

/**
 * User: gno, Date: 28.01.2015 - 16:16
 */
@SuppressWarnings("ClassExplicitlyAnnotation")
public class MockCliArgument implements CliArgument {
	private String shortName;
	private String longName;
	private boolean obligatory;
	private String defaultValue;
	private boolean isDefaultValueNull;
	private Class<? extends ArgumentParser<?>> parser;
	private Class<? extends ArgumentValidator<?>> validator;
	private String description;
	private String[] examples;

	public MockCliArgument() {
		super();
		setShortName("t");
		setLongName("test-name");
		setObligatory(false);
		setDefaultValue(null);
		setDefaultValueNull(false);
		setParser(MockArgumentParser.class);
		setValidator(DefaultArgumentValidator.class);
		setDescription("This is an example description.");
		setExamples(new String[] {"This is an test example."});
	}

	@NotNull
	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(@NotNull String shortName) {
		//Since this class represents an annotation null values are not allowed.
		if (shortName == null) {
			throw new ArgumentNullException();
		}
		this.shortName = shortName;
	}

	@NotNull
	public String getLongName() {
		return this.longName;
	}

	public void setLongName(@NotNull String longName) {
		//Since this class represents an annotation null values are not allowed.
		if (longName == null) {
			throw new ArgumentNullException();
		}
		this.longName = longName;
	}

	public boolean isObligatory() {
		return this.obligatory;
	}

	public void setObligatory(boolean obligatory) {
		this.obligatory = obligatory;
	}

	@NotNull
	public String getDefaultValue() {
		return this.defaultValue;
	}

	public void setDefaultValue(@Nullable String defaultValue) {
		//Since this class represents an annotation null values are not allowed. The null value is represented by a unique string.
		if (defaultValue == null) {
			this.defaultValue = CliArgument.nullValue;
		} else {
			this.defaultValue = defaultValue;
		}
	}

	public void setDefaultValueNull(boolean isDefaultValueNull) {
		this.isDefaultValueNull = isDefaultValueNull;
	}

	@NotNull
	public Class<? extends ArgumentParser<?>> getParser() {
		return this.parser;
	}

	public void setParser(@NotNull Class<? extends ArgumentParser<?>> parser) {
		//Since this class represents an annotation null values are not allowed.
		if (parser == null) {
			throw new ArgumentNullException();
		}
		this.parser = parser;
	}

	@NotNull
	public Class<? extends ArgumentValidator<?>> getValidator() {
		return this.validator;
	}

	public void setValidator(@NotNull Class<? extends ArgumentValidator<?>> validator) {
		//Since this class represents an annotation null values are not allowed.
		if (validator == null) {
			throw new ArgumentNullException();
		}
		this.validator = validator;
	}

	@NotNull
	public String getDescription() {
		return this.description;
	}

	public void setDescription(@NotNull String description) {
		//Since this class represents an annotation null values are not allowed.
		if (description == null) {
			throw new ArgumentNullException();
		}
		this.description = description;
	}

	@NotNull
	public String[] getExamples() {
		return this.examples;
	}

	public void setExamples(@NotNull String[] examples) {
		//Since this class represents an annotation null values are not allowed.
		if (examples == null) {
			throw new ArgumentNullException();
		}
		this.examples = examples;
	}

	@NotNull
	@Override
	public String shortName() {
		return this.shortName;
	}

	@NotNull
	@Override
	public String longName() {
		return this.longName;
	}

	@Override
	public boolean obligatory() {
		return this.obligatory;
	}

	@NotNull
	@Override
	public String defaultValue() {
		return this.defaultValue;
	}

	@Override
	public boolean isDefaultValueNull() {
		return this.isDefaultValueNull;
	}

	@NotNull
	@Override
	public Class<? extends ArgumentParser<?>> parser() {
		return this.parser;
	}

	@NotNull
	@Override
	public Class<? extends ArgumentValidator<?>> validator() {
		return this.validator;
	}

	@NotNull
	@Override
	public String description() {
		return this.description;
	}

	@NotNull
	@Override
	public String[] examples() {
		return this.examples;
	}

	@NotNull
	@Override
	public Class<? extends Annotation> annotationType() {
		return CliArgument.class;
	}
}
