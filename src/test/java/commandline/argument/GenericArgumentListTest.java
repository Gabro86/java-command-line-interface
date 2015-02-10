package commandline.argument;

import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class GenericArgumentListTest {
	public GenericArgumentListTest() {
		super();
	}

	@Test
	public void testAdd() throws Exception {
		GenericArgumentList arguments;
		GenericArgument argument;

		argument = GenericArgument.createMock();
		arguments = new GenericArgumentList();
		arguments.add(argument);
		assertTrue(arguments.contains(argument));
	}

	@Test
	public void testAdd_DoubleAdd() throws Exception {
		GenericArgumentList arguments;
		GenericArgument argument;

		argument = GenericArgument.createMock();
		arguments = new GenericArgumentList();
		assertEquals(0, arguments.getSize());
		arguments.add(argument);
		assertEquals(1, arguments.getSize());
		arguments.add(argument);
		assertEquals(1, arguments.getSize());
	}

	@Test
	public void testAddAll() throws Exception {
		GenericArgumentList arguments;
		GenericArgument argument1;
		GenericArgument argument2;
		LinkedList<GenericArgument> argumentsToAdd;

		argument1 = new GenericArgument("GenericArgument1", "Value1");
		argument2 = new GenericArgument("GenericArgument2", "Value2");
		argumentsToAdd = new LinkedList<>();
		argumentsToAdd.add(argument1);
		argumentsToAdd.add(argument2);
		arguments = new GenericArgumentList();
		arguments.addAll(argumentsToAdd);
		assertTrue(arguments.contains(argument1));
		assertTrue(arguments.contains(argument2));
	}

	@Test
	public void testAddAll_DuplicateArgument() throws Exception {
		GenericArgumentList arguments;
		GenericArgument argument;
		LinkedList<GenericArgument> argumentsToAdd;

		argument = GenericArgument.createMock();
		argumentsToAdd = new LinkedList<>();
		argumentsToAdd.add(argument);
		argumentsToAdd.add(argument);

		arguments = new GenericArgumentList();
		arguments.addAll(argumentsToAdd);
		assertEquals(1, arguments.getSize());
		assertSame(argument, arguments.get(argument.getName()));
	}

	@Test
	public void testClear() throws Exception {
		GenericArgumentList arguments;
		GenericArgument argument;

		argument = GenericArgument.createMock();
		arguments = new GenericArgumentList();
		arguments.add(argument);
		assertFalse(arguments.isEmpty());
		arguments.clear();
		assertTrue(arguments.isEmpty());
	}

	@Test
	public void testContainsByArgumentName() throws Exception {
		GenericArgumentList arguments;
		GenericArgument argument;
		String argumentName;

		argument = GenericArgument.createMock();
		arguments = new GenericArgumentList();
		argumentName = argument.getName();
		assertFalse(arguments.contains(argumentName));
		arguments.add(argument);
		assertTrue(arguments.contains(argumentName));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testContainsByArgumentName_EmptyName() throws Exception {
		GenericArgumentList arguments;

		arguments = new GenericArgumentList();
		arguments.contains(" ");
	}

	@Test
	public void testContainsByArgumentReference() throws Exception {
		GenericArgumentList arguments;
		GenericArgument argument;

		argument = GenericArgument.createMock();
		arguments = new GenericArgumentList();
		assertFalse(arguments.contains(argument));
		arguments.add(argument);
		assertTrue(arguments.contains(argument));
	}

	@Test
	public void testGet() throws Exception {
		GenericArgumentList arguments;
		GenericArgument argument;

		argument = GenericArgument.createMock();
		arguments = new GenericArgumentList();
		assertNull(arguments.get(argument.getName()));
		arguments.add(argument);
		assertNotNull(arguments.get(argument.getName()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGet_EmptyName() throws Exception {
		GenericArgumentList arguments;

		arguments = new GenericArgumentList();
		arguments.get(" ");
	}

	@Test
	public void testGet_NonExistentArgument() throws Exception {
		GenericArgumentList arguments;

		arguments = new GenericArgumentList();
		arguments.get("non-existent-argument");
	}

	@Test
	public void testGetSize() throws Exception {
		GenericArgumentList arguments;

		arguments = new GenericArgumentList();
		assertEquals(0, arguments.getSize());
		arguments.add(GenericArgument.createMock());
		assertEquals(1, arguments.getSize());
	}

	@Test
	public void testGetCollection() throws Exception {
		GenericArgumentList arguments;
		GenericArgument argument;

		argument = GenericArgument.createMock();
		arguments = new GenericArgumentList();
		arguments.add(argument);
		assertTrue(arguments.getCollection().contains(argument));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testGetCollection_AddToUnmodifiableCollection() throws Exception {
		GenericArgumentList arguments;
		GenericArgument argument;

		arguments = new GenericArgumentList();
		argument = GenericArgument.createMock();
		arguments.getCollection().add(argument);
	}

	@Test
	public void testIsEmpty() throws Exception {
		GenericArgumentList arguments;

		arguments = new GenericArgumentList();
		assertTrue(arguments.isEmpty());
		arguments.add(GenericArgument.createMock());
		assertFalse(arguments.isEmpty());
	}

	@Test
	public void testRemoveByArgumentName() throws Exception {
		GenericArgumentList arguments;
		GenericArgument argument;
		String argumentName;

		argument = GenericArgument.createMock();
		arguments = new GenericArgumentList();
		argumentName = argument.getName();
		assertNull(arguments.remove(argumentName));
		arguments.add(argument);
		assertSame(argument, arguments.remove(argumentName));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRemoveByArgumentName_EmptyName() throws Exception {
		GenericArgumentList arguments;

		arguments = new GenericArgumentList();
		arguments.remove(" ");
	}

	@Test
	public void testRemoveByArgumentName_NonExistentArgument() throws Exception {
		GenericArgumentList arguments;

		arguments = new GenericArgumentList();
		arguments.remove("non-existent-argument");
	}

	@Test
	public void testRemoveByArgumentReference() throws Exception {
		GenericArgumentList arguments;
		GenericArgument argument;

		argument = GenericArgument.createMock();
		arguments = new GenericArgumentList();
		assertNull(arguments.remove(argument));
		arguments.add(argument);
		assertSame(argument, arguments.remove(argument));
	}

	@Test
	public void testRemoveByArgumentReference_NonExistentArgument() throws Exception {
		GenericArgumentList arguments;

		arguments = new GenericArgumentList();
		arguments.remove(GenericArgument.createMock());
	}
}