package dev.dankom.ppl.plugin;

import dev.dankom.ppl.util.JsonUtil;
import dev.dankom.util.general.Validation;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class PluginConfiguration extends JSONObject {

    public PluginConfiguration(String pluginName, ClassLoader loader) throws IOException, ParseException {
        super(JsonUtil.getJsonFromResourceStream("plugin", loader));

        Validation.notNull(pluginName + " is missing a name attribute!", getName());
        Validation.notNull(pluginName + " is missing a main attribute!", getMainClass());
    }

    public String getName() {
        return (String) get("name");
     }

    public String getMainClass() {
        return (String) get("main");
    }
}
