package dev.dankom.util.general;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public static void copyDirectory(File source, File destination) {
        try {
            Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<File> getChildren(File file) {
        List<File> out = new ArrayList<>();
        String[] directories = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });

        for (String s : directories) {
            out.add(new File(s));
        }
        return out;
    }
}
