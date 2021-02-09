package dev.dankom.file.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Iterator;

public class JsonFile {
    private File path;
    private String name;
    private JSONObject json;
    private JSONObject Default;

    public JsonFile(File path, String name, JSONObject Default) {
        this.path = path;
        this.name = name + ".json";
        this.Default = Default;
        this.json = this.Default;

        generate();
    }

    public void generate() {
        if (!isGenerated()) {
            create();
        } else {
            update();
        }
    }

    public JSONObject get() {
        if (!isGenerated()) {
            generate();
        }
        File out = new File(path, getName());
        JSONParser parser = new JSONParser();

        try {
            return (JSONObject) parser.parse(new FileReader(out));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void create() {
        JSONObject obj = json;

        if (!path.exists()) {
            path.mkdirs();
        }

        try (FileWriter file = new FileWriter(new File(path, getName()))) {
            file.write(obj.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void update() {
        JSONObject obj = get();
        boolean updated = false;

        for(Iterator iterator = Default.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            Object val = Default.get(key);

            if (obj.containsKey(key)) {
                continue;
            } else {
                obj.put(key, val);
                updated = true;
            }
        }

        if (updated) {
            try (FileWriter file = new FileWriter(new File(path, getName()))) {
                file.write(obj.toJSONString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isGenerated() {
        try {
            File main = new File(path, getName());
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(main));
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void purge() {
        try (FileWriter file = new FileWriter(new File(path, getName()))) {
            file.write(Default.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public void save() {
        purge();
        generate();
    }

    public void set(String key, Object value) {
        json.put(key, value);
        save();
    }

    public void addToArray(String array, Object o) {
        JSONArray jsonArray = (JSONArray) json.get(array);
        jsonArray.add(o);
        set(array, jsonArray);
    }
}
