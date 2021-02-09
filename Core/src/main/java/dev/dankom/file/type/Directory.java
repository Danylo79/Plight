package dev.dankom.file.type;

import java.io.File;

public class Directory extends File {
    public String name;

    public Directory(String name) {
        super(name);
        this.name = name;

        this.mkdirs();
    }

    public Directory(File parent, String name) {
        super(parent, name);
        this.name = name;

        this.mkdirs();
    }
}