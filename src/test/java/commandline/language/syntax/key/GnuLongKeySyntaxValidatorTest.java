package commandline.language.syntax.key;

import commandline.language.syntax.SyntaxException;
import commandline.language.syntax.validator.key.GnuLongKeySyntaxValidator;
import org.junit.Test;

/**
 * User: gno Date: 28.06.13 Time: 14:31
 */
public class GnuLongKeySyntaxValidatorTest {
	public GnuLongKeySyntaxValidatorTest() {
		super();
	}

	@Test
	public void testValidate_Valid_MinCharCount() {
		validate("--xy");
	}

	@Test
	public void testValidate_Valid_LowerCase() {
		validate("--test");
	}

	@Test
	public void testValidate_Valid_MultiWord() {
		validate("--this-is-a-test");
	}

	@Test
	public void testValidate_Valid_Digits() {
		validate("--123");
	}

	@Test
	public void testValidate_Valid_MultiWordDigits() {
		validate("--123-456-6789");
	}

	@Test
	public void testValidate_Valid_MultiWordCharsAndDigits() {
		validate("--abc-123");
	}

	@Test(expected = SyntaxException.class)
	public void testValidate_Invalid_TooShortName() {
		validate("--t");
	}

	@Test(expected = SyntaxException.class)
	public void testValidate_Invalid_UpperCase() {
		validate("--TEST");
	}

	@Test(expected = SyntaxException.class)
	public void testValidate_Invalid_CamelCaseMultiWord() {
		validate("--This-Is-A-Test");
	}

	@Test(expected = SyntaxException.class)
	public void testValidate_Invalid_NoPrefix() {
		validate("test");
	}

	@Test(expected = SyntaxException.class)
	public void testValidate_Invalid_MultiWordNoPrefix() {
		validate("this-is-a-test");
	}

	@Test(expected = SyntaxException.class)
	public void testValidate_Invalid_WrongPrefix() {
		validate("-test");
	}

	@Test(expected = SyntaxException.class)
	public void testValidate_Invalid_NoKey() {
		validate("--");
	}

	@Test(expected = SyntaxException.class)
	public void testValidate_Invalid_NoKeyAndWrongPrefix() {
		validate("-");
	}

	@Test(expected = SyntaxException.class)
	public void testValidate_Invalid_NoChars() {
		validate("");
	}

	@Test(expected = SyntaxException.class)
	public void testValidate_Invalid_EmptyString() {
		validate(" ");
	}

	@Test(expected = SyntaxException.class)
	public void testValidate_Invalid_Spaces() {
		validate("--this is a test");
	}

	private void validate(String argument) {
		GnuLongKeySyntaxValidator validator;

		validator = new GnuLongKeySyntaxValidator();
		validator.validate(argument);
	}
}
