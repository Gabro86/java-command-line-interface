package commandline.command;

import commandline.language.CommandLineLanguage;
import commandline.language.GnuCommandLineLanguage;
import org.junit.Test;

/**
 * User: gno Date: 28.06.13 Time: 10:32
 */
public class MockCommandTest {
	@Test
	public void testExecute() throws Exception {
		String[] arguments;
		CommandExecutor executor;
		CommandList commands;
		CommandLineLanguage language;
		Command command;

		command = new MockCommand();
		commands = new CommandList();
		commands.add(command);
		language = new GnuCommandLineLanguage();
		arguments = new String[] {"test-command", "--test-key", "mytestvalue"};
		executor = new CommandExecutor(commands, language);
		executor.execute(arguments);
	}
}
