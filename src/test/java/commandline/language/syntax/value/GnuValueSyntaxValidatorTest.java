package commandline.language.syntax.value;

import commandline.language.syntax.SyntaxException;
import commandline.language.syntax.validator.value.GnuValueSyntaxValidator;
import org.junit.Test;

/**
 * User: gno Date: 02.07.13 Time: 16:41
 */
public class GnuValueSyntaxValidatorTest {
	public GnuValueSyntaxValidatorTest() {
		super();
	}

	//TODO Implement more value syntax tests

	@Test
	public void testValidate_Valid_ContainingHyphen() {
		validate("val-ue");
	}

	@Test
	public void testValidate_Valid_EndingWithHyphen() {
		validate("value-");
	}

	@Test(expected = SyntaxException.class)
	public void testValidate_Invalid_StartingWithHyphen() {
		validate("-value");
	}

	@Test
	public void testValidate_Valid_EmptyValue() {
		validate("");
	}

	@Test
	public void testValidate_Valid_Spaces() {
		validate(" ");
	}

	@Test
	public void testValidate_OneDigit() {
		validate("0");
	}

	@Test
	public void testValidate_NegativeValue() {
		validate("-1");
	}

	@Test
	public void testValidate_MultipleDigit() {
		validate("100");
	}

	@Test
	public void testValidate_OneLetter() {
		validate("a");
	}

	@Test
	public void testValidate_MultipleLetters() {
		validate("abc");
	}

	@Test
	public void testValidate_MiscValue() {
		validate("a1");
	}

	public void validate(String value) {
		GnuValueSyntaxValidator validator;

		validator = new GnuValueSyntaxValidator();
		validator.validate(value);
	}
}
