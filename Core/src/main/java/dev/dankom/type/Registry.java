package dev.dankom.type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Registry<KEY, VALUE> {

    private List<RegistryEntry> registry;

    public Registry() {
        this.registry = new ArrayList<>();
    }

    public Registry(List<RegistryEntry> registry) {
        this.registry = registry;
    }

    public Registry clone() {
        return new Registry(registry);
    }

    public void put(KEY key, VALUE value) {
        registry.add(new RegistryEntry(key, value));
    }

    public VALUE get(KEY key) {
        for (RegistryEntry re : registry) {
            if (re.getKey().equals(key)) return (VALUE) re;
        }
        return null;
    }

    public void reverse() {
        Collections.reverse(registry);
    }

    class RegistryEntry<KEY, VALUE> {
        private final KEY key;
        private final VALUE value;

        public RegistryEntry(KEY key, VALUE value) {
            this.key = key;
            this.value = value;
        }

        public KEY getKey() {
            return key;
        }

        public VALUE getValue() {
            return value;
        }
    }
}
