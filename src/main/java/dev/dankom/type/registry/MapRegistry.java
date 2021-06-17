package dev.dankom.type.registry;

import dev.dankom.annotation.json.JsonSerializable;
import dev.dankom.file.json.JsonObjectBuilder;
import dev.dankom.interfaces.Copyable;
import dev.dankom.interfaces.Wrapper;
import dev.dankom.type.map.SimpleMap;
import org.json.simple.JSONAware;

import java.util.UUID;

public class MapRegistry<K, V> implements Copyable, Wrapper<SimpleMap<Registry<K>, Registry<V>>>, JSONAware, JsonSerializable {
    private Registry<K> keys = new Registry<>();
    private Registry<V> values = new Registry<>();

    private UUID id = UUID.randomUUID();

    public MapRegistry() {}

    public MapRegistry(Registry<K> keys, Registry<V> values) {
        this.keys = keys.copy();
        this.values = values.copy();
    }

    public void put(K key, V val) {
        if (keys.isEmpty()) {
            put(key, val, keys.size() + 1);
        } else {
            put(key, val, keys.size());
        }
    }

    public void put(K key, V val, int index) {
        keys.insert(key, index);
        values.insert(val, index);
    }

    public V get(K key) {
        V out = null;
        for (int i = 0; i < keys.size(); i++) {
            if (key == keys.get(i)) {
                out = values.get(i);
            } else if (key instanceof String && ((String)key).equalsIgnoreCase((String) keys.get(i))) {
                out = values.get(i);
            }
        }
        return out;
    }

    public void clear() {
        keys.clear();
        values.clear();
    }

    public Registry<K> keys() {
        return keys.copy();
    }

    public Registry<V> values() {
        return values.copy();
    }

    @Override
    public MapRegistry<K, V> copy() {
        return new MapRegistry<>(keys, values);
    }

    @Override
    public SimpleMap<Registry<K>, Registry<V>> fetch() {
        return new SimpleMap<>(keys, values);
    }

    @Override
    public String toJSONString() {
        return new JsonObjectBuilder().addKeyValuePair("id", id).addArray("keys", keys).addArray("values", values).build().toJSONString();
    }
}
