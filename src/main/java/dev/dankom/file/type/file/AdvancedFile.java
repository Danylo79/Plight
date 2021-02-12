package dev.dankom.file.type.file;

import java.io.File;
import java.io.IOException;
import java.net.URI;

public class AdvancedFile extends File {
    public AdvancedFile(String pathname) {
        super(pathname);
        init();
    }

    public AdvancedFile(String parent, String child) {
        super(parent, child);
        init();
    }

    public AdvancedFile(File parent, String child) {
        super(parent, child);
        init();
    }

    public AdvancedFile(URI uri) {
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
