package commandline.argument.metainfo;/*


/**
 * Tags a method as an option
 *
 * @author Tim Wood
 */

import commandline.argument.validator.ArgumentValidator;
import commandline.argument.validator.DefaultArgumentValidator;
import commandline.language.parser.argument.ArgumentParser;
import commandline.language.parser.argument.MockArgumentParser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CommandArgument {
	String nullValue = "4bc850d9f16c4db588333495b089d04a70bdc00a4abc462a877278762a4e3b2d";
	String shortName();
	String longName();
	boolean obligatory();
	String defaultValue() default nullValue;
	boolean defaultToNull() default false;
	Class<? extends ArgumentParser<?>> parser() default MockArgumentParser.class;
	Class<? extends ArgumentValidator<?>> validator() default DefaultArgumentValidator.class;
	String description();
	String[] examples();
}
