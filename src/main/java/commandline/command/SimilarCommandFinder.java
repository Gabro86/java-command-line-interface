package commandline.command;

import commandline.exception.ArgumentNullException;

import java.util.LinkedList;
import java.util.List;

/**
 * User: gno, Date: 25.02.2015 - 14:19
 */
public class SimilarCommandFinder {
	private final CommandDefinitionList definitionList;

	public SimilarCommandFinder(CommandDefinitionList definitionList) {
		super();
		if (definitionList == null) {
			throw new ArgumentNullException();
		}
		this.definitionList = definitionList;
	}

	public CommandDefinitionList getDefinitionList() {
		return this.definitionList;
	}

	public List<CommandDefinition> findSimilarCommands(String commandName) {
		List<CommandDefinition> similarDefinitions;
		int similarity;

		similarDefinitions = new LinkedList<>();
		for (CommandDefinition definition : getDefinitionList()) {
			similarity = calcLevenshteinDistance(definition.getName(), commandName);
			if (similarity <= 3) {
				similarDefinitions.add(definition);
			}
		}

		return similarDefinitions;
	}

	/**
	 * Source: http://rosettacode.org/wiki/Levenshtein_distance#Java.html
	 */
	public static int calcLevenshteinDistance(String a, String b) {
		a = a.toLowerCase();
		b = b.toLowerCase();
		// i == 0
		int[] costs = new int[b.length() + 1];
		for (int j = 0; j < costs.length; j++) {
			costs[j] = j;
		}
		for (int i = 1; i <= a.length(); i++) {
			// j == 0; nw = lev(i - 1, j)
			costs[0] = i;
			int nw = i - 1;
			for (int j = 1; j <= b.length(); j++) {
				int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
				nw = costs[j];
				costs[j] = cj;
			}
		}
		return costs[b.length()];
	}
}
