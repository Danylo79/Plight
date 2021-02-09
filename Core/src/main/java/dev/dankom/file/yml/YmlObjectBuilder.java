package dev.dankom.file.yml;

import dev.dankom.file.yml.type.YmlObject;

public class YmlObjectBuilder {
    private final YmlObject yml = new YmlObject();

    public YmlObjectBuilder put(String key, Object value) {
        yml.put(key, value);
        return this;
    }

    public YmlObject build() {
        return yml;
    }
}
