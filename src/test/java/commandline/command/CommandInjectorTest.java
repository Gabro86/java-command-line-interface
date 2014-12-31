package commandline.command;

import commandline.argument.Argument;
import commandline.argument.ArgumentList;
import commandline.argument.metainfo.ArgumentMetaInfo;
import commandline.argument.metainfo.ArgumentMetaInfoExtractor;
import commandline.argument.validator.DefaultArgumentValidator;
import commandline.language.parser.argument.IntegerArgumentParser;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * User: gno, Date: 02.08.13 - 11:21
 */
public class CommandInjectorTest {
	public CommandInjectorTest() {
		super();
	}

	@Test
	public void testInject() throws Exception {
		MockCommand command;
		ArgumentList arguments;
		Argument<?> argument;
		List<ArgumentMetaInfo> infos;
		ArgumentMetaInfo info;
		String value;
		ArgumentMetaInfoExtractor extractor;
		CommandInjector injector;

		//Extracts the meta info for the only argument in TestCommand, because it's needed to create an argument instance
		extractor = new ArgumentMetaInfoExtractor();
		infos = extractor.extract(MockCommand.class);
		assertEquals(1, infos.size());
		info = infos.get(0);

		//Creates the argument list with the only argument of TestCommand with the argument value
		value = "value1";
		argument = new Argument<>(info, value);
		arguments = new ArgumentList();
		arguments.put(argument);

		command = new MockCommand();
		injector = new CommandInjector();
		injector.inject(arguments, command);
		assertEquals("value1", command.getTestValue());
	}

	@Test(expected = CommandLineException.class)
	public void testInject_WrongArgument() throws Exception {
		MockCommand command;
		ArgumentList arguments;
		Argument<?> argument;
		ArgumentMetaInfo info;
		CommandInjector injector;
		int value;

		info = new ArgumentMetaInfo(MockCommand.LONG_NAME, MockCommand.SHORT_NAME, Integer.class, IntegerArgumentParser.class,
				DefaultArgumentValidator.class, true, null, "description", new String[] {"example"});
		value = 100;
		argument = new Argument<>(info, value);
		arguments = new ArgumentList();
		arguments.put(argument);

		command = new MockCommand();
		injector = new CommandInjector();
		injector.inject(arguments, command);
		assertEquals("value1", command.getTestValue());
	}

	@Test
	public void testCreateArgumentSettersMap() throws Exception {
		HashMap<String, Method> map;
		CommandInjector injector;

		injector = new CommandInjector();
		map = injector.createArgumentSettersMap(MockCommand.class);
		assertEquals("setTestField", map.get("test-key").getName());
	}
}
