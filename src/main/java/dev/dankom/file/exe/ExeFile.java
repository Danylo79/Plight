package dev.dankom.file.exe;

import dev.dankom.exception.OperatingSystemNotSupported;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;

public class ExeFile extends File {
    public ExeFile(@NotNull String pathname) {
        super(pathname);
    }

    public ExeFile(String parent, @NotNull String child) {
        super(parent, child);
    }

    public ExeFile(File parent, @NotNull String child) {
        super(parent, child);
    }

    public ExeFile(@NotNull URI uri) {
        super(uri);
    }

    public void run() throws OperatingSystemNotSupported, IOException {
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(this);
        } else {
            throw new OperatingSystemNotSupported("Your current os is not supported by this class!");
        }
    }
}
