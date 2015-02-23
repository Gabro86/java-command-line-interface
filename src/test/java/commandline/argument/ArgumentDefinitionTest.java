package commandline.argument;

import commandline.annotation.CliArgument;
import commandline.annotation.MockCliArgument;
import commandline.argument.validator.DefaultArgumentValidator;
import commandline.command.CommandLineException;
import commandline.command.MockExecutableCommand;
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
import org.junit.Test;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * User: gno, Date: 05.07.13 - 11:04
 */
public class ArgumentDefinitionTest {
	public ArgumentDefinitionTest() {
		super();
	}

	@Test
	public void testArgumentDefinition() {
		new ArgumentDefinition(MockExecutableCommand.ARGUMENT_TEST_LONG_NAME, MockExecutableCommand.ARGUMENT_TEST_SHORT_NAME,
				MockExecutableCommand.ARGUMENT_TEST_VALUE_CLASS, MockExecutableCommand.ARGUMENT_TEST_PARSER,
				MockExecutableCommand.ARGUMENT_TEST_VALIDATOR, MockExecutableCommand.ARGUMENT_TEST_OBLIGATORY,
				MockExecutableCommand.ARGUMENT_TEST_DEFAULT_VALUE, MockExecutableCommand.ARGUMENT_TEST_DESCRIPTION,
				MockExecutableCommand.ARGUMENT_TEST_EXAMPLES);
	}

	@Test
	public void testArgumentDefinition_AutoFindCompatibleParser() {
		ArgumentDefinition definition;

		definition = new ArgumentDefinition(MockExecutableCommand.ARGUMENT_TEST_LONG_NAME,
				MockExecutableCommand.ARGUMENT_TEST_SHORT_NAME, String.class, new StringArgumentParser(),
				new DefaultArgumentValidator(), MockExecutableCommand.ARGUMENT_TEST_OBLIGATORY,
				MockExecutableCommand.ARGUMENT_TEST_DEFAULT_VALUE, MockExecutableCommand.ARGUMENT_TEST_DESCRIPTION,
				MockExecutableCommand.ARGUMENT_TEST_EXAMPLES);
		assertEquals(StringArgumentParser.class, definition.getParser().getClass());
	}

	@Test(expected = CommandLineException.class)
	public void testArgumentDefinition_ObligatoryArgument_NonNullDefaultValue() {
		new ArgumentDefinition(MockExecutableCommand.ARGUMENT_TEST_LONG_NAME, MockExecutableCommand.ARGUMENT_TEST_SHORT_NAME,
				MockExecutableCommand.ARGUMENT_TEST_VALUE_CLASS, MockExecutableCommand.ARGUMENT_TEST_PARSER,
				MockExecutableCommand.ARGUMENT_TEST_VALIDATOR, true, "default value", MockExecutableCommand.ARGUMENT_TEST_DESCRIPTION,
				MockExecutableCommand.ARGUMENT_TEST_EXAMPLES);
	}

	@Test
	public void testArgumentDefinition_NonObligatoryArgument_NullDefaultValue() {
		new ArgumentDefinition(MockExecutableCommand.ARGUMENT_TEST_LONG_NAME, MockExecutableCommand.ARGUMENT_TEST_SHORT_NAME,
				MockExecutableCommand.ARGUMENT_TEST_VALUE_CLASS, MockExecutableCommand.ARGUMENT_TEST_PARSER,
				MockExecutableCommand.ARGUMENT_TEST_VALIDATOR, false, null, MockExecutableCommand.ARGUMENT_TEST_DESCRIPTION,
				MockExecutableCommand.ARGUMENT_TEST_EXAMPLES);
	}

	@Test
	public void testArgumentDefinitionAnnotation() throws Exception {
		MockCliArgument annotation;
		ArgumentDefinition definition;

		annotation = new MockCliArgument();
		annotation.setShortName("t");
		annotation.setLongName("test-name");
		annotation.setDefaultValue("test-value");
		annotation.setDefaultValueNull(false);
		annotation.setObligatory(false);
		annotation.setDescription("This is an example description");
		annotation.setExamples(new String[] {"This is an example."});
		annotation.setParser(StringArgumentParser.class);
		annotation.setValidator(DefaultArgumentValidator.class);
		definition = new ArgumentDefinition(annotation, String.class);
		compareAnnotationWithDefinition(annotation, definition);
	}

	@Test
	public void testProcessShortName() {
		assertEquals("a", ArgumentDefinition.processShortName("a"));
	}

