package commandline.command;

import commandline.annotation.CliArgument;
import commandline.argument.Argument;
import commandline.argument.ArgumentDefinition;
import commandline.argument.ArgumentList;
import commandline.exception.ArgumentNullException;
import org.jetbrains.annotations.NotNull;

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

	public void inject(@NotNull Command sourceCommand, @NotNull Object destinationObject) {
		if (sourceCommand == null) {
			throw new ArgumentNullException();
		}
		if (destinationObject == null) {
			throw new ArgumentNullException();
		}
		inject(sourceCommand.getArgumentList(), destinationObject);
	}

	public void inject(@NotNull ArgumentList arguments, @NotNull Object destinationObject) {
		HashMap<String, Method> methodMap;
		HashMap<String, ArgumentDefinition> methodDefinitionMap;
		Method setterMethod;
		ArgumentDefinition extractedArgumentDefinition;
		ArgumentDefinition passedArgumentDefinition;
		CommandDefinitionReader definitionReader;
		CliArgument definitionAnnotation;
		String longName;

		if (arguments == null) {
			throw new ArgumentNullException();
		}
		if (destinationObject == null) {
			throw new ArgumentNullException();
		}

		/*
		 * Creates a map of the argument names and the corresponding setters in the command class. This map is used to retrieve
		 * the setter using the argument name.
		 *
		 * Creates a map of the argument names and the corresponding argument definitions extracted from the CliArgument annotation
		 * above the setters of the class. This map is used to retrieve the argument definition of a specific method using the
		 * argument name.
		 *
		 * This two maps are created to improve the performance of this method.
		 */
		methodMap = new HashMap<>();
		methodDefinitionMap = new HashMap<>();
		definitionReader = new CommandDefinitionReader();
		for (Method m : destinationObject.getClass().getMethods()) {
			definitionAnnotation = m.getAnnotation(CliArgument.class);
			if (definitionAnnotation != null) {
				longName = definitionAnnotation.longName();
				methodMap.put(longName, m);

				//Test implicitly that the method is a setter method.
				extractedArgumentDefinition = definitionReader.readArgumentDefinition(m);
				methodDefinitionMap.put(longName, extractedArgumentDefinition);
			}
		}

		for (Argument argument : arguments.getCollection()) {
			/*
			 * Retrieves the setter of the command class that corresponds to the argument name. If there is no setter for the
			 * argument name, the argument is skipped.
			 */
			passedArgumentDefinition = argument.getDefinition();
			longName = passedArgumentDefinition.getLongName();
			setterMethod = methodMap.get(longName);
			if (setterMethod == null) {
				continue;
			}
			/*
			 * Compares the argument definition of the passed argument with the argument definition in the annotation of the
			 * setter of the command class. The argument value is only injected into the command if the argument definitions
			 * match.
			 */
			extractedArgumentDefinition = methodDefinitionMap.get(longName);
			if (!passedArgumentDefinition.equals(extractedArgumentDefinition)) {
				throw new CommandLineException("The arguments couldn't be injected into the command, because the passed " +
						"argument \"" + longName + "\" has an argument definition that doesn't match with the argument " +
						"definition in the annotation of the setter of the command class.");
			}
			try {
				setterMethod.invoke(destinationObject, argument.getValue());
			} catch (IllegalAccessException | InvocationTargetException e) {
				throw new CommandLineException(e.getMessage(), e);
			}
		}
	}
}
