package dev.dankom.ppl.configuration;

import dev.dankom.ppl.util.JsonUtil;
import dev.dankom.util.general.Validation;
import dev.dankom.util.reflection.scanner.AnnotationScanner;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class PluginConfiguration extends JSONObject {

    protected List<ConfigurationAttribute> attributes;

    @ConfigurationAttribute
    public String name;
    @ConfigurationAttribute
    public String mainClass;

    public PluginConfiguration(String pluginName, ClassLoader loader) throws IOException, ParseException {
        JsonUtil.getJsonFromResourceStream("plugin", loader);

        this.attributes = new ArrayList<>();
        AnnotationScanner scanner = new AnnotationScanner(getClass());
        for (Annotation ca : scanner.scan(ConfigurationAttribute.class)) {
            ConfigurationAttribute attribute = (ConfigurationAttribute) ca;
            this.attributes.add(attribute);
        }

        set("name", pluginName);
    }

    public String getName() {
        return (String) get("name");
     }

    public String getMainClass() {
        return (String) get("main");
    }

    public <T> void set(String field, T value) {
        try {
            Field df = getClass().getDeclaredField(field);
            df.setAccessible(true);
            df.set(value, value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public <T> T get(String field) {
        try {
            Field df = getClass().getDeclaredField(field);
            df.setAccessible(true);
            return (T) df.get(null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
