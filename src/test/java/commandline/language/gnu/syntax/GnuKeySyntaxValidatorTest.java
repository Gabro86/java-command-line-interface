package commandline.language.gnu.syntax;

import commandline.language.syntax.SyntaxException;
import org.junit.Test;

/**
 * User: gno Date: 02.07.13 Time: 16:25
 */
public class GnuKeySyntaxValidatorTest {
	public GnuKeySyntaxValidatorTest() {
		super();
	}

	@Test
	public void testValidate_ShortKey() throws Exception {
		GnuKeySyntaxValidator validator;

		validator = new GnuKeySyntaxValidator();
		validator.validate("-k");
	}

	@Test
	public void testValidate_LongKey() throws Exception {
		GnuKeySyntaxValidator validator;

		validator = new GnuKeySyntaxValidator();
		validator.validate("--key");
	}

	@Test(expected = SyntaxException.class)
	public void testValidate_InvalidKey() throws Exception {
		GnuKeySyntaxValidator validator;

		validator = new GnuKeySyntaxValidator();
		validator.validate("key");
	}
}