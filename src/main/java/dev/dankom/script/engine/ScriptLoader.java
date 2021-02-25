package dev.dankom.script.engine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ScriptLoader {

    private List<Script> scripts;
    private boolean seeDebug;

    public ScriptLoader(boolean seeDebug) {
        this.seeDebug = seeDebug;
    }

    public void load(File... scripts) {
        this.scripts = new ArrayList<>();
        for (File f : scripts) {
            Script s = new Script(this, seeDebug);
            s.bindScriptToMemory(f);
            this.scripts.add(s);
        }
    }

    public void load(String... scripts) {
        this.scripts = new ArrayList<>();
        for (String f : scripts) {
            f = f.replace(".", "\\\\");
            Script s = new Script(this, seeDebug);
            s.bindResourceAsScriptToMemory(f);
            this.scripts.add(s);
        }
    }

    public Script getScript(String packageAndName) {
        for (Script s : scripts) {
            if ((s.getPackage() + s.getName().replace(".plight", "")).equalsIgnoreCase(packageAndName)) {
                return s;
            }
        }
        return null;
    }

    public Script getScript(String spackage, String name) {
        return getScript(spackage + name);
    }

    public List<Script> scripts() {
        return scripts;
    }

    public static void main(String[] args) {
        ScriptLoader loader = new ScriptLoader(true);
        loader.load("dev/dankom/plight/script/test");

        loader.getScript("scripts/dev/dankom/plight/script/test").getMethod("main").call(new ArrayList<>());
    }
}
