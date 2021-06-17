package dev.dankom.file.jar.runner;

import dev.dankom.util.reflection.JarClassLoader;

import java.io.File;

public class Runner {
    public static boolean run(File jarFile, String... args) {
        try {
            JarClassLoader loader = new JarClassLoader(jarFile.toURL());
            String main = loader.getMainClassName();
            loader.invokeClass(main, args);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
