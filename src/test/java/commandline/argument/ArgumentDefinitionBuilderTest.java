package commandline.argument;

import commandline.argument.validator.ArgumentValidator;
import commandline.argument.validator.DefaultArgumentValidator;
import commandline.language.parser.ArgumentParser;
import commandline.language.parser.specific.StringArgumentParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ArgumentDefinitionBuilderTest {
	@Test
	public void testConstructor() {
		ArgumentDefinitionBuilder builder;
		String shortName;
		String longName;
		Class<?> valueClass;
		Class<? extends ArgumentParser<?>> parserClass;
		Class<? extends ArgumentValidator<?>> validatorClass;
		boolean obligatory;
		String defaultValue;
		String description;
		String[] examples;
		ArgumentDefinition expectedArgument;
		ArgumentDefinition builderArgument;

		shortName = "a";
		longName = "mock-argument";
		valueClass = String.class;
		parserClass = StringArgumentParser.class;
		validatorClass = DefaultArgumentValidator.class;
		obligatory = true;
		defaultValue = null;
		description = "This is an example description.";
		examples = new String[] {"example1", "example2"};

		expectedArgument = new ArgumentDefinition(longName, shortName, valueClass, parserClass, validatorClass, obligatory,
				defaultValue, description, examples);

		builder = new ArgumentDefinitionBuilder();
		builder.setShortName(shortName);
		builder.setLongName(longName);
		builder.setValueClass(valueClass);
		builder.setParserClass(parserClass);
		builder.setValidatorClass(validatorClass);
		builder.setObligatory(obligatory);
		builder.setDefaultValue(defaultValue);
		builder.setDescription(description);
		builder.setExamples(examples);
		builderArgument = builder.create();

		assertEquals(expectedArgument, builderArgument);
	}
}