package dev.dankom.script.engine.loader;

import dev.dankom.interfaces.impl.ThreadMethodRunner;
import dev.dankom.logger.abztract.DefaultLogger;
import dev.dankom.operation.operations.ShutdownOperation;
import dev.dankom.script.engine.Script;
import dev.dankom.script.engine.loader.boostrap.ScriptBoostrap;
import dev.dankom.script.type.method.ScriptMethodCall;
import dev.dankom.util.general.args.ArgParser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ScriptLoader {

    private List<Script> scripts;
    private boolean seeDebug;
    private ScriptBoostrap boostrap;

    /**
     * @param args A list of all the args
     */
    public ScriptLoader(String... args) {
        ArgParser parser = new ArgParser();
        parser.addArg(new ArgParser.Arg("-boostrap", "dev.dankom.script.engine.loader.boostrap.TestBoostrap", false, false));
        parser.addArg(new ArgParser.Arg("-help", false, true, false));
        ArgParser.ArgList argList = parser.parseArgs(args);

        if ((boolean) argList.get("-help").getValue()) {
            System.out.println("Commands:");
            System.out.println("-help | List all arguments");
            System.out.println("-boostrap <class> | Sets the boostrap to the given boostrap");
        }

        try {
            this.boostrap = (ScriptBoostrap) Class.forName((String) argList.get("-boostrap").getValue()).newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        this.boostrap.onInit();

        this.seeDebug = this.boostrap.seeDebug();

        new ShutdownOperation(new ThreadMethodRunner(() -> {
            this.boostrap.onShutdown();
        }), "", new DefaultLogger());
    }

    public void load(File... scripts) {
        this.scripts = new ArrayList<>();
        for (File f : scripts) {
            Script s = new Script(this, seeDebug);
            s.bindScriptToMemory(f);
            this.scripts.add(s);
            boostrap.onLoadScript(s);
        }
    }

    public void loadDefaultLibraryScript(String... scripts) {
        this.scripts = new ArrayList<>();
        for (String f : scripts) {
            f = f.replace(".", "\\\\");
            Script s = new Script(this, seeDebug);
            s.bindLibraryScriptToMemory(f);
            this.scripts.add(s);
            boostrap.onLoadScript(s);
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
        ScriptLoader loader = new ScriptLoader();
        loader.loadDefaultLibraryScript("dev/dankom/plight/script/test", "dev/dankom/plight/script/test1");

        Script script = loader.getScript("scripts/dev/dankom/plight/script/test");
        script.getMethod("main").call(new ArrayList<>());
    }
}
