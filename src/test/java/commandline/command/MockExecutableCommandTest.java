package commandline.command;

import commandline.language.CommandLineLanguage;
import commandline.language.gnu.GnuCommandLineLanguage;
import org.junit.Test;

import java.util.LinkedList;

/**
 * User: gno Date: 28.06.13 Time: 10:32
 */
public class MockExecutableCommandTest {
	@Test
	public void testExecute() throws Exception {
		String[] arguments;
		CommandExecutor executor;
		LinkedList<ExecutableCommand> commands;
		CommandLineLanguage language;
		ExecutableCommand command;

		command = new MockExecutableCommand();
		commands = new LinkedList<>();
		commands.add(command);
		language = new GnuCommandLineLanguage();
		arguments = new String[] {"test-command", "--test-key", "mytestvalue"};
		executor = new CommandExecutor(language);
		executor.execute(arguments, commands);
	}
}
