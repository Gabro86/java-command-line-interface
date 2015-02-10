package commandline.argument;

import commandline.argument.validator.DefaultArgumentValidator;
import commandline.language.parser.specific.StringArgumentParser;
import org.junit.Test;

import java.util.Collection;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * User: gno, Date: 26.07.13 - 13:53
 */
public class ArgumentListTest {
	public ArgumentListTest() {
		super();
	}

	@Test
	public void testAdd() throws Exception {
		ArgumentList arguments;
		Argument<String> argument;

		argument = Argument.createMock();
		arguments = new ArgumentList();
		assertSame(null, arguments.get(argument.getLongName()));
		arguments.add(argument);
		assertSame(argument, arguments.get(argument.getLongName()));
		assertSame(argument, arguments.get(argument.getShortName()));
	}

	@Test
	public void testAdd_DoubleAdd() throws Exception {
		ArgumentList arguments;
		Argument<String> argument;

		argument = Argument.createMock();
		arguments = new ArgumentList();
		arguments.add(argument);
		assertEquals(1, arguments.getSize());
		arguments.add(argument);
		assertEquals(1, arguments.getSize());
	}

	@Test
	public void testAddAll() throws Exception {
		ArgumentList arguments;
		Argument<String> argument1;
		Argument<String> argument2;
		ArgumentDefinition definition1;
		ArgumentDefinition definition2;
		LinkedList<Argument<?>> argumentList;
		ArgumentDefinitionBuilder builder;

		builder = new ArgumentDefinitionBuilder();
		builder.setLongName("argument1");
		builder.setShortName("a");
		builder.setExamples(new String[] {"example"});
		builder.setDescription("description");
		builder.setObligatory(false);
		builder.setDefaultValue("default-value");
		builder.setParserClass(StringArgumentParser.class);
		builder.setValidatorClass(DefaultArgumentValidator.class);
		builder.setValueClass(String.class);
		definition1 = builder.create();

		builder.setLongName("argument2");
		definition2 = builder.create();

		builder.setLongName("argument2");
		argument1 = new Argument<>(definition1, "value1");
		argument2 = new Argument<>(definition2, "value2");

		argumentList = new LinkedList<>();
		argumentList.add(argument1);
		argumentList.add(argument2);

		arguments = new ArgumentList();
		assertTrue(arguments.isEmpty());
		arguments.addAll(argumentList);
		assertEquals(argumentList, new LinkedList<>(arguments.getCollection()));
	}

	@Test
	public void testAddAll_DuplicateArgument() throws Exception {
		ArgumentList arguments;
		Argument argument;
		LinkedList<Argument<?>> argumentsToAdd;

		argument = Argument.createMock();
		argumentsToAdd = new LinkedList<>();
		argumentsToAdd.add(argument);
		argumentsToAdd.add(argument);

		arguments = new ArgumentList();
		arguments.addAll(argumentsToAdd);
		assertEquals(1, arguments.getSize());
		assertSame(argument, arguments.get(argument.getLongName()));
	}

	@Test
	public void testClear() throws Exception {
		ArgumentList arguments;
		Argument<String> argument;

		argument = Argument.createMock();
		arguments = new ArgumentList();
		assertEquals(true, arguments.isEmpty());
		arguments.add(argument);
		assertFalse(arguments.isEmpty());
		arguments.clear();
		assertTrue(arguments.isEmpty());
	}

	@Test
	public void testContainsByArgumentName_LongName() throws Exception {
		ArgumentList arguments;
		Argument<String> argument;
		String name;

		argument = Argument.createMock();
		arguments = new ArgumentList();
		name = argument.getLongName();
		assertFalse(arguments.contains(name));
		arguments.add(argument);
		assertTrue(arguments.contains(name));
	}

