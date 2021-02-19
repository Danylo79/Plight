package dev.dankom.tests.plugin;

import dev.dankom.ppl.configuration.PluginConfiguration;
import dev.dankom.ppl.loader.PluginLoader;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;

public class PluginConfigurationTest {
    public static void main(String[] args) {
        try {
            PluginConfiguration pc = new PluginConfiguration("", new PluginLoader(new File("C:\\Users\\danko\\.m2\\repository\\dev\\dankom\\plight\\Plight\\1.4\\Plight-1.4.jar")));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
