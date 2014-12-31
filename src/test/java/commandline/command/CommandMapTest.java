package commandline.command;

import org.junit.Test;

import java.util.Map;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;

/**
 * User: gno, Date: 30.07.13 - 16:32
 */
public class CommandMapTest {
	public CommandMapTest() {
		super();
	}

	@Test
	public void testGetCommandMap() throws Exception {
		CommandList commandList;
		Map<String, Command> commandMap;
		MockCommand command;

		commandList = new CommandList();
		command = new MockCommand();
		commandList.add(command);
		commandMap = commandList.getCommandMap();
		assertEquals(commandList.size(), commandMap.size());
		assertEquals(command, commandMap.get(MockCommand.COMMAND_NAME));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testGetCommandMap_ModifyReadonlyMap() throws Exception {
		CommandList commandList;
		Map<String, Command> commandMap;

		commandList = new CommandList();
		commandMap = commandList.getCommandMap();
		commandMap.put(MockCommand.COMMAND_NAME, new MockCommand());
	}

	@Test
	public void testGet() throws Exception {
		CommandList commands;
		MockCommand command;

		commands = new CommandList();
		assertNull(commands.get("test-command"));
		command = new MockCommand();
		commands.add(command);
		assertEquals(command, commands.get("test-command"));
	}

	@Test
	public void testAdd() throws Exception {
		CommandList commands;
		MockCommand command;

		commands = new CommandList();
		assertEquals(null, commands.get("test-command"));
		command = new MockCommand();
		commands.add(command);
		assertEquals(command, commands.get("test-command"));
	}

	@Test
	public void testRemoveCommandName() throws Exception {
		CommandList commands;
		MockCommand command;

		commands = new CommandList();
		assertEquals(true, commands.isEmpty());
		command = new MockCommand();
		commands.add(command);
		assertEquals(false, commands.isEmpty());
		commands.remove("test-command");
		assertEquals(true, commands.isEmpty());
	}

	@Test
	public void testRemoveCommandName_NonExistingCommand() throws Exception {
		CommandList commands;

		commands = new CommandList();
		assertEquals(true, commands.isEmpty());
		commands.remove("test-command");
		assertEquals(true, commands.isEmpty());
	}

	@Test
	public void testSize() throws Exception {
		CommandList commands;

		commands = new CommandList();
		assertEquals(0, commands.size());
		commands.add(new MockCommand());
		assertEquals(1, commands.size());
	}

	@Test
	public void testIsEmpty() throws Exception {
		CommandList commands;

		commands = new CommandList();
		assertEquals(true, commands.isEmpty());
		commands.add(new MockCommand());
		assertEquals(false, commands.isEmpty());
		commands.remove("test-command");
		assertEquals(true, commands.isEmpty());
	}

	@Test
	public void testContains() throws Exception {
		CommandList commands;

		commands = new CommandList();
		assertEquals(false, commands.contains("test-command"));
		commands.add(new MockCommand());
		assertEquals(true, commands.contains("test-command"));
	}

	@Test
	public void testClear() throws Exception {
		CommandList commands;

		commands = new CommandList();
		commands.add(new MockCommand());
		commands.clear();
		assertEquals(true, commands.isEmpty());
	}
}
