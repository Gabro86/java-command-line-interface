package commandline.language.gnu.syntax;

import commandline.language.syntax.SyntaxException;
import org.junit.Test;

/**
 * User: gno Date: 28.06.13 Time: 12:49
 */
public class GnuShortKeySyntaxValidatorTest {
	public GnuShortKeySyntaxValidatorTest() {
		super();
	}

	@Test
	public void testValidate_Valid_LowerCaseChar() {
		validate("-x");
	}

	@Test(expected = SyntaxException.class)
	public void testValidate_Invalid_Digit() {
		//Short names with a digit are not allowed, because a user could pass a negative digit as a value. Therefore the short name
		//and the value could not be distinguished.
		validate("-1");
	}

	@Test(expected = SyntaxException.class)
	public void testValidate_Invalid_MultiChar() {
		validate("-xyz");
	}

	@Test(expected = SyntaxException.class)
	public void testValidate_Invalid_MissingPrefix() {
		validate("x");
	}

	@Test(expected = SyntaxException.class)
	public void testValidate_Invalid_MissingPrefixMultiChar() {
		validate("xyz");
	}

	@Test(expected = SyntaxException.class)
	public void testValidate_Invalid_UnsupportedChar() {
		validate("$");
	}

	@Test(expected = SyntaxException.class)
	public void testValidate_Invalid_MissingName() {
		validate("-");
	}

	@Test(expected = SyntaxException.class)
	public void testValidate_Invalid_LongPrefix() {
		validate("--");
	}

	@Test(expected = SyntaxException.class)
	public void testValidate_Invalid_NoChar() {
		validate("");
	}

	@Test(expected = SyntaxException.class)
	public void testValidate_Invalid_EmptyString() {
		validate(" ");
	}

	private void validate(String argument) {
		GnuShortKeySyntaxValidator validator;

		validator = new GnuShortKeySyntaxValidator();
		validator.validate(argument);
	}

	@Test(expected = SyntaxException.class)
	public void testValidate_Invalid_UpperCaseChar() {
		validate("-X");
	}
}
