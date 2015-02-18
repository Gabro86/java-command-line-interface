package commandline.command;

import commandline.annotation.CliArgument;
import commandline.annotation.CommandLineAnnotationException;
import commandline.argument.ArgumentDefinition;
import commandline.argument.ArgumentDefinitionTest;
import commandline.command.mock.DuplicateLongNameTestCommand;
import commandline.command.mock.DuplicateShortNameTestCommand;
import commandline.command.mock.InheritedArgumentTestCommand;
import commandline.command.mock.NoCliCommandAnnotationTestCommand;
import commandline.command.mock.NoCommandNameTestCommand;
import commandline.command.mock.NoSetterParameterTestCommand;
import commandline.command.mock.OverriddenAnnotationsTestCommand;
import commandline.command.mock.TooManyMethodParametersTestCommand;
import commandline.command.mock.ValidTestCommand;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

/**
 * User: gno, Date: 09.07.13 - 11:22
 */
public class CommandDefinitionReaderTest {
	public CommandDefinitionReaderTest() {
		super();
	}

	@Test
	public void testReadCommandDefinition() {
		CommandDefinitionReader reader;
		CommandDefinition commandDefinition;
		Annotation[] annotations;
		CliArgument castedAnnotation;

		reader = new CommandDefinitionReader();
		commandDefinition = reader.readCommandDefinition(new ValidTestCommand());
		assertEquals(ValidTestCommand.COMMAND_NAME, commandDefinition.getName());
		assertEquals(ValidTestCommand.COMMAND_DESCRIPTION, commandDefinition.getDescription());
		annotations = ValidTestCommand.class.getAnnotations();
		for (Annotation annotation : annotations) {
			for (ArgumentDefinition argumentDefinition : commandDefinition) {
				if (annotation instanceof CliArgument) {
					castedAnnotation = (CliArgument) annotation;
					if (castedAnnotation.longName().equals(argumentDefinition.getLongName())) {
						ArgumentDefinitionTest.compareAnnotationWithDefinition(castedAnnotation, argumentDefinition);
					}
				}
			}
		}
	}

	/**
	 * Tests if the CliCommand annotation of the superclass is overridden by the CliCommand annotation of the subclass
	 */
	@Test
	public void testReadCommandDefinition_OverriddenCliCommandAnnotation() {
		CommandDefinitionReader reader;
		CommandDefinition commandDefinition;
		String nameBefore;
		String nameAfter;

		reader = new CommandDefinitionReader();
		commandDefinition = reader.readCommandDefinition(new OverriddenAnnotationsTestCommand());
		nameBefore = OverriddenAnnotationsTestCommand.COMMAND_NAME;
		nameAfter = commandDefinition.getName();
		assertEquals(nameBefore, nameAfter);
	}

	/**
	 * Tests if the CliArgument annotation of the method in the superclass that is overridden in the subclass is read correctly.
	 */
	@Test
	public void testReadCommandDefinition_OverriddenCliArgumentAnnotation() {
		CommandDefinitionReader reader;
		CommandDefinition commandDefinition;
		ArgumentDefinition definition;
		String nameBefore;
		String nameAfter;

		reader = new CommandDefinitionReader();
		commandDefinition = reader.readCommandDefinition(new OverriddenAnnotationsTestCommand());

		nameBefore = OverriddenAnnotationsTestCommand.ARGUMENT_OVERRIDDEN_LONG_NAME;
		definition = commandDefinition.getArgumentDefinition(nameBefore);
		nameAfter = definition.getLongName();
		assertEquals(nameBefore, nameAfter);
	}

