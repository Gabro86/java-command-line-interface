package commandline.command;

import commandline.annotation.CliArgument;
import commandline.annotation.CliCommand;
import commandline.annotation.CommandLineAnnotationException;
import commandline.argument.ArgumentDefinition;
import commandline.exception.ArgumentNullException;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * User: gno Date: 25.06.13 Time: 11:42
 */
public class CommandDefinitionReader {
	public CommandDefinitionReader() {
		super();
	}

	public CommandDefinition readCommandDefinition(Class<?> annotatedClass, ExecutableCommand commandToExecute) {
		CommandDefinition commandDefinition;
		List<ArgumentDefinition> argumentDefinitions;
		CliCommand definitionAnnotation;

		if (annotatedClass == null) {
			throw new ArgumentNullException();
		}
		if (commandToExecute == null) {
			throw new ArgumentNullException();
		}
		definitionAnnotation = annotatedClass.getAnnotation(CliCommand.class);
		if (definitionAnnotation == null) {
			throw new CommandLineAnnotationException("The command definition could not been read, because the passed " +
					"class \"" + annotatedClass.getSimpleName() + "\" doesn't have the annotation " +
					CliCommand.class.getSimpleName());
		}
		argumentDefinitions = readArgumentDefinitions(annotatedClass);
		commandDefinition = new CommandDefinition(definitionAnnotation, commandToExecute);
		commandDefinition.addAllArgumentDefinitions(argumentDefinitions);

		return commandDefinition;
	}

	@NotNull
	public List<ArgumentDefinition> readArgumentDefinitions(@NotNull Class<?> clazz) {
		ArgumentDefinition argumentDefinition;
		CliArgument annotation;
		HashMap<String, ArgumentDefinition> longNameDefinitionMap;
		HashMap<String, ArgumentDefinition> shortNameDefinitionMap;
		String longName;
		String shortName;
		LinkedList<ArgumentDefinition> argumentDefinitions;

		if (clazz == null) {
			throw new ArgumentNullException();
		}

		/*
		 * The long and short name maps are used to test if the passed class defines multiple arguments with the same long and short
		 * name.
		 */
		longNameDefinitionMap = new HashMap<>();
		shortNameDefinitionMap = new HashMap<>();
		for (Method method : clazz.getMethods()) {
			/*
			 * Tests if the method has an CliArgument annotation. This test is necessary, because the method readArgumentDefinition
			 * throws an exception when the method doesn't have this annotation.
			 */
			annotation = method.getAnnotation(CliArgument.class);
			if (annotation == null) {
				continue;
			}

			//Reads the CliArgument annotation and converts it to an argument definition.
			argumentDefinition = readArgumentDefinition(method);

			/*
			 * Puts the argument definition into the definition maps, but only if the class doesn't define multiple arguments with
			 * the same long or short name.
			 */
			longName = argumentDefinition.getLongName();
			if (longNameDefinitionMap.containsKey(longName)) {
				throw new CommandLineException("The argument definition could not been extracted, " +
						"because the argument long name \"" + longName + "\" is used by multiple arguments.");
			}
			longNameDefinitionMap.put(longName, argumentDefinition);

			shortName = argumentDefinition.getShortName();
			if (shortNameDefinitionMap.containsKey(shortName)) {
				throw new CommandLineException("The argument definition could not been extracted, " +
						"because the argument short name \"" + shortName + "\" is used by multiple arguments.");
			}
			shortNameDefinitionMap.put(shortName, argumentDefinition);
		}
		argumentDefinitions = new LinkedList<>(longNameDefinitionMap.values());

		return argumentDefinitions;
	}

	@NotNull
	public ArgumentDefinition readArgumentDefinition(@NotNull Method method) {
		ArgumentDefinition definition;
		CliArgument annotation;
		Class<?> methodParameterClass;
		Class<?>[] parameters;

		if (method == null) {
			throw new ArgumentNullException();
		}
		parameters = method.getParameterTypes();
		if (parameters.length < 1 || parameters.length > 1) {
			throw new CommandLineException("The argument definition could not been extracted from the passed method, " +
					"because the method has an invalid number of parameters. A valid method has exactly one parameter and is " +
					"usually a setter method.");
		}
		methodParameterClass = parameters[0];
		if (methodParameterClass == null) {
			throw new CommandLineException("The argument definition could not been extracted from the passed method, " +
					"because the type of the method parameter could not been determined");
		}
		if (methodParameterClass.isPrimitive()) {
			methodParameterClass = wrapPrimitiveClass(methodParameterClass);
		}
		annotation = method.getAnnotation(CliArgument.class);
		definition = new ArgumentDefinition(annotation, methodParameterClass);

		return definition;
	}

	@NotNull
	Class<?> wrapPrimitiveClass(Class<?> type) {
		if (type == null) {
			throw new ArgumentNullException();
		}
		if (type.equals(short.class)) {
			return Short.class;
		} else if (type.equals(int.class)) {
			return Integer.class;
		} else if (type.equals(long.class)) {
			return Long.class;
		} else if (type.equals(char.class)) {
			return Character.class;
		} else if (type.equals(boolean.class)) {
			return Boolean.class;
		} else if (type.equals(float.class)) {
			return Float.class;
		} else if (type.equals(double.class)) {
			return Double.class;
		} else {
			throw new IllegalArgumentException("The type could not been wrapped, because it's not a primitive type.");
		}
	}
}
