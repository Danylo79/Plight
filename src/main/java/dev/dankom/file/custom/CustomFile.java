package dev.dankom.file.custom;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class CustomFile extends File implements Cloneable {

    public CustomFile(File file) {
        this(file.getAbsolutePath());
    }

    public CustomFile(String pathname) {
        super(pathname);
        init();
    }

    public CustomFile(String parent, String child) {
        super(parent, child);
        init();
    }

    public CustomFile(File parent, String child) {
        super(parent, child);
        init();
    }

    public CustomFile(URI uri) {
        super(uri);
        init();
    }

    public void init() {
        try {
            if (!getParentFile().exists()) {
                getParentFile().mkdirs();
                createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save(List<String> toSave) {
        try {
            PrintWriter pw = new PrintWriter(this);
            for (String str : toSave) {
                pw.println(str);
            }
            pw.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<String> get() {
        List<String> lines = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this));
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lines;
    }

    @Override
    protected CustomFile clone() {
        return new CustomFile(getAbsolutePath());
    }
}
