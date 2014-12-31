package commandline.argument.metainfo;

import commandline.argument.metainfo.testcommands.InvalidDefaultValueTestCommand;
import commandline.argument.metainfo.testcommands.ValidTestCommand;
import commandline.argument.validator.IntegerArgumentValidator;
import commandline.argument.validator.StringArgumentValidator;
import commandline.command.CommandLineException;
import commandline.command.MockCommand;
import commandline.language.parser.argument.ArgumentParser;
import commandline.language.parser.argument.BooleanArgumentParser;
import commandline.language.parser.argument.CharacterArgumentParser;
import commandline.language.parser.argument.DoubleArgumentParser;
import commandline.language.parser.argument.FileArgumentParser;
import commandline.language.parser.argument.FloatArgumentParser;
import commandline.language.parser.argument.IntegerArgumentParser;
import commandline.language.parser.argument.LongArgumentParser;
import commandline.language.parser.argument.MockArgumentParser;
import commandline.language.parser.argument.ShortArgumentParser;
import commandline.language.parser.argument.StringArgumentParser;
import commandline.exception.ArgumentNullException;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Method;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * User: gno, Date: 05.07.13 - 11:04
 */
@SuppressWarnings({"ResultOfObjectAllocationIgnored", "OverlyCoupledClass"})
public class ArgumentMetaInfoTest {
	public ArgumentMetaInfoTest() {
		super();
	}

	@Test
	public void testArgumentMetaInfo() {
		new ArgumentMetaInfo(MockCommand.LONG_NAME, MockCommand.SHORT_NAME, MockCommand.TYPE, MockCommand.PARSER,
				MockCommand.VALIDATOR, MockCommand.OBLIGATORY, MockCommand.DEFAULT_VALUE, MockCommand.DESCRIPTION,
				MockCommand.EXAMPLES);
	}

	@Test(expected = CommandLineException.class)
	public void testArgumentMetaInfo_EmptyLongName() {
		new ArgumentMetaInfo("", MockCommand.SHORT_NAME, MockCommand.TYPE, MockCommand.PARSER, MockCommand.VALIDATOR,
				MockCommand.OBLIGATORY, MockCommand.DEFAULT_VALUE, MockCommand.DESCRIPTION, MockCommand.EXAMPLES);
	}

	@Test(expected = CommandLineException.class)
	public void testArgumentMetaInfo_EmptyShortName() {
		new ArgumentMetaInfo(MockCommand.LONG_NAME, "", MockCommand.TYPE, MockCommand.PARSER, MockCommand.VALIDATOR,
				MockCommand.OBLIGATORY, MockCommand.DEFAULT_VALUE, MockCommand.DESCRIPTION, MockCommand.EXAMPLES);
	}

	@Test(expected = CommandLineException.class)
	public void testArgumentMetaInfo_ShortNameTooLong() {
		new ArgumentMetaInfo(MockCommand.LONG_NAME, "ab", MockCommand.TYPE, MockCommand.PARSER, MockCommand.VALIDATOR,
				MockCommand.OBLIGATORY, MockCommand.DEFAULT_VALUE, MockCommand.DESCRIPTION, MockCommand.EXAMPLES);
	}

	@Test(expected = CommandLineException.class)
	public void testArgumentMetaInfo_TypeNotCompatibleWithParser() {
		new ArgumentMetaInfo(MockCommand.LONG_NAME, MockCommand.SHORT_NAME, String.class, IntegerArgumentParser.class,
				StringArgumentValidator.class, MockCommand.OBLIGATORY, MockCommand.DEFAULT_VALUE, MockCommand.DESCRIPTION,
				MockCommand.EXAMPLES);
	}

	@Test(expected = CommandLineException.class)
	public void testArgumentMetaInfo_TypeNotCompatibleWithValidator() {
		new ArgumentMetaInfo(MockCommand.LONG_NAME, MockCommand.SHORT_NAME, String.class, StringArgumentParser.class,
				IntegerArgumentValidator.class, MockCommand.OBLIGATORY, MockCommand.DEFAULT_VALUE, MockCommand.DESCRIPTION,
				MockCommand.EXAMPLES);
	}

	@Test(expected = CommandLineException.class)
	public void testArgumentMetaInfo_ObligatoryWithDefaultValue() {
		new ArgumentMetaInfo(MockCommand.LONG_NAME, MockCommand.SHORT_NAME, MockCommand.TYPE, MockCommand.PARSER,
				MockCommand.VALIDATOR, true, "default value", MockCommand.DESCRIPTION, MockCommand.EXAMPLES);
	}

