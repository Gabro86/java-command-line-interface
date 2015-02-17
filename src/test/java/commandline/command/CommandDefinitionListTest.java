package commandline.command;

import org.junit.Test;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class CommandDefinitionListTest {
	public CommandDefinitionListTest() {
		super();
	}

	@Test
	public void testAdd() throws Exception {
		CommandDefinitionList definitions;
		CommandDefinition definition;

		definition = CommandDefinition.createMock();
		definitions = new CommandDefinitionList();
		assertFalse(definitions.contains(definition));
		definitions.add(definition);
		assertTrue(definitions.contains(definition));
	}

	@Test
	public void testAdd_DoubleAdd() throws Exception {
		CommandDefinitionList definitions;
		CommandDefinition definition;

		definition = CommandDefinition.createMock();
		definitions = new CommandDefinitionList();
		assertEquals(0, definitions.getSize());
		definitions.add(definition);
		assertEquals(1, definitions.getSize());
		definitions.add(definition);
		assertEquals(1, definitions.getSize());
	}

	@Test
	public void testAddAll() throws Exception {
		CommandDefinitionList definitions;
		CommandDefinition definition1;
		CommandDefinition definition2;
		LinkedList<CommandDefinition> definitionList;

		definition1 = new CommandDefinition("command1", "description1", new MockExecutableCommand());
		definition2 = new CommandDefinition("command2", "description2", new MockExecutableCommand());
		definitionList = new LinkedList<>();
		definitionList.add(definition1);
		definitionList.add(definition2);

		definitions = new CommandDefinitionList();
		definitions.addAll(definitionList);
		assertEquals(definitionList, new LinkedList<>(definitions.getCollection()));
	}

	@Test
	public void testAddAll_DuplicateDefinition() throws Exception {
		CommandDefinitionList definitions;
		CommandDefinition definition;
		LinkedList<CommandDefinition> definitionList;

		definition = new CommandDefinition("command", "description", new MockExecutableCommand());
		definitionList = new LinkedList<>();
		definitionList.add(definition);
		definitionList.add(definition);

		definitions = new CommandDefinitionList();
		definitions.addAll(definitionList);
		assertEquals(1, definitions.getSize());
		assertEquals(definition, definitions.get(definition.getName()));
	}

	@Test
	public void testClear() throws Exception {
		CommandDefinitionList definitions;
		CommandDefinition definition;

		definition = CommandDefinition.createMock();
		definitions = new CommandDefinitionList();
		definitions.add(definition);
		assertFalse(definitions.isEmpty());
		definitions.clear();
		assertTrue(definitions.isEmpty());
	}

	@Test
	public void testContainsByDefinitionName() throws Exception {
		CommandDefinitionList definitions;
		CommandDefinition definition;
		String definitionName;

		definition = CommandDefinition.createMock();
		definitions = new CommandDefinitionList();
		definitionName = definition.getName();
		assertFalse(definitions.contains(definitionName));
		definitions.add(definition);
		assertTrue(definitions.contains(definitionName));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testContainsByDefinitionName_EmptyName() throws Exception {
		CommandDefinitionList definitions;

		definitions = new CommandDefinitionList();
		definitions.contains(" ");
	}

	@Test
	public void testContainsByDefinitionReference() throws Exception {
		CommandDefinitionList definitions;
		CommandDefinition definition;

		definition = CommandDefinition.createMock();
		definitions = new CommandDefinitionList();
		assertFalse(definitions.contains(definition));
		definitions.add(definition);
		assertTrue(definitions.contains(definition));
	}

	@Test
	public void testGet() throws Exception {
		CommandDefinitionList definitions;
		CommandDefinition definitionBefore;
		CommandDefinition definitionAfter;

		definitionBefore = CommandDefinition.createMock();
		definitions = new CommandDefinitionList();
		assertNull(definitions.get(definitionBefore.getName()));
		definitions.add(definitionBefore);
		definitionAfter = definitions.get(definitionBefore.getName());
		assertSame(definitionBefore, definitionAfter);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGet_EmptyName() throws Exception {
		CommandDefinitionList definitions;

		definitions = new CommandDefinitionList();
		definitions.get(" ");
	}

	@Test
	public void testGet_NonExistentDefinition() throws Exception {
		CommandDefinitionList definitions;

		definitions = new CommandDefinitionList();
		assertNull(definitions.get("non-existent-definition"));
	}

	@Test
	public void testGetCollection() throws Exception {
		CommandDefinitionList definitions;
		CommandDefinition definition1;
		CommandDefinition definition2;
		Collection<CommandDefinition> definitionList;

		definition1 = new CommandDefinition("command1", "description1", new MockExecutableCommand());
		definition2 = new CommandDefinition("command2", "description2", new MockExecutableCommand());
		definitions = new CommandDefinitionList();
		definitions.add(definition1);
		definitions.add(definition2);
		definitionList = definitions.getCollection();
		assertTrue(definitionList.contains(definition1));
		assertTrue(definitionList.contains(definition2));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testGetCollection_AddToUnmodifiableList() throws Exception {
		CommandDefinitionList definitions;
		Collection<CommandDefinition> definitionCollection;

		definitions = new CommandDefinitionList();
		definitionCollection = definitions.getCollection();
		definitionCollection.add(CommandDefinition.createMock());
	}

	@Test
	public void testGetMap() throws Exception {
		CommandDefinitionList definitions;
		CommandDefinition definition1;
		CommandDefinition definition2;
		Map<String, CommandDefinition> definitionMap;

		definition1 = new CommandDefinition("command1", "description1", new MockExecutableCommand());
		definition2 = new CommandDefinition("command2", "description2", new MockExecutableCommand());
		definitions = new CommandDefinitionList();
		definitions.add(definition1);
		definitions.add(definition2);
		definitionMap = definitions.getMap();
		assertTrue(definitionMap.containsValue(definition1));
		assertTrue(definitionMap.containsValue(definition2));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testGetMap_AddToUnmodifiableList() throws Exception {
		CommandDefinitionList definitions;
		Map<String, CommandDefinition> definitionMap;
		CommandDefinition definition;

		definition = CommandDefinition.createMock();
		definitions = new CommandDefinitionList();
		definitionMap = definitions.getMap();
		definitionMap.put(definition.getName(), definition);
	}

	@Test
	public void testGetSize() throws Exception {
		CommandDefinitionList definitions;

		definitions = new CommandDefinitionList();
		assertEquals(0, definitions.getSize());
		definitions.add(CommandDefinition.createMock());
		assertEquals(1, definitions.getSize());
	}

	@Test
	public void testIsEmpty() throws Exception {
		CommandDefinitionList definitions;

		definitions = new CommandDefinitionList();
		assertTrue(definitions.isEmpty());
		definitions.add(CommandDefinition.createMock());
		assertFalse(definitions.isEmpty());
	}

	@Test
	public void testRemoveByDefinitionName() throws Exception {
		CommandDefinitionList definitions;
		CommandDefinition definition;
		String definitionName;

		definition = CommandDefinition.createMock();
		definitions = new CommandDefinitionList();
		definitionName = definition.getName();
		assertNull(definitions.remove(definitionName));
		definitions.add(definition);
		assertSame(definition, definitions.remove(definitionName));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRemoveByDefinitionName_EmptyName() throws Exception {
		CommandDefinitionList definitions;

		definitions = new CommandDefinitionList();
		definitions.remove(" ");
	}

	@Test
	public void testRemoveByDefinitionName_NonExistentDefinition() throws Exception {
		CommandDefinitionList definitions;

		definitions = new CommandDefinitionList();
		assertNull(definitions.remove("non-existent-definition"));
	}

	@Test
	public void testRemoveByDefinitionReference() throws Exception {
		CommandDefinitionList definitions;
		CommandDefinition definition;

		definition = CommandDefinition.createMock();
		definitions = new CommandDefinitionList();
		assertNull(definitions.remove(definition));
		definitions.add(definition);
		assertSame(definition, definitions.remove(definition));
	}

	@Test
	public void testRemoveByDefinitionReference_NonExistentDefinition() throws Exception {
		CommandDefinitionList definitions;
		CommandDefinition definition;

		definition = CommandDefinition.createMock();
		definitions = new CommandDefinitionList();
		assertNull(definitions.remove(definition));
	}
}