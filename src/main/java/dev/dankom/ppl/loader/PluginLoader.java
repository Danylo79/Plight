package dev.dankom.ppl.loader;

import java.io.File;
import java.io.IOException;
import java.util.jar.JarFile;

public class PluginLoader extends ClassLoader {

    private final File jar;
    private final JarFile jarFile;

    public PluginLoader(File jar) throws IOException {
        this.jar = jar;
        this.jarFile = new JarFile(jar);
    }

    public File getJar() {
        return jar;
    }

    public JarFile getJarFile() {
        return jarFile;
    }
}
