package commandline.language.gnu.syntax;

import commandline.language.syntax.SyntaxException;
import commandline.exception.ArgumentNullException;
import org.junit.Test;

/**
 * User: gno Date: 02.07.13 Time: 15:29
 */
public class GnuCommandSyntaxValidatorTest {
	public GnuCommandSyntaxValidatorTest() {
		super();
	}

	@Test
	public void testValidate_Valid_OnlyCommandName() {
		validate("command");
	}

	@Test
	public void testValidate_Valid_ShortKey() {
		validate("command -k value");
	}

	@Test
	public void testValidate_Valid_LongKey() {
		validate("command --key value");
	}

	@Test
	public void testValidate_Valid_MultiLongKey() {
		validate("command --key1 value1 --key2 value2");
	}

	@Test
	public void testValidate_Valid_MultiShortKey() {
		validate("command -k value1 -l value2");
	}

	@Test
	public void testValidate_Valid_MultiShortLongKey() {
		validate("command --key value1 -k value2");
	}

	@Test(expected = SyntaxException.class)
	public void testValidate_Invalid_NoChar() {
		validate("");
	}

	@Test(expected = SyntaxException.class)
	public void testValidate_Invalid_SpaceChar() {
		validate("     ");
	}

	@Test(expected = SyntaxException.class)
	public void testValidate_Invalid_NoCommandName() {
		validate("--key value");
	}

	@Test(expected = SyntaxException.class)
	public void testValidate_Invalid_NoValue() {
		validate("command --key");
	}

	@Test(expected = SyntaxException.class)
	public void testValidate_Invalid_NoKey() {
		validate("command value");
	}

	@Test(expected = SyntaxException.class)
	public void testValidate_Invalid_NoKeyMultipleValues() {
		validate("command value value");
	}

	@Test(expected = SyntaxException.class)
	public void testValidate_Invalid_MultiValue() {
		validate("command --key value value");
	}

	@Test(expected = SyntaxException.class)
	public void testValidate_Invalid_MultiKey() {
		validate("command --key1 --key2");
	}

	@Test(expected = SyntaxException.class)
	public void testValidate_Invalid_MissingLastValue() {
		validate("command --key1 value1 --key2");
	}

	public void validate(String command) {
		validate(command.split(" "));
	}

	public void validate(String... commandTokens) {
		GnuCommandSyntaxValidator validator;

		if (commandTokens == null) {
			throw new ArgumentNullException();
		}
		validator = new GnuCommandSyntaxValidator();
		validator.validate(commandTokens);
	}
}
