package dev.dankom.file.type;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

    public List<File> getChildren() {
        List<File> out = new ArrayList<>();

        String[] names = list();
        for(String name : names) {
            out.add(new File(new File(this, name).getAbsolutePath()));
        }

        return out;
    }
}