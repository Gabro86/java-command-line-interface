package commandline.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation marks a class as command line interface command.
 * @author gno
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CliCommand {
	/**
	 * Defines the command name that is used to execute the command.<p>This field is obligatory.</p>
	 * @return Returns the command name.
	 */
	String name();
	/**
	 * Defines the command description that is shown in the command line help.<p>This field is obligatory, because the help is really
	 * important to the user to be able to use the command line interface.</p>
	 * @return Returns the command description.
	 */
	String description();
}