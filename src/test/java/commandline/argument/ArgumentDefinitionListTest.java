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

public class ArgumentDefinitionListTest {
	public ArgumentDefinitionListTest() {
		super();
	}

	@Test
	public void testAdd() throws Exception {
		ArgumentDefinitionList definitions;
		ArgumentDefinition definition;

		definition = ArgumentDefinition.createMock();
		definitions = new ArgumentDefinitionList();
		assertFalse(definitions.contains(definition));
		definitions.add(definition);
		assertEquals(definition, definitions.get(definition.getLongName()));
		assertEquals(definition, definitions.get(definition.getShortName()));
	}

	@Test
	public void testAdd_DoubleAdd() throws Exception {
		ArgumentDefinitionList definitions;
		ArgumentDefinition definition;

		definition = ArgumentDefinition.createMock();
		definitions = new ArgumentDefinitionList();
		assertEquals(0, definitions.getSize());
		definitions.add(definition);
		assertEquals(1, definitions.getSize());
		definitions.add(definition);
		assertEquals(1, definitions.getSize());
	}

	@Test
	public void testAddAll() throws Exception {
		ArgumentDefinitionList definitions;
		ArgumentDefinition definition1;
		ArgumentDefinition definition2;
		LinkedList<ArgumentDefinition> definitionList;

		definition1 = new ArgumentDefinition("test-argument1", "a", String.class, StringArgumentParser.class,
				DefaultArgumentValidator.class, false, null, "This is a test argument 1.", new String[] {"Test example 1"});
		definition2 = new ArgumentDefinition("test-argument2", "b", String.class, StringArgumentParser.class,
				DefaultArgumentValidator.class, false, null, "This is a test argument 2.", new String[] {"Test example 2"});
		definitionList = new LinkedList<>();
		definitionList.add(definition1);
		definitionList.add(definition2);

		definitions = new ArgumentDefinitionList();
		definitions.addAll(definitionList);
		assertTrue(definitions.contains(definition1));
		assertTrue(definitions.contains(definition2));
	}

	@Test
	public void testAddAll_DoubleAdd() throws Exception {
		ArgumentDefinitionList definitions;
		ArgumentDefinition definition1;
		ArgumentDefinition definition2;
		LinkedList<ArgumentDefinition> definitionList;

		definition1 = new ArgumentDefinition("test-argument", "a", String.class, StringArgumentParser.class,
				DefaultArgumentValidator.class, false, null, "This is a test argument.", new String[] {"Test example"});
		definition2 = new ArgumentDefinition("test-argument", "a", String.class, StringArgumentParser.class,
				DefaultArgumentValidator.class, false, null, "This is a test argument.", new String[] {"Test example"});
		definitionList = new LinkedList<>();
		definitionList.add(definition1);
		definitionList.add(definition2);

		definitions = new ArgumentDefinitionList();
		assertTrue(definitions.isEmpty());
		definitions.addAll(definitionList);
		assertEquals(1, definitions.getSize());
		assertTrue(definitions.contains(definition2));
	}

	@Test
	public void testClear() throws Exception {
		ArgumentDefinitionList definitions;
		ArgumentDefinition definition;

		definition = ArgumentDefinition.createMock();
		definitions = new ArgumentDefinitionList();
		definitions.add(definition);
		assertFalse(definitions.isEmpty());
		definitions.clear();
		assertTrue(definitions.isEmpty());
	}

	@Test
	public void testContainsByDefinitionName_LongName() throws Exception {
		ArgumentDefinitionList definitions;
		ArgumentDefinition definition;
		String definitionName;

		definition = ArgumentDefinition.createMock();
		definitions = new ArgumentDefinitionList();
		definitionName = definition.getLongName();
		assertFalse(definitions.contains(definitionName));
		definitions.add(definition);
		assertTrue(definitions.contains(definitionName));
	}

