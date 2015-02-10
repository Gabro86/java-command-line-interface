package commandline.command.help.print;

import commandline.exception.ArgumentNullException;

import java.io.OutputStream;

public abstract class HelpPrinter {
	private final OutputStream outputStream;

	public HelpPrinter(OutputStream outputStream) {
		if (outputStream == null) {
			throw new ArgumentNullException();
		}
		this.outputStream = outputStream;
	}

	public OutputStream getOutputStream() {
		return this.outputStream;
	}

	public abstract void print();
}
