package dev.dankom.type;

import java.util.HashMap;

public class AttributeMap<T> extends HashMap<String, T> {

    public AttributeMap() {
        super(new HashMap<>());
    }

    public AttributeMap(HashMap<String, T> defaults) {
        super(defaults);
    }

    public void set(String key, T o) {
        put(key, o);
    }

    public void setDefaults(HashMap<String, T> defaults) {
        putAll(defaults);
    }
}
