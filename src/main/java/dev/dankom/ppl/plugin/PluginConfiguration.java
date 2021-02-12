package dev.dankom.ppl.plugin;

import dev.dankom.ppl.util.JsonUtil;
import org.apache.commons.lang3.Validate;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class PluginConfiguration extends JSONObject {

    public PluginConfiguration(String pluginName, ClassLoader loader) throws IOException, ParseException {
        super(JsonUtil.getJsonFromResourceStream("plugin", loader));

        Validate.notNull(getName(), pluginName + " is missing a name attribute!");
        Validate.notNull(getMainClass(), pluginName + " is missing a main attribute!");
    }

    public String getName() {
        return (String) get("name");
     }

    public String getMainClass() {
        return (String) get("main");
    }
}
