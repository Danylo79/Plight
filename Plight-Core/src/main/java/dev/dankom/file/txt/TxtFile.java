package dev.dankom.file.txt;

import dev.dankom.file.type.file.SimpleFile;

import java.io.File;

public class TxtFile extends SimpleFile {
    public TxtFile(File path, String name, String contents, boolean override) {
        super("txt", path, name, contents, override);
    }
}
