package dev.dankom.file.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JsonObjectReader {
    private JSONObject json;
    private JsonObjectReader instance;

    public JsonObjectReader(JSONObject json) {
        this.json = json;
        this.instance = this;
    }

    public Object get(String name) {
        return json.get(name);
    }

    public JSONArray getAsArray(String name) {
        return (JSONArray) json.get(name);
    }

    public JSONObject getAsJsonObject(String name) {
        return (JSONObject) json.get(name);
    }
}