	/**
	 * Tests if the inherited methods with CliAnnotations are read correctly
	 */
	@Test
	public void testReadCommandDefinition_InheritedCliArgument() {
		CommandDefinitionReader reader;
		CommandDefinition commandDefinition;
		ArgumentDefinition helpArgumentDefinition;
		ArgumentDefinition testArgumentDefinition;
		String helpArgumentNameBefore;
		String testArgumentName;
		String helpArgumentNameAfter;

		reader = new CommandDefinitionReader();
		commandDefinition = reader.readCommandDefinition(new InheritedArgumentTestCommand());

		helpArgumentNameBefore = InheritedArgumentTestCommand.ARGUMENT_HELP_LONG_NAME;
		helpArgumentDefinition = commandDefinition.getArgumentDefinition(helpArgumentNameBefore);
		helpArgumentNameAfter = helpArgumentDefinition.getLongName();
		assertEquals(helpArgumentNameBefore, helpArgumentNameAfter);

		testArgumentName = InheritedArgumentTestCommand.ARGUMENT_TEST_LONG_NAME;
		testArgumentDefinition = commandDefinition.getArgumentDefinition(testArgumentName);
		assertEquals(testArgumentName, testArgumentDefinition.getLongName());
	}

	@Test(expected = CommandLineAnnotationException.class)
	public void testReadCommandDefinition_NoCliCommandAnnotation() {
		CommandDefinitionReader reader;

		reader = new CommandDefinitionReader();
		reader.readCommandDefinition(new NoCliCommandAnnotationTestCommand());
	}

	@Test(expected = CommandLineException.class)
	public void testReadCommandDefinition_NoCommandName() {
		CommandDefinitionReader reader;

		reader = new CommandDefinitionReader();
		reader.readCommandDefinition(new NoCommandNameTestCommand());
	}

	@Test(expected = CommandLineException.class)
	public void testReadArgumentDefinitions_DuplicateShortName() throws Exception {
		CommandDefinitionReader reader;

		reader = new CommandDefinitionReader();
		reader.readArgumentDefinitions(DuplicateShortNameTestCommand.class);
	}

	@Test(expected = CommandLineException.class)
	public void testReadArgumentDefinitions_DuplicateLongNameTestCommand() throws Exception {
		CommandDefinitionReader reader;

		reader = new CommandDefinitionReader();
		reader.readArgumentDefinitions(DuplicateLongNameTestCommand.class);
	}

	public void testReadArgumentDefinition_ObligatoryWithParserWithValidator() throws Exception {
		Method method;
		CliArgument annotation;
		ArgumentDefinition definition;
		CommandDefinitionReader reader;

		method = ValidTestCommand.class.getMethod("obligatoryWithParserWithValidator", String.class);
		annotation = method.getAnnotation(CliArgument.class);
		reader = new CommandDefinitionReader();
		definition = reader.readArgumentDefinition(method);
		ArgumentDefinitionTest.compareAnnotationWithDefinition(annotation, definition);
	}

	@Test
	public void testReadArgumentDefinition_ObligatoryWithParserNoValidator() throws Exception {
		Method method;
		CliArgument annotation;
		ArgumentDefinition definition;
		CommandDefinitionReader reader;

		method = ValidTestCommand.class.getMethod("obligatoryWithParserNoValidator", String.class);
		annotation = method.getAnnotation(CliArgument.class);
		reader = new CommandDefinitionReader();
		definition = reader.readArgumentDefinition(method);
		ArgumentDefinitionTest.compareAnnotationWithDefinition(annotation, definition);
	}

	@Test
	public void testReadArgumentDefinition_ObligatoryNoParserWithValidator() throws Exception {
		Method method;
		CliArgument annotation;
		ArgumentDefinition definition;
		CommandDefinitionReader reader;

		method = ValidTestCommand.class.getMethod("obligatoryNoParserWithValidator", String.class);
		annotation = method.getAnnotation(CliArgument.class);
		reader = new CommandDefinitionReader();
		definition = reader.readArgumentDefinition(method);
		ArgumentDefinitionTest.compareAnnotationWithDefinition(annotation, definition);
	}

	@Test
	public void testReadArgumentDefinition_ObligatoryNoParserNoValidator() throws Exception {
		Method method;
		CliArgument annotation;
		ArgumentDefinition definition;
		CommandDefinitionReader reader;

		method = ValidTestCommand.class.getMethod("obligatoryNoParserNoValidator", String.class);
		annotation = method.getAnnotation(CliArgument.class);
		reader = new CommandDefinitionReader();
		definition = reader.readArgumentDefinition(method);
		ArgumentDefinitionTest.compareAnnotationWithDefinition(annotation, definition);
	}

