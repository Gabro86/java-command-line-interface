package commandline.language.parser.argument;

import commandline.exception.ArgumentNullException;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

/**
 * User: gno, Date: 09.07.13 - 10:46
 */
public class DoubleArgumentParser extends ArgumentParser<Double> {
	public DoubleArgumentParser() {
		super();
	}

	public boolean isValid(String value) {
		String regex;

		if (value == null) {
			throw new ArgumentNullException();
		}
		//Sources
		//http://docs.oracle.com/cd/E17802_01/j2se/j2se/1.5.0/jcp/beta1/apidiffs/java/lang/Double.html#valueOf(java.lang.String)
		//http://stackoverflow.com/questions/3133770/how-to-find-out-if-the-value-contained-in-a-string-is-double-or-not
		regex = "[\\x00-\\x20]*[+-]?(((((\\p{Digit}+)(\\.)?((\\p{Digit}+)?)([eE][+-]?(\\p{Digit}+))?)|(\\.((\\p{Digit}+))([eE][+-]?" +
				"(\\p{Digit}+))?)|(((0[xX](\\p{XDigit}+)(\\.)?)|(0[xX](\\p{XDigit}+)?(\\.)(\\p{XDigit}+)))[pP][+-]?" +
				"(\\p{Digit}+)))[fFdD]?))[\\x00-\\x20]*";

		return Pattern.matches(regex, value);
	}

	@Override
	public boolean isCompatible(Class<?> clazz) {
		return super.isCompatible(clazz) || clazz.equals(double.class);
	}

	@NotNull
	@Override
	public Double parse(@NotNull String value) {
		Double parsedValue;

		if (value == null) {
			throw new ArgumentNullException();
		}
		if (! isValid(value)) {
			throw new ArgumentParseException("");
		}
		try {
			parsedValue = Double.parseDouble(value);
		} catch (Exception e) {
			throw new ArgumentParseException(e.getMessage(), e);
		}

		return parsedValue;
	}

	@NotNull
	@Override
	public Class<Double> getValueType() {
		return Double.class;
	}
}
