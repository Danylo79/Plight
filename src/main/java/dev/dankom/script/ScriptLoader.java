package dev.dankom.script;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ScriptLoader {

    private List<Script> scripts;

    public void load(File... scripts) {
        this.scripts = new ArrayList<>();
        for (File f : scripts) {
            Script s = new Script(this);
            s.loadToMemory(f);
        }
    }

    public void load(String... scripts) {
        this.scripts = new ArrayList<>();
        for (String f : scripts) {
            f = f.replace(".", "\\\\");
            Script s = new Script(this);
            s.loadToMemoryFromResource(f);
        }
    }

    public Script getScript(String packageAndName) {
        for (Script s : scripts) {
            if ((s.getName().replace(".plight", "") + "/" + s.getPackage()).equalsIgnoreCase(packageAndName)) {
                return s;
            }
        }
        return null;
    }

    public Script getScript(String spackage, String name) {
        return getScript(spackage + "/" + name);
    }

    public static void main(String[] args) {
        ScriptLoader loader = new ScriptLoader();
        loader.load("dev/dankom/plight/script/test", "dev/dankom/plight/script/test1");
    }
}
