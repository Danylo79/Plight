package dev.dankom.jna;

import com.sun.jna.Library;
import com.sun.jna.Native;
import dev.dankom.logger.LogManager;
import dev.dankom.logger.abztract.DefaultLogger;
import dev.dankom.logger.interfaces.ILogger;

public class LibraryLoader<T extends Library> {
    private final T lib;
    private static final ILogger logger = LogManager.addLogger("jns", new DefaultLogger());

    LibraryLoader(String name, Class<T> clazz) {
        this.lib = loadNative(name, clazz);
    }

    private T loadNative(String name, Class<T> clazz) {
        T lib = (T) Native.loadLibrary(name, clazz);
        logger.important("LibraryLoader", "Loading native " + name + " using " + clazz.getName() + "!");
        return lib;
    }

    public static final <T extends Library> LibraryLoader<T> create(String name, Class<T> clazz) {
        logger.important("LibraryLoader", "Creating new library loader for " + name + "!");
        return new LibraryLoader(name, clazz);
    }

    public T getLib() {
        return lib;
    }
}
