package commandline.annotation;

import commandline.argument.validator.ArgumentValidator;
import commandline.argument.validator.DefaultArgumentValidator;
import commandline.language.parser.ArgumentParser;
import commandline.language.parser.MockArgumentParser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CliArgument {
	String nullValue = "null-value-4bc850d9f16c4db588333495b089d04a70bdc00a4abc462a877278762a4e3b2d";
	String shortName();
	String longName();
	boolean obligatory();
	String defaultValue() default nullValue;
	Class<? extends ArgumentParser<?>> parser() default MockArgumentParser.class;
	Class<? extends ArgumentValidator<?>> validator() default DefaultArgumentValidator.class;
	String description();
	String[] examples();
}