	@Test
	public void testContainsByDefinitionName_ShortName() throws Exception {
		ArgumentDefinitionList definitions;
		ArgumentDefinition definition;
		String definitionName;

		definition = ArgumentDefinition.createMock();
		definitions = new ArgumentDefinitionList();
		definitionName = definition.getShortName();
		assertFalse(definitions.contains(definitionName));
		definitions.add(definition);
		assertTrue(definitions.contains(definitionName));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testContainsByDefinitionName_EmptyName() throws Exception {
		ArgumentDefinitionList definitions;

		definitions = new ArgumentDefinitionList();
		assertTrue(definitions.contains(" "));
	}

	@Test
	public void testContainsByDefinitionReference() throws Exception {
		ArgumentDefinitionList definitions;
		ArgumentDefinition definition;

		definition = ArgumentDefinition.createMock();
		definitions = new ArgumentDefinitionList();
		assertFalse(definitions.contains(definition));
		definitions.add(definition);
		assertTrue(definitions.contains(definition));
	}

	@Test
	public void testIsEmpty() throws Exception {
		ArgumentDefinitionList definitions;

		definitions = new ArgumentDefinitionList();
		assertTrue(definitions.isEmpty());
		definitions.add(ArgumentDefinition.createMock());
		assertFalse(definitions.isEmpty());
	}

	@Test
	public void testGetByShortName() throws Exception {
		ArgumentDefinitionList definitions;
		ArgumentDefinition definitionBefore;
		ArgumentDefinition definitionAfter;
		String name;

		definitionBefore = ArgumentDefinition.createMock();
		definitions = new ArgumentDefinitionList();
		name = definitionBefore.getShortName();
		assertNull(definitions.get(name));
		definitions.add(definitionBefore);
		definitionAfter = definitions.get(name);
		assertSame(definitionBefore, definitionAfter);
	}

	@Test
	public void testGetByLongName() throws Exception {
		ArgumentDefinitionList definitions;
		ArgumentDefinition definitionBefore;
		ArgumentDefinition definitionAfter;
		String name;

		definitionBefore = ArgumentDefinition.createMock();
		definitions = new ArgumentDefinitionList();
		name = definitionBefore.getLongName();
		assertNull(definitions.get(name));
		definitions.add(definitionBefore);
		definitionAfter = definitions.get(name);
		assertSame(definitionBefore, definitionAfter);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGet_EmptyName() throws Exception {
		ArgumentDefinitionList definitions;

		definitions = new ArgumentDefinitionList();
		definitions.get(" ");
	}

	@Test
	public void testGet_NonExistentDefinition() throws Exception {
		ArgumentDefinitionList definitions;

		definitions = new ArgumentDefinitionList();
		assertNull(definitions.get("non-existent-definition"));
	}

	@Test
	public void testGetSize() throws Exception {
		ArgumentDefinitionList definitions;

		definitions = new ArgumentDefinitionList();
		assertEquals(0, definitions.getSize());
		definitions.add(ArgumentDefinition.createMock());
		assertEquals(1, definitions.getSize());
	}

	@Test
	public void testGetCollection() throws Exception {
		ArgumentDefinitionList definitions;
		ArgumentDefinition definition1;
		ArgumentDefinition definition2;
		Collection<ArgumentDefinition> definitionList;

		definition1 = new ArgumentDefinition("test-argument1", "a", String.class, StringArgumentParser.class,
				DefaultArgumentValidator.class, false, null, "This is a test argument 1.", new String[] {"Test example 1"});
		definition2 = new ArgumentDefinition("test-argument2", "b", String.class, StringArgumentParser.class,
				DefaultArgumentValidator.class, false, null, "This is a test argument 2.", new String[] {"Test example 2"});
		definitions = new ArgumentDefinitionList();
		definitions.add(definition1);
		definitions.add(definition2);
		definitionList = definitions.getCollection();
		assertTrue(definitionList.contains(definition1));
		assertTrue(definitionList.contains(definition2));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testGetCollection_AddToUnmodifiableList() throws Exception {
		ArgumentDefinitionList definitions;
		Collection<ArgumentDefinition> definitionCollection;

		definitions = new ArgumentDefinitionList();
		definitionCollection = definitions.getCollection();
		definitionCollection.add(ArgumentDefinition.createMock());
	}

	@Test
	public void testRemoveByDefinitionName_LongName() throws Exception {
		ArgumentDefinitionList definitions;
		ArgumentDefinition definition;
		String definitionName;

		definition = ArgumentDefinition.createMock();
		definitions = new ArgumentDefinitionList();
		definitionName = definition.getLongName();
		assertNull(definitions.remove(definitionName));
		definitions.add(definition);
		assertSame(definition, definitions.remove(definitionName));
	}

	@Test
	public void testRemoveByDefinitionName_ShortName() throws Exception {
		ArgumentDefinitionList definitions;
		ArgumentDefinition definition;
		String definitionName;

		definition = ArgumentDefinition.createMock();
		definitions = new ArgumentDefinitionList();
		definitionName = definition.getShortName();
		assertNull(definitions.remove(definitionName));
		definitions.add(definition);
		assertSame(definition, definitions.remove(definitionName));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRemoveByDefinitionName_EmptyName() throws Exception {
		ArgumentDefinitionList definitions;

		definitions = new ArgumentDefinitionList();
		definitions.remove(" ");
	}

	@Test
	public void testRemoveByDefinitionName_NonExistentDefinition() throws Exception {
		ArgumentDefinitionList definitions;

		definitions = new ArgumentDefinitionList();
		assertNull(definitions.remove("non-existent-definition"));
	}

	@Test
	public void testRemoveByDefinitionReference() throws Exception {
		ArgumentDefinitionList definitions;
		ArgumentDefinition definition;

		definition = ArgumentDefinition.createMock();
		definitions = new ArgumentDefinitionList();
		assertNull(definitions.remove(definition));
		definitions.add(definition);
		assertSame(definition, definitions.remove(definition));
	}

	@Test
	public void testRemoveByDefinitionReference_NonExistentDefinition() throws Exception {
		ArgumentDefinitionList definitions;
		ArgumentDefinition definition;

		definition = ArgumentDefinition.createMock();
		definitions = new ArgumentDefinitionList();
		assertNull(definitions.remove(definition));
	}
}