package commandline.language.parser;

import org.jetbrains.annotations.NotNull;

/**
 * User: gno, Date: 06.01.2015 - 16:01
 */
public interface GenericCommandParser {
	@NotNull
	commandline.command.GenericCommand parse(@NotNull String[] cliArguments);
}
