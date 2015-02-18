package commandline.language.parser.specific.list;

import commandline.exception.ArgumentNullException;
import commandline.language.parser.ArgumentParseException;
import commandline.language.parser.ArgumentParser;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

/**
 * User: gno, Date: 18.02.2015 - 12:09
 */
public class ListArgumentParser<ListEntryType> extends ArgumentParser<List<ListEntryType>> {
	private final ArgumentParser<ListEntryType> argumentParser;

	public ListArgumentParser(ArgumentParser<ListEntryType> argumentParser) {
		super();
		if (argumentParser == null) {
			throw new ArgumentNullException();
		}
		this.argumentParser = argumentParser;
	}

	public ArgumentParser<ListEntryType> getArgumentParser() {
		return this.argumentParser;
	}

	@Override
	public boolean isCompatible(@NotNull Class<?> valueClass) {
		return valueClass.isAssignableFrom(List.class);
	}

	@NotNull
	@Override
	public List<ListEntryType> parse(@NotNull String value) throws ArgumentParseException {
		LinkedList<ListEntryType> list;
		String[] valueTokens;
		ListEntryType parsedValue;

		if (value == null) {
			throw new ArgumentNullException();
		}
		list = new LinkedList<>();
		valueTokens = value.trim().split(" ");
		for (String token : valueTokens) {
			parsedValue = getArgumentParser().parse(token);
			list.add(parsedValue);
		}

		return list;
	}
}