	@Test(expected = CommandLineException.class)
	public void testProcessShortName_EmptyShortName() {
		ArgumentDefinition.processShortName("");
	}

	@Test(expected = CommandLineException.class)
	public void testProcessShortName_EmptyShortNameWithSpaces() {
		ArgumentDefinition.processShortName(" ");
	}

	@Test(expected = CommandLineException.class)
	public void testProcessShortName_ShortNameTooLong() {
		ArgumentDefinition.processShortName("ThisShortNameIsTooLong");
	}

	@Test
	public void testCreateCompatibleParser_MockParser() {
		ArgumentParser<?> parser;

		parser = ArgumentDefinition.createCompatibleParser(MockArgumentParser.class, String.class);
		assertEquals(StringArgumentParser.class, parser.getClass());
	}

	@Test(expected = CommandLineException.class)
	public void testCreateCompatibleParser_NoParserForValueClass() {
		ArgumentDefinition.createCompatibleParser(MockArgumentParser.class, Object.class);
	}

	@Test
	public void testCreateValidator() {
		ArgumentDefinition.createValidator(DefaultArgumentValidator.class);
	}

	@Test
	public void testProcessExamples() {
		String[] examplesBefore;
		String[] examplesAfter;

		examplesBefore = new String[] {"example"};
		examplesAfter = ArgumentDefinition.processExamples(examplesBefore);
		assertArrayEquals(examplesBefore, examplesAfter);
	}

	@Test(expected = CommandLineException.class)
	public void testProcessExamples_NoExamples() {
		ArgumentDefinition.processExamples(new String[0]);
	}

	@Test(expected = CommandLineException.class)
	public void testProcessExamples_EmptyExamples() {
		ArgumentDefinition.processExamples(new String[] {""});
	}

	@Test(expected = CommandLineException.class)
	public void testProcessExamples_EmptyExamplesWithSpaces() {
		ArgumentDefinition.processExamples(new String[] {" "});
	}

	@Test(expected = CommandLineException.class)
	public void testProcessExamples_NullExamples() {
		ArgumentDefinition.processExamples(new String[] {null});
	}

	@Test
	public void testFindCompatibleParser_StringValue() throws Exception {
		assertEquals(StringArgumentParser.class, ArgumentDefinition.findCompatibleParser(String.class));
	}

	@Test
	public void testFindCompatibleParser_ShortValue() throws Exception {
		assertEquals(ShortArgumentParser.class, ArgumentDefinition.findCompatibleParser(Short.class));
	}

	@Test
	public void testFindCompatibleParser_ShortPrimitiveValue() throws Exception {
		assertEquals(ShortArgumentParser.class, ArgumentDefinition.findCompatibleParser(short.class));
	}

	@Test
	public void testFindCompatibleParser_IntegerValue() throws Exception {
		assertEquals(IntegerArgumentParser.class, ArgumentDefinition.findCompatibleParser(Integer.class));
	}

	@Test
	public void testFindCompatibleParser_IntegerPrimitiveValue() throws Exception {
		assertEquals(IntegerArgumentParser.class, ArgumentDefinition.findCompatibleParser(int.class));
	}

	@Test
	public void testFindCompatibleParser_LongValue() throws Exception {
		assertEquals(LongArgumentParser.class, ArgumentDefinition.findCompatibleParser(Long.class));
	}

	@Test
	public void testFindCompatibleParser_LongPrimitiveValue() throws Exception {
		assertEquals(LongArgumentParser.class, ArgumentDefinition.findCompatibleParser(long.class));
	}

	@Test
	public void testFindCompatibleParser_CharacterValue() throws Exception {
		assertEquals(CharacterArgumentParser.class, ArgumentDefinition.findCompatibleParser(Character.class));
	}

	@Test
	public void testFindCompatibleParser_CharacterPrimitiveValue() throws Exception {
		assertEquals(CharacterArgumentParser.class, ArgumentDefinition.findCompatibleParser(char.class));
	}

	@Test
	public void testFindCompatibleParser_BooleanValue() throws Exception {
		assertEquals(BooleanArgumentParser.class, ArgumentDefinition.findCompatibleParser(Boolean.class));
	}

	@Test
	public void testFindCompatibleParser_BooleanPrimitiveValue() throws Exception {
		assertEquals(BooleanArgumentParser.class, ArgumentDefinition.findCompatibleParser(boolean.class));
	}

