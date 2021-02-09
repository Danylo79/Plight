package dev.dankom.file.yml.type;

import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.List;

public class YmlObject extends HashMap<String, Object> {

    public YmlObject() {}

    public YmlObject(HashMap<String, Object> map) {
        super(map);
    }

    //Getters
    public String getString(String key) {
        return (String) get(key);
    }

    public boolean getBoolean(String key) {
        return (boolean) get(key);
    }

    public int getInt(String key) {
        return (int) get(key);
    }

    public long getLong(String key) {
        return (long) get(key);
    }

    public double getDouble(String key) {
        return (double) get(key);
    }

    public float getFloat(String key) {
        return (float) get(key);
    }

    public <T> List<T> getList(String key) {
        return (List<T>) get(key);
    }

    public List<String> getStringList(String key) {
        return getList(key);
    }

    public List<Boolean> getBooleanList(String key) {
        return getList(key);
    }

    public List<Integer> getIntList(String key) {
        return getList(key);
    }

    public List<Long> getLongList(String key) {
        return getList(key);
    }

    public List<Double> getDoubleList(String key) {
        return getList(key);
    }

    public List<Float> getFloatList(String key) {
        return getList(key);
    }
    //

    public void addDataKey(String key, Object dval) {
        if (get(key) == null) {
            put(key, dval);
        }
    }

    public JSONObject toJSON() {
        return new JSONObject(this);
    }

    public YmlObject put(HashMap<String, Object> map) {
        return new YmlObject(map);
    }
}
