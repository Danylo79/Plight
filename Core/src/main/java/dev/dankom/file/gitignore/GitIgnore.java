package dev.dankom.file.gitignore;

import dev.dankom.file.type.file.SimpleFile;

import java.io.File;

public class GitIgnore extends SimpleFile {
    public GitIgnore(File path, String contents, boolean override) {
        super("", path, ".gitignore", contents, override);
    }
}
