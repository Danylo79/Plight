package dev.dankom.type;

import java.util.HashMap;
import java.util.Map;

public class Options {
    private Map<String, Object> options = new HashMap<>();

    public Options(Map<String, Object> dval) {

        addDefaults();

        for (Map.Entry<String, Object> me : dval.entrySet()) {
            options.put(me.getKey(), me.getValue());
        }
    }

    public void addDefaults() {}

    public void add(String key, Object dval) {
        if (options.get(key) == null) {
            set(key, dval);
        }
    }

    public void set(String key, Object value) {
        options.put(key, value);
    }

    public Object get(String key) {
        return options.get(key);
    }

    public Map<String, Object> options() {
        return options;
    }
}
