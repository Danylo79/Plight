package dev.dankom.file.sh;

import dev.dankom.file.type.file.SimpleFile;

import java.io.File;

public class ShFile extends SimpleFile {
    public ShFile(File path, String name, String contents, boolean override) {
        super("sh", path, name, contents, override);
    }

    public ShFile(File path, File contents, boolean override) {
        super("sh", path, contents, override);
    }
}
