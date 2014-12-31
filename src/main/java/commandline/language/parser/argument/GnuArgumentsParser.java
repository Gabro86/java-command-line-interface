package commandline.language.parser.argument;

import commandline.argument.Argument;
import commandline.argument.ArgumentList;
import commandline.argument.metainfo.ArgumentMetaInfo;
import commandline.command.CommandLineException;
import commandline.language.syntax.SyntaxException;
import commandline.exception.ArgumentNullException;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

/**
 * User: gno, Date: 09.07.13 - 13:53
 */
public class GnuArgumentsParser implements ArgumentsParser {
	public GnuArgumentsParser() {
		super();
	}

	//	@NotNull
	//	@Override
	//	public ArgumentList parse(@NotNull String[] cliArguments, @NotNull List<ArgumentMetaInfo> metaInfos) {
	//		String name;
	//		Argument<?> argument;
	//		ArgumentMetaInfo info;
	//		LinkedList<String> argumentList;
	//		ArgumentList parsedArguments;
	//		HashMap<String, ArgumentMetaInfo> shortLongNameMetaInfos;
	//
	//		/*
	//		 * This method is not responsible for validating the syntax of the cli arguments. That's the job of the SyntaxValidator
	//		 * classes. This method assumes that the passed cli arguments are valid.
	//		 */
	//
	//		if (cliArguments == null) {
	//			throw new ArgumentNullException();
	//		}
	//		if (cliArguments.length < 2) {
	//			return new ArgumentList();
	//		}
	//		//metaInfos can be empty, when command don't have arguments
	//		if (metaInfos == null) {
	//			throw new ArgumentNullException();
	//		}
	//
	//		/*
	//		* Creates a meta info map with mappings of the short and long names of the arguments for faster search. The short name
	// keys
	//		* do not override the long name keys, because the short names have a length of exactly 1 char and the long names must
	//		* contain more than 1 char. In other words the short name and long names set are disjoint.
	//		 */
	//		shortLongNameMetaInfos = new HashMap<>();
	//		for (ArgumentMetaInfo i : metaInfos) {
	//			shortLongNameMetaInfos.put(i.getShortName(), i);
	//			shortLongNameMetaInfos.put(i.getLongName(), i);
	//		}
	//
	//		//Creates a list of the passed arguments for removing the command name.
	//		argumentList = new LinkedList<>();
	//		Collections.addAll(argumentList, cliArguments);
	//		argumentList.remove(0);
	//
	//		name = null;
	//		parsedArguments = new ArgumentList();
	//		for (String token : argumentList) {
	//			//Checks if the token represents a argument name
	//			token = token.trim();
	//			if (token.startsWith("-") || token.startsWith("--")) {
	//				name = removePrefix(token);
	//				if (name.isEmpty()) {
	//					continue;
	//				}
	//				continue;
	//			}
	//			//This token represents a value
	//			//This value is ignored, when it does not have an argument name.
	//			if (name == null) {
	//				continue;
	//			}
	//			info = shortLongNameMetaInfos.get(name);
	//			if (info == null) {
	//				throw new CommandLineException(String.format(
	//						"The cli arguments could not been parsed, because the argument name \"%s\" is invalid.", name));
	//			}
	//			try {
	//				argument = Argument.parse(info, token);
	//			} catch (ArgumentParseException e) {
	//				throw new CommandLineException(String.format("The argument \"%s\" with the value \"%s\" could not been parsed.
	// %s",
	//						name, token, e.getMessage()), e);
	//			}
	//			parsedArguments.put(argument);
	//			//Only one value per name is allowed. That's why the name is reset.
	//			name = null;
	//		}
	//
	//		return parsedArguments;
	//	}

	@NotNull
	@Override
	public ArgumentList parse(@NotNull String[] cliArguments, @NotNull List<ArgumentMetaInfo> metaInfos) {
		Argument<?> argument;
		ArgumentMetaInfo metaInfo;
		ArgumentList parsedArguments;
		HashMap<String, String> argumentMap;
		HashMap<String, ArgumentMetaInfo> shortNameMetaInfoMap;
		HashMap<String, ArgumentMetaInfo> longNameMetaInfoMap;
		String value;
		String name;

		/*
		 * This method is not responsible for validating the syntax of the cli arguments. That's the job of the SyntaxValidator
		 * classes. This method assumes that the passed cli arguments are valid.
		 */

		if (cliArguments == null) {
			throw new ArgumentNullException();
		}
		//metaInfos can be empty, but not null when the command doesn't have arguments
		if (metaInfos == null) {
			throw new ArgumentNullException();
		}

		//Creates a map with argument name and value pairs and removes the command name. This is map is created for faster search.
		argumentMap = new HashMap<>();
		for (int i = 1; i < cliArguments.length - 1; i += 2) {
			try {
				name = removePrefix(cliArguments[i]);
			} catch (SyntaxException e) {
				throw new CommandLineException(String.format(
						"The arguments could not been parsed, because the argument \"%s\" does not start with an \"-\" or " +
								"\"--\"",
						cliArguments[i]), e);
			}
			value = cliArguments[i + 1];
			argumentMap.put(name, value);
		}

		//Creates the maps for the argument meta infos for faster search
		shortNameMetaInfoMap = new HashMap<>();
		longNameMetaInfoMap = new HashMap<>();
		for (ArgumentMetaInfo i : metaInfos) {
			shortNameMetaInfoMap.put(i.getShortName(), i);
			longNameMetaInfoMap.put(i.getLongName(), i);
		}

		parsedArguments = new ArgumentList();
		for (ArgumentMetaInfo i : metaInfos) {
			value = argumentMap.get(i.getLongName());
			if (value == null) {
				value = argumentMap.get(i.getShortName());
			}
			//Creates an argument. Even optional arguments are created. This way the argument list will contain every argument
			//defined for the command.
			if (value == null) {
				if (i.isObligatory()) {
					throw new CommandLineException(String.format(
							"The arguments could not been parsed, because the obligatory argument with the long name \"%s\" and " +
									"short name \"%s\" is missing or has no value.", i.getLongName(), i.getShortName()));
				}
			} else {
				value = value.trim();
			}
			try {
				argument = Argument.parse(i, value);
			} catch (ArgumentParseException e) {
				throw new CommandLineException(String.format(
						"The arguments could not been parsed, because the argument \"%s\" with the value \"%s\" could not been " +
								"parsed. %s", i.getLongName(), value, e.getMessage()), e);
			}
			parsedArguments.put(argument);
		}

		//Test if the passed cli arguments contain arguments that are not defined by the command.
		for (String argumentName : argumentMap.keySet()) {
			metaInfo = shortNameMetaInfoMap.get(argumentName);
			if (metaInfo == null) {
				metaInfo = longNameMetaInfoMap.get(argumentName);
			}
			if (metaInfo == null) {
				throw new CommandLineException(String.format(
						"The arguments could not been parsed, because the passed argument \"%s\" is not defined for the command.",
						argumentName));
			}
		}

		return parsedArguments;
	}

	@NotNull
	String removePrefix(@NotNull String value) {
		String editValue;

		if (value == null) {
			throw new ArgumentNullException();
		}
		if (value.startsWith("--")) {
			editValue = value.substring(2);
		} else if (value.startsWith("-")) {
			editValue = value.substring(1);
		} else {
			throw new SyntaxException(
					"The prefix could not been removed, because the passed value does not start with an \"-\" or \"--\"");
		}

		return editValue;
	}
}
