package dev.dankom.script;

import dev.dankom.logger.LogManager;
import dev.dankom.logger.abztract.DefaultLogger;
import dev.dankom.logger.interfaces.ILogger;
import dev.dankom.logger.profiler.Profiler;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PlightScriptLoader {

    private String name;
    private File plightScript;
    private String file;

    public PlightScriptLoader(String pathToResource) {
        loadResourceScript(pathToResource);
    }

    public PlightScriptLoader(File file) {
        loadScript(file);
    }

    public void loadResourceScript(String pathToResource) {
        String scriptPath = "scripts/" + pathToResource + ".plight";
        File script = new File(PlightScriptLoader.class.getClassLoader().getResource(scriptPath).getFile());
        loadScript(script);
    }

    public void loadScript(File file) {
        LogManager.addLogger(name,  new DefaultLogger());
        LogManager.addProfiler(name, new Profiler());

        this.plightScript = file;
        this.name = plightScript.getName().replaceAll(".plight", "");

        String out = "";
        for (String s : get()) {
            out += s + " ";
        }
        this.file = out;

        findBlocks();
    }

    public void findBlocks() {
        List<Block> blocks = new Tokenizer(this).tokenize();
        for (Block b : blocks) {
            System.out.println("--------------------------------- ");
            for (Type t : b.getTokens()) {
                System.out.println(t.getKey() + ": " + t.getToken());
            }
        }
    }

    public void save(List<String> toSave) {
        try {
            PrintWriter pw = new PrintWriter(getPlightScript());
            for (String str : toSave) {
                pw.println(str);
            }
            pw.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<String> get() {
        List<String> lines = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(getPlightScript()));
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lines;
    }

    public String getS() {
        return file;
    }

    public File getPlightScript() {
        return plightScript;
    }

    public ILogger logger() {
        return LogManager.getLogger(name);
    }

    public Profiler profiler() {
        return LogManager.getProfiler(name);
    }

    public static void main(String[] args) {
        PlightScriptLoader scriptLoader = new PlightScriptLoader("test");
    }
}
