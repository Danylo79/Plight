package dev.dankom.file.yml;

import dev.dankom.file.yml.type.YmlObject;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Iterator;

public class YmlFile {
    private final File path;
    private final String name;
    private YmlObject dval;

    public YmlFile(File path, String name, YmlObject dval) {
        this.path = path;
        this.name = name + ".yml";
        this.dval = dval;

        generate();
    }

    public void generate() {
        if (!isGenerated()) {
            create();
        } else {
            update();
        }
    }

    public void create() {
        try {
            File file = new File(path, name);
            if (!file.exists()) file.createNewFile();

            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            options.setPrettyFlow(true);

            Yaml yaml = new Yaml(options);
            PrintWriter writer = new PrintWriter(file);
            yaml.dump(dval, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        YmlObject obj = get();
        boolean updated = false;

        for(Iterator iterator = dval.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            Object val = dval.get(key);

            if (obj.containsKey(key)) {
                continue;
            } else {
                obj.put(key, val);
                updated = true;
            }
        }

        if (updated) {
            try (FileWriter file = new FileWriter(new File(path, getName()))) {
                DumperOptions options = new DumperOptions();
                options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
                options.setPrettyFlow(true);
                Yaml yaml = new Yaml(options);
                yaml.dump(obj, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public YmlObject get() {
        try {
            InputStream stream = new FileInputStream(new File(path, getName()));
            return new YmlObject(new Yaml().load(stream));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isGenerated() {
        return new File(path, getName()).exists();
    }

    public String getName() {
        return name;
    }
}
