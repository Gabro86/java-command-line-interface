package commandline.argument.metainfo;

import commandline.command.CommandLineException;
import commandline.exception.ArgumentNullException;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * User: gno Date: 25.06.13 Time: 11:42
 */
public class ArgumentMetaInfoExtractor {
	public ArgumentMetaInfoExtractor() {
		super();
	}

	@NotNull
	public List<ArgumentMetaInfo> extract(@NotNull Class<?> clazz) {
		List<ArgumentMetaInfo> infos;
		ArgumentMetaInfo info;
		CommandArgument commandAnnotation;
		HashMap<String, ArgumentMetaInfo> infoMap;

		if (clazz == null) {
			throw new ArgumentNullException();
		}
		infoMap = new HashMap<>();
		for (Method method : clazz.getMethods()) {
			commandAnnotation = method.getAnnotation(CommandArgument.class);
			if (commandAnnotation == null) {
				continue;
			}
			info = extract(method);
			if (infoMap.containsKey(info.getLongName())) {
				throw new CommandLineException(String.format("The argument meta infos could not been extracted, " +
						"because the command name \"%s\" is used by multiple arguments.", info.getLongName()));
			}
			infoMap.put(info.getLongName(), info);
		}
		infos = new LinkedList<>();
		infos.addAll(infoMap.values());

		//Tests if short or long arguments names were used on multiple arguments. Argument name must be unique.
		for (ArgumentMetaInfo infoA : infos) {
			for (ArgumentMetaInfo infoB : infos) {
				if (infoA == infoB) {
					continue;
				}
				if (infoA.getLongName().equals(infoB.getLongName())) {
					throw new CommandLineException(String.format("The argument meta infos could not been extracted, " +
							"because the command name \"%s\" is used by multiple arguments.", infoA.getLongName()));
				}
				if (infoA.getShortName().equals(infoB.getShortName())) {
					throw new CommandLineException(String.format("The argument meta infos could not been extracted, " +
							"because the command name \"" + infoA.getLongName() + "\" and \"" + infoB.getLongName() + "\" have " +
							"the same short command name \"%s\"", infoA.getShortName()));
				}
			}
		}

		return infos;
	}

	@NotNull
	public ArgumentMetaInfo extract(@NotNull Method method) {
		ArgumentMetaInfo extractedInfo;
		CommandArgument annotation;
		Class<?> setterType;
		Class<?>[] parameters;

		if (method == null) {
			throw new ArgumentNullException();
		}
		parameters = method.getParameterTypes();
		if (parameters.length < 1 || parameters.length > 1) {
			throw new CommandLineException("The argument meta infos could not been extracted from the passed method, " +
					"because the method has an invalid number of parameters. A valid method has exactly one parameter and is " +
					"usually a setter method.");
		}
		setterType = parameters[0];
		if (setterType == null) {
			throw new CommandLineException("The argument meta infos could not been extracted from the passed method, " +
					"because the type of the method parameter could not been determined");
		}
		if (setterType.isPrimitive()) {
			setterType = wrapType(setterType);
		}
		annotation = method.getAnnotation(CommandArgument.class);
		extractedInfo = new ArgumentMetaInfo(annotation, setterType);

		return extractedInfo;
	}

	private Class<?> wrapType(Class<?> type) {
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
