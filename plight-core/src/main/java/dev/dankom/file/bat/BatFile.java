package dev.dankom.file.bat;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class BatFile {

    private final File path;
    private final String name;
    private final List<String> contents;
    private boolean override;

    public BatFile(File path, String name, String contents, boolean override) {
        this.path = path;
        this.name = name;
        this.contents = Arrays.asList(contents.split("\n"));
        this.override = override;

        generate();
    }

    public void generate() {
        if (override && isGenerated()) {
            delete();
        }
        if (!isGenerated()) {
            create();
        }
    }

    public void create() {
        try {
            File file = new File(getName());
            file.createNewFile();
            PrintWriter writer = new PrintWriter(file, "UTF-8");
            for (String s : contents) {
                writer.println(s);
            }
            writer.close();
        } catch (IOException e) {

        }
    }

    public boolean isGenerated() {
        try {
            File main = new File(path, getName());
            new FileReader(main);
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void delete() {
        new File(getName()).delete();
    }

    public String getName() {
        path.mkdirs();
        File out = new File(path, name + ".bat");
        return out.getAbsolutePath();
    }
}
