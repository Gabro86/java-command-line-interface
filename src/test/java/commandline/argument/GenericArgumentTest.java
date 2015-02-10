package commandline.argument;

import org.junit.Test;

public class GenericArgumentTest {
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor() {
		new GenericArgument(" ", "value");
	}
}