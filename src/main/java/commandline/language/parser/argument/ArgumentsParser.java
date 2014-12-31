package commandline.language.parser.argument;

import commandline.argument.ArgumentList;
import commandline.argument.metainfo.ArgumentMetaInfo;

import java.util.List;

/**
 * User: gno, Date: 09.07.13 - 14:19
 */
public interface ArgumentsParser {
	ArgumentList parse(String[] cliArguments, List<ArgumentMetaInfo> metaInfos);
}
