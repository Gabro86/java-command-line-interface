package commandline.language.parser.specific.list;

import commandline.language.parser.specific.FileArgumentParser;

import java.io.File;

/*
 * This class is aimed to be used in the @CliAnnotation
 */

/**
 * User: gno, Date: 18.02.2015 - 14:44
 */
public class FileListArgumentParser extends ListArgumentParser<File> {
	public FileListArgumentParser() {
		super(new FileArgumentParser());
	}
}
