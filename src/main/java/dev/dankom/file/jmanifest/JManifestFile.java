package dev.dankom.file.jmanifest;

import dev.dankom.jmanifest.JManifestGenerator;
import dev.dankom.util.general.DataStructureAdapter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

public class JManifestFile {
    private File path;
    private String name;
    private JSONObject json;
    private JSONObject Default;

    public JManifestFile(File path, String name, JSONObject Default) {
        this.path = path;
        this.name = name + ".jmanifest";
        this.Default = Default;
        this.json = this.Default;
        this.generate();
    }

    public void generate() {
        if (!this.isGenerated()) {
            this.create();
        } else {
            this.update();
        }

    }

    public JSONObject get() {
        if (!this.isGenerated()) {
            this.generate();
        }

        File out = new File(this.path, this.getName());
        JSONParser parser = new JSONParser();

        try {
            return (JSONObject)parser.parse(new FileReader(out));
        } catch (FileNotFoundException var4) {
            var4.printStackTrace();
        } catch (ParseException var5) {
            var5.printStackTrace();
        } catch (IOException var6) {
            var6.printStackTrace();
        }

        return null;
    }

    private void create() {
        JSONObject obj = this.json;
        if (!this.path.exists()) {
            this.path.mkdirs();
        }

        try {
            FileWriter file = new FileWriter(new File(this.path, this.getName()));
            Throwable var3 = null;

            try {
                file.write(obj.toJSONString());
            } catch (Throwable var13) {
                var3 = var13;
                throw var13;
            } finally {
                if (file != null) {
                    if (var3 != null) {
                        try {
                            file.close();
                        } catch (Throwable var12) {
                            var3.addSuppressed(var12);
                        }
                    } else {
                        file.close();
                    }
                }

            }
        } catch (IOException var15) {
            var15.printStackTrace();
        }

    }

    private void update() {
        JSONObject obj = this.get();
        boolean updated = false;
        Iterator iterator = this.Default.keySet().iterator();

        while(iterator.hasNext()) {
            String key = (String)iterator.next();
            Object val = this.Default.get(key);
            if (!obj.containsKey(key)) {
                obj.put(key, val);
                updated = true;
            }
        }

        if (updated) {
            try {
                FileWriter file = new FileWriter(new File(this.path, this.getName()));
                Throwable var18 = null;

                try {
                    file.write(obj.toJSONString());
                } catch (Throwable var14) {
                    var18 = var14;
                    throw var14;
                } finally {
                    if (file != null) {
                        if (var18 != null) {
                            try {
                                file.close();
                            } catch (Throwable var13) {
                                var18.addSuppressed(var13);
                            }
                        } else {
                            file.close();
                        }
                    }

                }
            } catch (IOException var16) {
                var16.printStackTrace();
            }
        }

    }

    public boolean isGenerated() {
        try {
            File main = new File(this.path, this.getName());
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject)parser.parse(new FileReader(main));
            return true;
        } catch (FileNotFoundException var4) {
            return false;
        } catch (ParseException var5) {
            var5.printStackTrace();
        } catch (IOException var6) {
            var6.printStackTrace();
        }

        return false;
    }

    public void purge() {
        try {
            FileWriter file = new FileWriter(new File(this.path, this.getName()));
            Throwable var2 = null;

            try {
                file.write(this.Default.toJSONString());
            } catch (Throwable var12) {
                var2 = var12;
                throw var12;
            } finally {
                if (file != null) {
                    if (var2 != null) {
                        try {
                            file.close();
                        } catch (Throwable var11) {
                            var2.addSuppressed(var11);
                        }
                    } else {
                        file.close();
                    }
                }

            }
        } catch (IOException var14) {
            var14.printStackTrace();
        }

    }

    public String getName() {
        return this.name;
    }

    public void save() {
        this.purge();
        this.generate();
    }

    public void set(String key, Object value) {
        this.json.put(key, value);
        this.save();
    }

    public void addToArray(String array, Object o) {
        JSONArray jsonArray = (JSONArray)this.json.get(array);
        jsonArray.add(o);
        this.set(array, jsonArray);
    }

    public void genManifest(Class<?> clazz) {
        JManifestGenerator generator = new JManifestGenerator(path, clazz);
        generator.gen();
    }

    public Class<?> getJClass() throws ClassNotFoundException {
        return Class.forName((String) get().get("name"));
    }

    public List<Method> getMethods() throws ClassNotFoundException {
        return DataStructureAdapter.arrayToList(getJClass().getMethods());
    }

    public List<Field> getFields() throws ClassNotFoundException {
        return DataStructureAdapter.arrayToList(getJClass().getFields());
    }

    public List<Annotation> getAnnotations() throws ClassNotFoundException {
        return DataStructureAdapter.arrayToList(getJClass().getAnnotations());
    }
}
