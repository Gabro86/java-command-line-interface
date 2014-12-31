package commandline.argument.metainfo;

import commandline.argument.metainfo.testcommands.DoubleLongCommandArgumentTestCommand;
import commandline.argument.metainfo.testcommands.DoubleShortCommandArgumentTestCommand;
import commandline.argument.metainfo.testcommands.NoParameterTestCommand;
import commandline.argument.metainfo.testcommands.TooManyParametersTestCommand;
import commandline.argument.metainfo.testcommands.ValidTestCommand;
import commandline.command.CommandLineException;
import commandline.exception.ArgumentNullException;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

/**
 * User: gno, Date: 09.07.13 - 11:22
 */
public class ArgumentMetaInfoExtractorTest {
	public ArgumentMetaInfoExtractorTest() {
		super();
	}

	@Test
	public void testExtractClass() throws Exception {
		ArgumentMetaInfoExtractor extractor;
		List<ArgumentMetaInfo> infos;
		Annotation[] annotations;
		CommandArgument castedAnnotation;

		annotations = ValidTestCommand.class.getAnnotations();
		extractor = new ArgumentMetaInfoExtractor();
		infos = extractor.extract(ValidTestCommand.class);
		for (ArgumentMetaInfo metaInfo : infos) {
			for (Annotation annotation : annotations) {
				castedAnnotation = (CommandArgument) annotation;
				if (castedAnnotation.longName().equals(metaInfo.getLongName())) {
					ArgumentMetaInfoTest.compareAnnotationWithMetaInfo(castedAnnotation, metaInfo);
				}
			}
		}
	}

	@Test(expected = CommandLineException.class)
	public void testExtractClass_MethodWithoutParameters() throws Exception {
		ArgumentMetaInfoExtractor extractor;

		extractor = new ArgumentMetaInfoExtractor();
		extractor.extract(NoParameterTestCommand.class);
	}

	@Test(expected = CommandLineException.class)
	public void testExtractClass_MethodWithTooManyParameters() throws Exception {
		ArgumentMetaInfoExtractor extractor;

		extractor = new ArgumentMetaInfoExtractor();
		extractor.extract(TooManyParametersTestCommand.class);
	}

	@Test(expected = CommandLineException.class)
	public void testExtractClass_DoubleShortCommandArgumentTestCommand() throws Exception {
		ArgumentMetaInfoExtractor extractor;

		extractor = new ArgumentMetaInfoExtractor();
		extractor.extract(DoubleShortCommandArgumentTestCommand.class);
	}

	@Test(expected = CommandLineException.class)
	public void testExtractClass_DoubleLongCommandArgumentTestCommand() throws Exception {
		ArgumentMetaInfoExtractor extractor;

		extractor = new ArgumentMetaInfoExtractor();
		extractor.extract(DoubleLongCommandArgumentTestCommand.class);
	}

	public void testExtractMethod_ObligatoryWithParserWithValidator() throws Exception {
		Method method;

		method = ValidTestCommand.class.getMethod("obligatoryWithParserWithValidator", String.class);
		testExtractMethod(method);
	}

	@Test
	public void testExtractMethod_ObligatoryWithParserNoValidator() throws Exception {
		Method method;

		method = ValidTestCommand.class.getMethod("obligatoryWithParserNoValidator", String.class);
		testExtractMethod(method);
	}

	@Test
	public void testExtractMethod_ObligatoryNoParserWithValidator() throws Exception {
		Method method;

		method = ValidTestCommand.class.getMethod("obligatoryNoParserWithValidator", String.class);
		testExtractMethod(method);
	}

	@Test
	public void testExtractMethod_OptionalNonNullDefaultValue() throws Exception {
		Method method;

		method = ValidTestCommand.class.getMethod("optionalNonNullDefaultValue", String.class);
		testExtractMethod(method);
	}

	@Test
	public void testExtractMethod_OptionalNullDefaultValue() throws Exception {
		Method method;

		method = ValidTestCommand.class.getMethod("optionalNullDefaultValue", String.class);
		testExtractMethod(method);
	}

	@Test(expected = CommandLineException.class)
	public void testExtractMethod_MethodWithMultipleParameter() throws Exception {
		ArgumentMetaInfoExtractor extractor;
		Method method;

		method = NoParameterTestCommand.class.getMethod("method");
		extractor = new ArgumentMetaInfoExtractor();
		extractor.extract(method);
	}

	@Test(expected = CommandLineException.class)
	public void testExtractMethod_MethodWithoutParameter() throws Exception {
		ArgumentMetaInfoExtractor extractor;
		Method method;

		method = TooManyParametersTestCommand.class.getMethod("method", String.class, String.class);
		extractor = new ArgumentMetaInfoExtractor();
		extractor.extract(method);
	}

	public void testExtractMethod(Method method) throws Exception {
		CommandArgument annotation;
		ArgumentMetaInfo metaInfo;
		ArgumentMetaInfoExtractor extractor;

		if (method == null) {
			throw new ArgumentNullException();
		}
		annotation = method.getAnnotation(CommandArgument.class);
		extractor = new ArgumentMetaInfoExtractor();
		metaInfo = extractor.extract(method);
		ArgumentMetaInfoTest.compareAnnotationWithMetaInfo(annotation, metaInfo);
	}
}
