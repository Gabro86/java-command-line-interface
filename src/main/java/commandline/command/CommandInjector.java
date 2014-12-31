package commandline.command;

import commandline.argument.Argument;
import commandline.argument.ArgumentList;
import commandline.argument.metainfo.ArgumentMetaInfo;
import commandline.argument.metainfo.ArgumentMetaInfoExtractor;
import commandline.argument.metainfo.CommandArgument;
import commandline.exception.ArgumentNullException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * User: gno, Date: 02.08.13 - 11:03
 */
public class CommandInjector {
	public CommandInjector() {
		super();
	}

	public void inject(ArgumentList arguments, Object object) {
		HashMap<String, Method> methodMap;
		String longName;
		Method method;
		Object value;
		ArgumentMetaInfo extractedInfo;
		ArgumentMetaInfo passedInfo;
		ArgumentMetaInfoExtractor extractor;

		if (arguments == null) {
			throw new ArgumentNullException();
		}
		methodMap = createArgumentSettersMap(object.getClass());
		extractor = new ArgumentMetaInfoExtractor();
		for (Argument<?> argument : arguments.getArgumentList()) {
			longName = argument.getLongName();
			value = argument.getValue();
			passedInfo = argument.getMetaInfo();
			method = methodMap.get(longName);
			//When no matching setter for the current argument was found it will be skipped. This happens when the argument list
			//contains arguments for other commands
			if (method == null) {
				continue;
			}
			extractedInfo = extractor.extract(method);
			if (!passedInfo.equals(extractedInfo)) {
				throw new CommandLineException(String.format("The arguments couldn't be set into the command, " +
						"because the passed argument \"%s\" has meta infos that don't correspond with the meta infos of the " +
						"setter for the argument of the command class. Usually this means that it was attempted to set an " +
						"argument of the command by using the wrong argument instance.", longName));
			}
			try {
				method.invoke(object, value);
			} catch (IllegalAccessException | InvocationTargetException e) {
				throw new CommandLineException(e.getMessage(), e);
			}
		}
	}

	HashMap<String, Method> createArgumentSettersMap(Class<?> clazz) {
		HashMap<String, Method> methodMap;
		CommandArgument metaInfo;

		methodMap = new HashMap<>(30);
		for (Method method : clazz.getMethods()) {
			metaInfo = method.getAnnotation(CommandArgument.class);
			if (metaInfo == null) {
				continue;
			}
			methodMap.put(metaInfo.longName(), method);
		}

		return methodMap;
	}
}
