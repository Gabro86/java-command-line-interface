package commandline.command;

import commandline.argument.GenericArgumentList;
import org.junit.Test;

public class GenericCommandTest {
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor() {
		new GenericCommand(" ", new GenericArgumentList());
	}
}