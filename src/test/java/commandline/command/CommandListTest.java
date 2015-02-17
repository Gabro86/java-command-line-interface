package commandline.command;

import org.junit.Test;

import java.util.Collection;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * User: gno, Date: 30.07.13 - 16:32
 */
public class CommandListTest {
	public CommandListTest() {
		super();
	}

	@Test
	public void testAdd() throws Exception {
		CommandList commands;
		Command command;

		command = Command.createMock();
		commands = new CommandList();
		assertSame(null, commands.get(command.getName()));
		commands.add(command);
		assertSame(command, commands.get(command.getName()));
	}

	@Test
	public void testAdd_DoubleAdd() throws Exception {
		CommandList commands;
		Command command;

		command = Command.createMock();
		commands = new CommandList();
		commands.add(command);
		assertEquals(1, commands.getSize());
		commands.add(command);
		assertEquals(1, commands.getSize());
	}

	@Test
	public void testAddAll() throws Exception {
		CommandList commands;
		Command command1;
		Command command2;
		LinkedList<Command> commandList;

		command1 = new Command("command1", "description1", new MockExecutableCommand());
		command2 = new Command("command2", "description2", new MockExecutableCommand());
		commandList = new LinkedList<>();
		commandList.add(command1);
		commandList.add(command2);

		commands = new CommandList();
		assertTrue(commands.isEmpty());
		commands.addAll(commandList);
		assertEquals(commandList, new LinkedList<>(commands.getCollection()));
	}

	@Test
	public void testAddAll_DuplicateCommand() throws Exception {
		CommandList commands;
		Command command;
		LinkedList<Command> commandList;

		command = new Command("command", "description", new MockExecutableCommand());
		commandList = new LinkedList<>();
		commandList.add(command);
		commandList.add(command);

		commands = new CommandList();
		assertTrue(commands.isEmpty());
		commands.addAll(commandList);
		assertEquals(1, commands.getSize());
		assertSame(command, commands.get(command.getName()));
	}

	@Test
	public void testClear() throws Exception {
		CommandList commands;
		Command command;

		command = Command.createMock();
		commands = new CommandList();
		assertTrue(commands.isEmpty());
		commands.add(command);
		assertFalse(commands.isEmpty());
		commands.clear();
		assertTrue(commands.isEmpty());
	}

	@Test
	public void testContainsByCommandName() throws Exception {
		CommandList commands;
		Command command;

		command = Command.createMock();
		commands = new CommandList();
		assertFalse(commands.contains(command.getName()));
		commands.add(command);
		assertTrue(commands.contains(command.getName()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testContainsByCommandName_EmptyName() throws Exception {
		CommandList commands;

		commands = new CommandList();
		commands.contains(" ");
	}

	@Test
	public void testContainsByCommandReference() throws Exception {
		CommandList commands;
		Command command;

		command = Command.createMock();
		commands = new CommandList();
		assertFalse(commands.contains(command));
		commands.add(command);
		assertTrue(commands.contains(command));
	}

	@Test
	public void testGet() throws Exception {
		CommandList commands;
		Command command;

		command = Command.createMock();
		commands = new CommandList();
		assertNull(commands.get(command.getName()));
		commands.add(command);
		assertSame(command, commands.get(command.getName()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGet_EmptyName() throws Exception {
		CommandList commands;

		commands = new CommandList();
		commands.get(" ");
	}

	@Test
	public void testGet_NonExistentCommand() throws Exception {
		CommandList commands;

		commands = new CommandList();
		assertSame(null, commands.get("non-existent-command"));
	}

	@Test
	public void testGetCollection() throws Exception {
		CommandList commands;
		Collection<Command> unmodifiableCommands;
		Command command;

		command = Command.createMock();
		commands = new CommandList();
		commands.add(command);
		unmodifiableCommands = commands.getCollection();
		assertEquals(commands.getSize(), unmodifiableCommands.size());
		assertSame(command, commands.get(command.getName()));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testGetCollection_AddToUnmodifiableList() throws Exception {
		CommandList commands;
		Collection<Command> unmodifiableArguments;

		commands = new CommandList();
		unmodifiableArguments = commands.getCollection();
		unmodifiableArguments.add(Command.createMock());
	}

	@Test
	public void testGetSize() throws Exception {
		CommandList commands;

		commands = new CommandList();
		assertEquals(0, commands.getSize());
		commands.add(Command.createMock());
		assertEquals(1, commands.getSize());
	}

	@Test
	public void testIsEmpty() throws Exception {
		CommandList commands;
		Command command;

		commands = new CommandList();
		assertTrue(commands.isEmpty());
		command = Command.createMock();
		commands.add(command);
		assertFalse(commands.isEmpty());
		commands.remove(command.getName());
		assertTrue(commands.isEmpty());
	}

	@Test
	public void testRemoveByCommandName() throws Exception {
		CommandList commands;
		Command command;

		commands = new CommandList();
		assertTrue(commands.isEmpty());
		command = Command.createMock();
		commands.add(command);
		assertFalse(commands.isEmpty());
		assertSame(command, commands.remove(command.getName()));
		assertTrue(commands.isEmpty());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRemoveByCommandName_EmptyName() throws Exception {
		CommandList commands;

		commands = new CommandList();
		commands.remove(" ");
	}

	@Test
	public void testRemoveByCommandName_NonExistentCommand() throws Exception {
		CommandList commands;

		commands = new CommandList();
		assertNull(commands.remove("command"));
	}

	@Test
	public void testRemoveByCommandReference() throws Exception {
		CommandList commands;
		Command command;

		commands = new CommandList();
		assertTrue(commands.isEmpty());
		command = Command.createMock();
		commands.add(command);
		assertFalse(commands.isEmpty());
		assertSame(command, commands.remove(command));
		assertTrue(commands.isEmpty());
	}

	@Test
	public void testRemoveByCommandReference_NonExistentCommand() throws Exception {
		CommandList commands;
		Command command;

		commands = new CommandList();
		command = Command.createMock();
		assertNull(commands.remove(command));
	}
}