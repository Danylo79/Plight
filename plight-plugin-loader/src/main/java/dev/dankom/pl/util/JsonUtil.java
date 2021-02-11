package dev.dankom.pl.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class JsonUtil {
    public static final JSONObject getJsonFromResourceStream(String jsonName, ClassLoader loader) throws IOException, ParseException {
        JSONObject out;
        JSONParser parser = new JSONParser();
        out = (JSONObject) parser.parse(new InputStreamReader(loader.getResourceAsStream(jsonName)));
        return out;
    }

    public static final Map getJsonMapFromResourceStream(String jsonName, ClassLoader loader) throws IOException, ParseException {
        return getJsonFromResourceStream(jsonName, loader);
    }
}
