package commandline.annotation;

import commandline.argument.validator.ArgumentValidator;
import commandline.argument.validator.DefaultArgumentValidator;
import commandline.language.parser.ArgumentParser;
import commandline.language.parser.MockArgumentParser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used on setter methods. A setter method marked with this annotation is called when the corresponding argument was
 * passed throw the command line interface.
 * @author gno
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CliArgument {
	/**
	 * Null values are not allowed in annotations. To solve this problem a unique string is used that represents the null value.
	 */
	String nullValue = "null-value-4bc850d9f16c4db588333495b089d04a70bdc00a4abc462a877278762a4e3b2d";
	/**
	 * Defines the short name of the command line argument. The short name is exactly one character long. It can be used by the user
	 * instead of the long name. This saves time when entering the command. <p>This field is optional.</p>
	 * @return Returns the short name of the command line argument
	 */
	String shortName() default nullValue;
	/**
	 * Defines the long name of the command line argument. <p>This field is obligatory.</p>
	 * @return Returns the long name of the command line argument.
	 */
	String longName();
	/**
	 * Defines if the command line argument is obligatory or optional. If the argument is optional the user doesn't need to pass it to
	 * the command line and a default value will be used. <p>This annotation field is optional. If this annotation field is not set
	 * the
	 * argument is automatically marked as obligatory.</p>
	 * @return Returns true when the command line argument is obligatory and false when it's optional.
	 */
	boolean obligatory() default true;
	/**
	 * Defines the default value to use when the command line argument is optional and the user didn't pass a value for this argument.
	 * If the command line argument is marked as optional and the default value is not set, then null will be used as default value .
	 * If the command line argument is marked as obligatory the default value cannot be set.<p>This field is optional.</p>
	 * @return Returns the default value or a string representing the null value.
	 */
	String defaultValue() default nullValue;
	/**
	 * Defines the parser class to use to parse the string argument value passed by the user. The parser parses the string value and
	 * converts	it into the type needed by the setter method. If no parser class is set, then default parsers are used. There is a
	 * default parser for all primitive types and the corresponding Wrappers and also for {@link java.io.File} and {@link
	 * java.net.URL}. <p>This field is optional.</p>
	 * @return Returns the parser class to use to parse the command line argument value.
	 */
	Class<? extends ArgumentParser<?>> parser() default MockArgumentParser.class;
	/**
	 * Defines the validator class to use to validate the argument value passed by the user. <p>This field is optional.</p>
	 * @return Returns the validator class to use to validate the command line argument value.
	 */
	Class<? extends ArgumentValidator<?>> validator() default DefaultArgumentValidator.class;
	/**
	 * Defines the description to show in the help of the command line interface.<p>This field is obligatory, because the help is
	 * really important to the user to be able to use the command line interface.</p>
	 * @return Returns the description to show in the help of the command line interface.
	 */
	String description();
	/**
	 * Defines example argument values to show in the help of the command line interface.<p>This field is obligatory, because the help
	 * is really important to the user to be able to use the command line interface.</p>
	 * @return Returns the example argument values to show in the help of the command line interface.
	 */
	String[] examples();
}