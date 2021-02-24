package dev.dankom.script;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ScriptLoader {
    private List<Script> scripts = new ArrayList<>();

    public void loadResourceScript(String pathToFile) {
        Script script = new Script(this);
        script.loadFromResource(pathToFile);
        scripts.add(script);
    }

    public void loadScript(File file) {
        Script script = new Script(this);
        script.loadFile(file);
        scripts.add(script);
    }

    public Script getScript(String packageAndName) {
        for (Script s : scripts) {
            if ((s.getPackage() + s.getName()).equalsIgnoreCase(packageAndName)) {
                return s;
            }
        }
        return null;
    }

    public Script getScript(String spackage, String name) {
        return getScript(spackage + name);
    }

    public List<Script> getScripts() {
        return scripts;
    }

    public static void main(String[] args) {
        ScriptLoader loader = new ScriptLoader();
        loader.loadResourceScript("dev/dankom/plight/script/test1");
        loader.loadResourceScript("dev/dankom/plight/script/test");

        loader.scripts.get(1).getMethod("main").run();
    }
}