	@Test
	public void testArgumentMetaInfo_NotObligatoryNoDefaultValue() {
		new ArgumentMetaInfo(MockCommand.LONG_NAME, MockCommand.SHORT_NAME, MockCommand.TYPE, MockCommand.PARSER,
				MockCommand.VALIDATOR, false, null, MockCommand.DESCRIPTION, MockCommand.EXAMPLES);
	}

	@Test(expected = CommandLineException.class)
	public void testArgumentMetaInfo_EmptyDescription() {
		new ArgumentMetaInfo(MockCommand.LONG_NAME, MockCommand.SHORT_NAME, MockCommand.TYPE, MockCommand.PARSER,
				MockCommand.VALIDATOR, MockCommand.OBLIGATORY, MockCommand.DEFAULT_VALUE, "", MockCommand.EXAMPLES);
	}

	@Test(expected = CommandLineException.class)
	public void testArgumentMetaInfo_NoExamples() {
		new ArgumentMetaInfo(MockCommand.LONG_NAME, MockCommand.SHORT_NAME, MockCommand.TYPE, MockCommand.PARSER,
				MockCommand.VALIDATOR, MockCommand.OBLIGATORY, MockCommand.DEFAULT_VALUE, MockCommand.DESCRIPTION, new String[0]);
	}

	@Test(expected = CommandLineException.class)
	public void testArgumentMetaInfo_EmptyExamples() {
		new ArgumentMetaInfo(MockCommand.LONG_NAME, MockCommand.SHORT_NAME, MockCommand.TYPE, MockCommand.PARSER,
				MockCommand.VALIDATOR, MockCommand.OBLIGATORY, MockCommand.DEFAULT_VALUE, MockCommand.DESCRIPTION, new String[] {""});
	}

	@Test(expected = CommandLineException.class)
	public void testArgumentMetaInfo_NullExamples() {
		new ArgumentMetaInfo(MockCommand.LONG_NAME, MockCommand.SHORT_NAME, MockCommand.TYPE, MockCommand.PARSER,
				MockCommand.VALIDATOR, MockCommand.OBLIGATORY, MockCommand.DEFAULT_VALUE, MockCommand.DESCRIPTION,
				new String[] {null});
	}

	@Test
	public void testArgumentMetaInfoAnnotation_ObligatoryWithParserWithValidator() throws Exception {
		Method method;

		method = ValidTestCommand.class.getMethod("obligatoryWithParserWithValidator", String.class);
		testArgumentMetaInfoAnnotation(method);
	}

	@Test
	public void testArgumentMetaInfoAnnotation_ObligatoryWithParserNoValidator() throws Exception {
		Method method;

		method = ValidTestCommand.class.getMethod("obligatoryWithParserNoValidator", String.class);
		testArgumentMetaInfoAnnotation(method);
	}

	@Test
	public void testArgumentMetaInfoAnnotation_ObligatoryNoParserWithValidator() throws Exception {
		Method method;

		method = ValidTestCommand.class.getMethod("obligatoryNoParserWithValidator", String.class);
		testArgumentMetaInfoAnnotation(method);
	}

	@Test
	public void testArgumentMetaInfoAnnotation_OptionalNonNullDefaultValue() throws Exception {
		Method method;

		method = ValidTestCommand.class.getMethod("optionalNonNullDefaultValue", String.class);
		testArgumentMetaInfoAnnotation(method);
	}

	@Test
	public void testArgumentMetaInfoAnnotation_OptionalNullDefaultValue() throws Exception {
		Method method;

		method = ValidTestCommand.class.getMethod("optionalNullDefaultValue", String.class);
		testArgumentMetaInfoAnnotation(method);
	}

	public void testArgumentMetaInfoAnnotation(Method testMethod) {
		CommandArgument annotation;
		ArgumentMetaInfo info;

		if (testMethod == null) {
			throw new ArgumentNullException();
		}
		assertNotNull(testMethod);
		annotation = testMethod.getAnnotation(CommandArgument.class);
		assertNotNull(annotation);
		info = new ArgumentMetaInfo(annotation, testMethod.getParameterTypes()[0]);
		compareAnnotationWithMetaInfo(annotation, info);
	}

	@Test
	public void testFindCompatibleParser_StringValue() throws Exception {
		testFindCompatibleParser(String.class, StringArgumentParser.class);
	}

	@Test
	public void testFindCompatibleParser_ShortValue() throws Exception {
		testFindCompatibleParser(Short.class, ShortArgumentParser.class);
	}

	@Test
	public void testFindCompatibleParser_ShortPrimitiveValue() throws Exception {
		testFindCompatibleParser(short.class, ShortArgumentParser.class);
	}

	@Test
	public void testFindCompatibleParser_IntegerValue() throws Exception {
		testFindCompatibleParser(Integer.class, IntegerArgumentParser.class);
	}

