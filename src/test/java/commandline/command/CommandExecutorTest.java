package commandline.command;

import commandline.language.GnuCommandLineLanguage;
import org.junit.Test;

/**
 * User: gno, Date: 30.07.13 - 15:31
 */
public class CommandExecutorTest {
	public CommandExecutorTest() {
		super();
	}

	@Test
	public void testExecute() throws Exception {
		CommandExecutor manager;
		CommandList commands;
		GnuCommandLineLanguage language;
		String cliCommand;
		String[] cliCommandTokens;

		commands = new CommandList();
		commands.add(new MockCommand());
		language = new GnuCommandLineLanguage();
		cliCommand = "test-command --test-key value";
		cliCommandTokens = cliCommand.split(" ");
		manager = new CommandExecutor(commands, language);
		manager.execute(cliCommandTokens);
	}

	@Test(expected = CommandLineException.class)
	public void testExecute_EmptyCommandList() throws Exception {
		CommandExecutor manager;
		CommandList commands;
		GnuCommandLineLanguage language;
		String cliCommand;
		String[] cliCommandTokens;

		commands = new CommandList();
		language = new GnuCommandLineLanguage();
		cliCommand = "test-command --test-key value";
		cliCommandTokens = cliCommand.split(" ");
		manager = new CommandExecutor(commands, language);
		manager.execute(cliCommandTokens);
	}

	@Test(expected = CommandLineException.class)
	public void testExecute_InvalidCommand() throws Exception {
		CommandExecutor manager;
		CommandList commands;
		GnuCommandLineLanguage language;
		String cliCommand;
		String[] cliCommandTokens;

		commands = new CommandList();
		commands.add(new MockCommand());
		language = new GnuCommandLineLanguage();
		cliCommand = "invalid-command --key value";
		cliCommandTokens = cliCommand.split(" ");
		manager = new CommandExecutor(commands, language);
		manager.execute(cliCommandTokens);
	}

	@Test(expected = CommandLineException.class)
	public void testExecute_SyntaxError() throws Exception {
		CommandExecutor manager;
		CommandList commands;
		GnuCommandLineLanguage language;
		String cliCommand;
		String[] cliCommandTokens;

		commands = new CommandList();
		commands.add(new MockCommand());
		language = new GnuCommandLineLanguage();
		cliCommand = "syntax-error-command --key";
		cliCommandTokens = cliCommand.split(" ");
		manager = new CommandExecutor(commands, language);
		manager.execute(cliCommandTokens);
	}
}
