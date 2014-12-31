package commandline.language.syntax.key;

import commandline.language.syntax.validator.key.GnuKeySyntaxValidator;
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
}