	@Test
	public void testReadArgumentDefinition_NonObligatoryNonNullDefaultValue() throws Exception {
		Method method;
		CliArgument annotation;
		ArgumentDefinition definition;
		CommandDefinitionReader reader;

		method = ValidTestCommand.class.getMethod("optionalNonNullDefaultValue", String.class);
		annotation = method.getAnnotation(CliArgument.class);
		reader = new CommandDefinitionReader();
		definition = reader.readArgumentDefinition(method);
		ArgumentDefinitionTest.compareAnnotationWithDefinition(annotation, definition);
	}

	@Test
	public void testReadArgumentDefinition_NonObligatoryNullDefaultValue() throws Exception {
		Method method;
		CliArgument annotation;
		ArgumentDefinition definition;
		CommandDefinitionReader reader;

		method = ValidTestCommand.class.getMethod("optionalNullDefaultValue", String.class);
		annotation = method.getAnnotation(CliArgument.class);
		reader = new CommandDefinitionReader();
		definition = reader.readArgumentDefinition(method);
		ArgumentDefinitionTest.compareAnnotationWithDefinition(annotation, definition);
	}

	@Test(expected = CommandLineException.class)
	public void testReadArgumentDefinition_NoMethodParameter() throws Exception {
		CommandDefinitionReader reader;
		Method method;

		method = NoSetterParameterTestCommand.class.getMethod("method");
		reader = new CommandDefinitionReader();
		reader.readArgumentDefinition(method);
	}

	@Test(expected = CommandLineException.class)
	public void testReadArgumentDefinition_TooManyMethodParameters() throws Exception {
		CommandDefinitionReader reader;
		Method method;

		method = TooManyMethodParametersTestCommand.class.getMethod("method", String.class, String.class);
		reader = new CommandDefinitionReader();
		reader.readArgumentDefinition(method);
	}

	@Test
	public void testWrapPrimitiveClass_Short() {
		CommandDefinitionReader reader;

		reader = new CommandDefinitionReader();
		assertEquals(Short.class, reader.wrapPrimitiveClass(short.class));
	}

	@Test
	public void testWrapPrimitiveClass_Int() {
		CommandDefinitionReader reader;

		reader = new CommandDefinitionReader();
		assertEquals(Integer.class, reader.wrapPrimitiveClass(int.class));
	}

	@Test
	public void testWrapPrimitiveClass_Long() {
		CommandDefinitionReader reader;

		reader = new CommandDefinitionReader();
		assertEquals(Long.class, reader.wrapPrimitiveClass(long.class));
	}

	@Test
	public void testWrapPrimitiveClass_Char() {
		CommandDefinitionReader reader;

		reader = new CommandDefinitionReader();
		assertEquals(Character.class, reader.wrapPrimitiveClass(char.class));
	}

	@Test
	public void testWrapPrimitiveClass_Boolean() {
		CommandDefinitionReader reader;

		reader = new CommandDefinitionReader();
		assertEquals(Boolean.class, reader.wrapPrimitiveClass(boolean.class));
	}

	@Test
	public void testWrapPrimitiveClass_Float() {
		CommandDefinitionReader reader;

		reader = new CommandDefinitionReader();
		assertEquals(Float.class, reader.wrapPrimitiveClass(float.class));
	}

	@Test
	public void testWrapPrimitiveClass_Double() {
		CommandDefinitionReader reader;

		reader = new CommandDefinitionReader();
		assertEquals(Double.class, reader.wrapPrimitiveClass(double.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrapPrimitiveClass_InvalidClass() {
		CommandDefinitionReader reader;

		reader = new CommandDefinitionReader();
		assertEquals(Double.class, reader.wrapPrimitiveClass(Object.class));
	}
}