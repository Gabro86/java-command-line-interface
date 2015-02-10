package commandline.language.gnu.syntax;

import commandline.language.syntax.SyntaxException;
import org.junit.Test;

/**
 * User: gno Date: 02.07.13 Time: 16:27
 */
public class GnuCommandNameSyntaxValidatorTest {
	public GnuCommandNameSyntaxValidatorTest() {
		super();
	}

	@Test
	public void testValidate_Valid_ShortName() {
		validate("c");
	}

	@Test
	public void testValidate_Valid_LongName() {
		validate("command");
	}

	@Test
	public void testValidate_Valid_DigitName() {
		validate("command2");
	}

	@Test
	public void testValidate_Valid_DividedName() {
		validate("this-is-a-command");
	}

	@Test(expected = SyntaxException.class)
	public void testValidate_Invalid_NoChars() {
		validate("");
	}

	@Test(expected = SyntaxException.class)
	public void testValidate_Invalid_NoSpaces() {
		validate(" ");
	}

	@Test(expected = SyntaxException.class)
	public void testValidate_Invalid_CapitalizedName() {
		validate("Command");
	}

	@Test(expected = SyntaxException.class)
	public void testValidate_Invalid_StartingWithHyphen() {
		validate("-command");
	}

	@Test(expected = SyntaxException.class)
	public void testValidate_Invalid_EndingWithHyphen() {
		validate("command-");
	}

	@Test(expected = SyntaxException.class)
	public void testValidate_Invalid_DoubleHyphen() {
		validate("com--mand");
	}

	@Test(expected = SyntaxException.class)
	public void testValidate_Invalid_NullValue() {
		validate(null);
	}

	public void validate(String name) {
		GnuCommandNameSyntaxValidator validator;

		validator = new GnuCommandNameSyntaxValidator();
		validator.validate(name);
	}
}
