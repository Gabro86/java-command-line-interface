package commandline.command.help.print;

import commandline.exception.ArgumentNullException;
import org.jetbrains.annotations.NotNull;

import java.io.OutputStream;

public abstract class HelpPrinter {
	@NotNull
	private final OutputStream outputStream;

	public HelpPrinter(@NotNull OutputStream outputStream) {
		if (outputStream == null) {
			throw new ArgumentNullException();
		}
		this.outputStream = outputStream;
	}

	@NotNull
	public OutputStream getOutputStream() {
		return this.outputStream;
	}

	public abstract void print();
}
