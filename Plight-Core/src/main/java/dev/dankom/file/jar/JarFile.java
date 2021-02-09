package dev.dankom.file.jar;

import dev.dankom.file.jar.runner.Runner;
import dev.dankom.util.general.ZipUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class JarFile {

    private File jar;
    private File path;
    private String name;

    public JarFile(File path, String name) throws FileNotFoundException {
        this.path = path;
        this.name = name;

        File jar = new File(path, name + ".jar");
        if (jar.exists()) {
            this.jar = jar;
        } else {
            throw new FileNotFoundException("Jar file not found " + jar.getAbsolutePath() + "!");
        }
    }

    public void unpackage() {
        try {
            File destFile = new File(path, name + ".zip");
            FileUtils.copyFile(jar, destFile);
            ZipUtil.unzip(destFile, new File(path, name));
            destFile.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void repackage() {
        File zip = new File(path, name + ".zip");
        org.zeroturnaround.zip.ZipUtil.pack(new File(path, name), zip);
        zip.renameTo(new File(path, name + ".jar"));
        zip.delete();
    }

    public void run(String... args) {
        Runner.run(jar, args);
    }
}
