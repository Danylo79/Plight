package dev.dankom.file.md;

import dev.dankom.file.type.file.SimpleFile;

import java.io.File;

public class MdFile extends SimpleFile {
    public MdFile(File path, String name, String contents, boolean override) {
        super("md", path, name, contents, override);
    }
}
