package commandline.command.help;

import commandline.exception.ArgumentNullException;

import java.io.OutputStream;

public abstract class HelpPrinter {
    private final OutputStream outputStream;
    private final String applicationName;

    public HelpPrinter(OutputStream outputStream, String applicationName) {
        if (outputStream == null) {
            throw new ArgumentNullException();
        }
        if (applicationName == null) {
            throw new ArgumentNullException();
        }
        this.outputStream = outputStream;
        this.applicationName = applicationName;
    }

    public OutputStream getOutputStream() {
        return this.outputStream;
    }

    public String getApplicationName() {
        return this.applicationName;
    }

    public abstract void print();
}