	@Test
	public void testContainsByArgumentName_ShortName() throws Exception {
		ArgumentList arguments;
		Argument<String> argument;
		String name;

		argument = Argument.createMock();
		arguments = new ArgumentList();
		name = argument.getShortName();
		assertFalse(arguments.contains(name));
		arguments.add(argument);
		assertTrue(arguments.contains(name));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testContainsByArgumentName_EmptyName() throws Exception {
		ArgumentList arguments;

		arguments = new ArgumentList();
		arguments.contains(" ");
	}

	@Test
	public void testContainsByArgumentReference() throws Exception {
		ArgumentList arguments;
		Argument<String> argument;

		argument = Argument.createMock();
		arguments = new ArgumentList();
		assertFalse(arguments.contains(argument));
		arguments.add(argument);
		assertTrue(arguments.contains(argument));
	}

	@Test
	public void testGet() throws Exception {
		ArgumentList arguments;
		Argument<String> argument;

		argument = Argument.createMock();
		arguments = new ArgumentList();
		assertNull(arguments.get(argument.getLongName()));
		arguments.add(argument);
		assertSame(argument, arguments.get(argument.getLongName()));
	}

	@Test
	public void testGetByLongName() throws Exception {
		ArgumentList arguments;
		Argument<String> argument;
		String name;

		argument = Argument.createMock();
		arguments = new ArgumentList();
		name = argument.getLongName();
		assertNull(arguments.get(name));
		arguments.add(argument);
		assertSame(argument, arguments.get(name));
	}

	@Test
	public void testGetByShortName() throws Exception {
		ArgumentList arguments;
		Argument<String> argument;
		String name;

		argument = Argument.createMock();
		arguments = new ArgumentList();
		name = argument.getShortName();
		assertNull(arguments.get(name));
		arguments.add(argument);
		assertSame(argument, arguments.get(name));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGet_EmptyName() throws Exception {
		ArgumentList arguments;

		arguments = new ArgumentList();
		arguments.get(" ");
	}

	@Test
	public void testGet_NonExistentArgument() throws Exception {
		ArgumentList arguments;

		arguments = new ArgumentList();
		assertSame(null, arguments.get("non-existent-argument"));
	}

	@Test
	public void testGetCollection() throws Exception {
		ArgumentList arguments;
		Collection<Argument<?>> unmodifiableArguments;
		Argument<String> argument;

		argument = Argument.createMock();
		arguments = new ArgumentList();
		arguments.add(argument);
		unmodifiableArguments = arguments.getCollection();
		assertEquals(arguments.getSize(), unmodifiableArguments.size());
		assertSame(argument, arguments.get(argument.getLongName()));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testGetCollection_AddToUnmodifiableCollection() throws Exception {
		ArgumentList arguments;
		Collection<Argument<?>> unmodifiableArguments;

		arguments = new ArgumentList();
		unmodifiableArguments = arguments.getCollection();
		unmodifiableArguments.add(Argument.createMock());
	}

	@Test
	public void testGetSize() throws Exception {
		ArgumentList arguments;

		arguments = new ArgumentList();
		assertEquals(0, arguments.getSize());
		arguments.add(Argument.createMock());
		assertEquals(1, arguments.getSize());
	}

	@Test
	public void testIsEmpty() throws Exception {
		ArgumentList arguments;
		Argument<String> argument;

		arguments = new ArgumentList();
		assertTrue(arguments.isEmpty());
		argument = Argument.createMock();
		arguments.add(argument);
		assertFalse(arguments.isEmpty());
		arguments.remove(argument);
		assertTrue(arguments.isEmpty());
	}

	@Test
	public void testRemoveByArgumentName_LongName() throws Exception {
		ArgumentList arguments;
		Argument<String> argument;

		argument = Argument.createMock();
		arguments = new ArgumentList();
		assertTrue(arguments.isEmpty());
		arguments.add(argument);
		assertFalse(arguments.isEmpty());
		assertSame(argument, arguments.remove(argument.getLongName()));
		assertTrue(arguments.isEmpty());
	}

	@Test
	public void testRemoveByArgumentName_ShortName() throws Exception {
		ArgumentList arguments;
		Argument<String> argument;

		argument = Argument.createMock();
		arguments = new ArgumentList();
		assertTrue(arguments.isEmpty());
		arguments.add(argument);
		assertFalse(arguments.isEmpty());
		assertSame(argument, arguments.remove(argument.getShortName()));
		assertTrue(arguments.isEmpty());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRemoveByArgumentName_EmptyName() throws Exception {
		ArgumentList arguments;

		arguments = new ArgumentList();
		arguments.remove(" ");
	}

	@Test
	public void testRemoveByArgumentName_NonExistentArgument() throws Exception {
		ArgumentList arguments;

		arguments = new ArgumentList();
		assertNull(arguments.remove("non-existent-argument"));
	}

	@Test
	public void testRemoveByArgumentReference() throws Exception {
		ArgumentList arguments;
		Argument<String> argument;

		argument = Argument.createMock();
		arguments = new ArgumentList();
		assertTrue(arguments.isEmpty());
		arguments.add(argument);
		assertFalse(arguments.isEmpty());
		assertSame(argument, arguments.remove(argument));
		assertTrue(arguments.isEmpty());
	}

	@Test
	public void testRemoveByArgumentReference_NonExistentArgument() throws Exception {
		ArgumentList arguments;

		arguments = new ArgumentList();
		assertNull(arguments.remove(Argument.createMock()));
	}
}
