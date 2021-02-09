package dev.dankom.file.type.file;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.net.URI;

public class AdvancedFile extends File {
    public AdvancedFile(@NotNull String pathname) {
        super(pathname);
        init();
    }

    public AdvancedFile(String parent, @NotNull String child) {
        super(parent, child);
        init();
    }

    public AdvancedFile(File parent, @NotNull String child) {
        super(parent, child);
        init();
    }

    public AdvancedFile(@NotNull URI uri) {
        super(uri);
        init();
    }

    public void init() {
        if (!getParentFile().exists()) {
            getParentFile().mkdirs();
        }
        try {
            createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