	@Test
	public void testFindCompatibleParser_IntegerPrimitiveValue() throws Exception {
		testFindCompatibleParser(int.class, IntegerArgumentParser.class);
	}

	@Test
	public void testFindCompatibleParser_LongValue() throws Exception {
		testFindCompatibleParser(Long.class, LongArgumentParser.class);
	}

	@Test
	public void testFindCompatibleParser_LongPrimitiveValue() throws Exception {
		testFindCompatibleParser(long.class, LongArgumentParser.class);
	}

	@Test
	public void testFindCompatibleParser_CharacterValue() throws Exception {
		testFindCompatibleParser(Character.class, CharacterArgumentParser.class);
	}

	@Test
	public void testFindCompatibleParser_CharacterPrimitiveValue() throws Exception {
		testFindCompatibleParser(char.class, CharacterArgumentParser.class);
	}

	@Test
	public void testFindCompatibleParser_BooleanValue() throws Exception {
		testFindCompatibleParser(Boolean.class, BooleanArgumentParser.class);
	}

	@Test
	public void testFindCompatibleParser_BooleanPrimitiveValue() throws Exception {
		testFindCompatibleParser(boolean.class, BooleanArgumentParser.class);
	}

	@Test
	public void testFindCompatibleParser_FloatValue() throws Exception {
		testFindCompatibleParser(Float.class, FloatArgumentParser.class);
	}

	@Test
	public void testFindCompatibleParser_FloatPrimitiveValue() throws Exception {
		testFindCompatibleParser(float.class, FloatArgumentParser.class);
	}

	@Test
	public void testFindCompatibleParser_DoublePrimitiveValue() throws Exception {
		testFindCompatibleParser(double.class, DoubleArgumentParser.class);
	}

	@Test
	public void testFindCompatibleParser_FileValue() throws Exception {
		testFindCompatibleParser(File.class, FileArgumentParser.class);
	}

	public void testFindCompatibleParser(Class<?> typeToParse, Class<? extends ArgumentParser<?>> expectedParser) {
		if (typeToParse == null) {
			throw new ArgumentNullException();
		}
		if (expectedParser == null) {
			throw new ArgumentNullException();
		}
		assertEquals(expectedParser, ArgumentMetaInfo.findCompatibleParser(typeToParse));
	}

	@Test
	public void testGetDefaultValue_OptionalNonNullDefaultValue() throws Exception {
		Method method;

		method = ValidTestCommand.class.getMethod("optionalNonNullDefaultValue", String.class);
		testGetDefaultValue(method, "test-value");
	}

	@Test
	public void testGetDefaultValue_OptionalNullDefaultValue() throws Exception {
		Method method;

		method = ValidTestCommand.class.getMethod("optionalNullDefaultValue", String.class);
		testGetDefaultValue(method, null);
	}

	@Test(expected = CommandLineException.class)
	public void testGetDefaultValue_InvalidDefaultValueFields() throws Exception {
		Method method;

		method = InvalidDefaultValueTestCommand.class.getMethod("method", String.class);
		testGetDefaultValue(method, null);
	}

	public void testGetDefaultValue(Method method, @Nullable String expectedDefaultValue) throws Exception {
		CommandArgument metaInfo;
		String defaultValue;

		if (method == null) {
			throw new ArgumentNullException();
		}
		metaInfo = method.getAnnotation(CommandArgument.class);
		defaultValue = ArgumentMetaInfo.getDefaultValue(metaInfo);
		assertEquals(expectedDefaultValue, defaultValue);
	}

	public static void compareAnnotationWithMetaInfo(CommandArgument annotation, ArgumentMetaInfo metaInfo) {
		if (annotation == null) {
			throw new ArgumentNullException();
		}
		if (metaInfo == null) {
			throw new ArgumentNullException();
		}

		assertEquals(annotation.shortName(), metaInfo.getShortName());
		assertEquals(annotation.longName(), metaInfo.getLongName());
		assertEquals(annotation.obligatory(), metaInfo.isObligatory());
		if (!annotation.obligatory()) {
			if (annotation.defaultToNull()) {
				assertNull(metaInfo.getDefaultValue());
			} else {
				assertEquals(annotation.defaultValue(), metaInfo.getDefaultValue());
			}
		}
		if (annotation.parser() != MockArgumentParser.class) {
			assertEquals(annotation.parser(), metaInfo.getParser());
		} else {
			assertNotNull(metaInfo.getParser());
		}
		assertEquals(annotation.validator(), metaInfo.getValidator());
		assertEquals(annotation.description(), metaInfo.getDescription());
		assertArrayEquals(annotation.examples(), metaInfo.getExamples());
	}
}