	@Test
	public void testFindCompatibleParser_FloatValue() throws Exception {
		assertEquals(FloatArgumentParser.class, ArgumentDefinition.findCompatibleParser(Float.class));
	}

	@Test
	public void testFindCompatibleParser_FloatPrimitiveValue() throws Exception {
		assertEquals(FloatArgumentParser.class, ArgumentDefinition.findCompatibleParser(float.class));
	}

	@Test
	public void testFindCompatibleParser_DoublePrimitiveValue() throws Exception {
		assertEquals(DoubleArgumentParser.class, ArgumentDefinition.findCompatibleParser(double.class));
	}

	@Test
	public void testFindCompatibleParser_FileValue() throws Exception {
		assertEquals(FileArgumentParser.class, ArgumentDefinition.findCompatibleParser(File.class));
	}

	@Test
	public void testFindCompatibleParser_UrlValue() throws Exception {
		assertEquals(UrlArgumentParser.class, ArgumentDefinition.findCompatibleParser(URL.class));
	}

	@Test
	public void testGetDefaultValue() throws Exception {
		MockCliArgument annotation;
		String defaultValueAfter;
		String defaultValueBefore;

		defaultValueBefore = "test-value";
		annotation = new MockCliArgument();
		annotation.setDefaultValue(defaultValueBefore);
		annotation.setDefaultValueNull(false);
		defaultValueAfter = ArgumentDefinition.getDefaultValueFromAnnotation(annotation);
		assertEquals(defaultValueBefore, defaultValueAfter);
	}

	@Test
	public void testGetDefaultValue_NonNullDefaultValue() throws Exception {
		MockCliArgument annotation;
		String defaultValueAfter;
		String defaultValueBefore;

		defaultValueBefore = "test-value";
		annotation = new MockCliArgument();
		annotation.setDefaultValue(defaultValueBefore);
		annotation.setDefaultValueNull(false);
		defaultValueAfter = ArgumentDefinition.getDefaultValueFromAnnotation(annotation);
		assertEquals(defaultValueBefore, defaultValueAfter);
	}

	@Test
	public void testGetDefaultValue_NullDefaultValue_DefaultToNull() throws Exception {
		MockCliArgument annotation;
		String defaultValueAfter;

		annotation = new MockCliArgument();
		annotation.setDefaultValue(null);
		annotation.setDefaultValueNull(true);
		defaultValueAfter = ArgumentDefinition.getDefaultValueFromAnnotation(annotation);
		assertEquals(null, defaultValueAfter);
	}

	@Test
	public void testGetDefaultValue_NullDefaultValue_DontDefaultToNull() throws Exception {
		MockCliArgument annotation;
		String defaultValueAfter;

		annotation = new MockCliArgument();
		annotation.setDefaultValue(null);
		annotation.setDefaultValueNull(false);
		defaultValueAfter = ArgumentDefinition.getDefaultValueFromAnnotation(annotation);
		assertEquals(null, defaultValueAfter);
	}

	@Test(expected = CommandLineException.class)
	public void testGetDefaultValue_NonNullDefaultValue_DefaultValueToNull() throws Exception {
		MockCliArgument annotation;
		String defaultValueAfter;

		annotation = new MockCliArgument();
		annotation.setDefaultValue("test-value");
		annotation.setDefaultValueNull(true);
		defaultValueAfter = ArgumentDefinition.getDefaultValueFromAnnotation(annotation);
		assertEquals(null, defaultValueAfter);
	}

	public static void compareAnnotationWithDefinition(CliArgument annotation, ArgumentDefinition definition) {
		if (annotation == null) {
			throw new ArgumentNullException();
		}
		if (definition == null) {
			throw new ArgumentNullException();
		}

		assertEquals(annotation.shortName(), definition.getShortName());
		assertEquals(annotation.longName(), definition.getLongName());
		assertEquals(annotation.obligatory(), definition.isObligatory());
		if (annotation.isDefaultValueNull() || annotation.defaultValue().equals(CliArgument.nullValue)) {
			assertNull(definition.getDefaultValue());
		} else {
			assertEquals(annotation.defaultValue(), definition.getDefaultValue());
		}
		if (annotation.parser() == MockArgumentParser.class) {
			assertNotNull(definition.getParser());
		} else {
			assertEquals(annotation.parser(), definition.getParser().getClass());
		}
		assertEquals(annotation.validator(), definition.getValidator().getClass());
		assertEquals(annotation.description(), definition.getDescription());
		assertArrayEquals(annotation.examples(), definition.getExamples());
	}
}