package dev.dankom.file.type.file;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.URISyntaxException;

public class SimpleFile {
    private String extension;
    private File path;
    private String name;
    private String contents;
    private File template;
    private boolean override;

    public SimpleFile(String extension, File path, String name, String contents, boolean override) {
        this.extension = extension;
        this.path = path;
        this.name = name;
        this.contents = contents;
        this.template = null;
        this.override = override;

        generate();
    }

    public SimpleFile(String extension, File path, File template, boolean override) {
        this.extension = extension;
        try {
            ClassLoader classLoader = SimpleFile.class.getClassLoader();
            this.path = new File(classLoader.getResource(template.getName()).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        this.template = template;
        this.contents = null;
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

    public void create(){
        try {
            if (template == null && contents != null) {
                Writer output = new BufferedWriter(new FileWriter(getName()));
                output.write(contents);
                output.close();
                if (extension.equalsIgnoreCase(".sh")) {
                    Runtime.getRuntime().exec("chmod u+x " + getName());
                }
            } else {
                FileUtils.copyFileToDirectory(template, path);
            }
        } catch (IOException ex){

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
        if (contents != null && template == null) {
            path.mkdirs();
            File out = new File(path, name + "." + extension);
            return out.getAbsolutePath();
        } else {
            path.mkdirs();
            File out = new File(path, template.getName());
            return out.getAbsolutePath();
        }
    }
}
