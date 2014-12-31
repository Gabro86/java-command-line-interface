package commandline.argument;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * User: gno, Date: 26.07.13 - 13:53
 */
public class ArgumentListTest {
	public ArgumentListTest() {
		super();
	}

	@Test
	public void testGetArgumentList() throws Exception {
		ArgumentList arguments;
		List<Argument<?>> unmodifiableArguments;
		MockArgument argument;

		argument = new MockArgument();
		arguments = new ArgumentList();
		arguments.put(argument);
		unmodifiableArguments = arguments.getArgumentList();
		assertEquals(arguments.getSize(), unmodifiableArguments.size());
		assertSame(argument, arguments.get(argument.getLongName()));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testGetArgumentList_AddToUnmodifiableList() throws Exception {
		ArgumentList arguments;
		List<Argument<?>> unmodifiableArguments;

		arguments = new ArgumentList();
		unmodifiableArguments = arguments.getArgumentList();
		unmodifiableArguments.add(new MockArgument());
	}

	@Test
	public void testGetSize() throws Exception {
		ArgumentList arguments;
		MockArgument argument;

		argument = new MockArgument();
		arguments = new ArgumentList();
		assertEquals(0, arguments.getSize());
		arguments.put(argument);
		assertEquals(1, arguments.getSize());
	}

	@Test
	public void testContains() throws Exception {
		ArgumentList arguments;
		MockArgument argument;

		argument = new MockArgument();
		arguments = new ArgumentList();
		assertEquals(false, arguments.contains(argument.getLongName()));
		arguments.put(argument);
		assertEquals(true, arguments.contains(argument.getLongName()));
	}

	@Test
	public void testAdd() throws Exception {
		ArgumentList arguments;
		MockArgument argument;

		argument = new MockArgument();
		arguments = new ArgumentList();
		assertSame(null, arguments.get(argument.getLongName()));
		arguments.put(argument);
		assertSame(argument, arguments.get(argument.getLongName()));
	}

	@Test
	public void testAdd_DoubleAdd() throws Exception {
		ArgumentList arguments;
		MockArgument argument;

		argument = new MockArgument();
		arguments = new ArgumentList();
		arguments.put(argument);
		assertEquals(1, arguments.getSize());
		arguments.put(argument);
		assertEquals(1, arguments.getSize());
	}

	@Test
	public void testGet() throws Exception {
		ArgumentList arguments;
		MockArgument argument;

		argument = new MockArgument();
		arguments = new ArgumentList();
		assertSame(null, arguments.get(argument.getLongName()));
		arguments.put(argument);
		assertSame(argument, arguments.get(argument.getLongName()));
	}

	@Test
	public void testGet_NonExistentArgument() throws Exception {
		ArgumentList arguments;

		arguments = new ArgumentList();
		assertSame(null, arguments.get("test"));
	}

	@Test
	public void testRemove() throws Exception {
		ArgumentList arguments;
		MockArgument argument;

		argument = new MockArgument();
		arguments = new ArgumentList();
		arguments.put(argument);
		assertSame(argument, arguments.get(argument.getLongName()));
		arguments.remove(argument.getLongName());
		assertEquals(null, arguments.get(argument.getLongName()));
	}

	@Test
	public void testIsEmpty() throws Exception {
		ArgumentList arguments;
		MockArgument argument;

		argument = new MockArgument();
		arguments = new ArgumentList();
		assertEquals(true, arguments.isEmpty());
		arguments.put(argument);
		assertEquals(false, arguments.isEmpty());
	}

	@Test
	public void testClear() throws Exception {
		ArgumentList arguments;
		MockArgument argument;

		argument = new MockArgument();
		arguments = new ArgumentList();
		assertEquals(true, arguments.isEmpty());
		arguments.put(argument);
		assertEquals(false, arguments.isEmpty());
		arguments.clear();
		assertEquals(true, arguments.isEmpty());
	}

	@Test
	public void testToArray() throws Exception {
		ArgumentList arguments;
		MockArgument argument;
		String[] argumentArray;

		argument = new MockArgument();
		arguments = new ArgumentList();
		arguments.put(argument);
		argumentArray = arguments.toArray();
		assertEquals(argument.getLongName(), argumentArray[0]);
		assertEquals(argument.getValue(), argumentArray[1]);
	}
}
