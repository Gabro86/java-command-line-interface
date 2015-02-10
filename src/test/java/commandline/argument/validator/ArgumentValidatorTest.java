package commandline.argument.validator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArgumentValidatorTest {
	@Test
	public void testValidate() throws Exception {
		MockStringArgumentValidator validator;

		validator = new MockStringArgumentValidator();
		validator.validate("test-value");
	}

	@Test
	public void testGetSupportedClass() throws Exception {
		MockStringArgumentValidator validator;

		validator = new MockStringArgumentValidator();
		assertEquals(String.class, validator.getSupportedClass());
	}

	@Test
	public void testIsCompatible() throws Exception {
		MockStringArgumentValidator validator;

		validator = new MockStringArgumentValidator();
		assertTrue(validator.isCompatible(String.class));
	}
}