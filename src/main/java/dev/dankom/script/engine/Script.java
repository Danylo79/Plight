package dev.dankom.script.engine;

import dev.dankom.script.exception.ScriptRuntimeException;
import dev.dankom.script.exception.exceptions.ScriptMethodNotFoundException;
import dev.dankom.script.exception.exceptions.ScriptNotLoadedException;
import dev.dankom.script.exception.exceptions.ScriptUniformNotFoundException;
import dev.dankom.script.exception.exceptions.ScriptVariableNotFoundException;
import dev.dankom.script.lexer.Lexeme;
import dev.dankom.script.lexer.Lexer;
import dev.dankom.script.lexer.Token;
import dev.dankom.logger.LogManager;
import dev.dankom.logger.abztract.DefaultLogger;
import dev.dankom.logger.interfaces.ILogger;
import dev.dankom.logger.profiler.Profiler;
import dev.dankom.script.interfaces.MemoryBoundStructure;
import dev.dankom.logger.abztract.DebugLogger;
import dev.dankom.script.type.imported.ScriptImport;
import dev.dankom.script.type.imported.ScriptJavaImport;
import dev.dankom.script.type.method.ScriptMethod;
import dev.dankom.script.type.method.ScriptMethodParameter;
import dev.dankom.script.type.var.ScriptUniform;
import dev.dankom.script.type.var.ScriptVariable;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Script {

    private File file;
    private String name;
    private String spackage;
    private ScriptLoader loader;
    private boolean seeDebug;

    private ILogger logger;
    private ILogger debug;
    private Profiler profiler;

    private Lexer lexer;
    private List<Lexeme> lexemes = new ArrayList<>();

    public List<ScriptImport> imports = new ArrayList<>();
    public List<ScriptVariable> variables = new ArrayList<>();
    public List<ScriptUniform> uniforms = new ArrayList<>();
    public List<ScriptMethod> methods = new ArrayList<>();
    public List<ScriptJavaImport> java_imports = new ArrayList<>();

    public Script(ScriptLoader loader, boolean seeDebug) {
        this.loader = loader;
        this.seeDebug = seeDebug;
    }

    public boolean bindResourceAsScriptToMemory(String pathToFile) {
        if (!pathToFile.contains(".plight")) {
            pathToFile += ".plight";
        }
        return bindScriptToMemory(new File(Script.class.getClassLoader().getResource("scripts/" + pathToFile).getPath()));
    }

    public boolean bindScriptToMemory(File file) {
        try {
            this.file = file;
            this.name = file.getName().replace(".plight", "");

            boolean readPackage = false;
            for (String s : file.getAbsolutePath().split("\\\\")) {
                if (s.equalsIgnoreCase("scripts")) {
                    readPackage = true;
                }
                if (s.contains(".plight")) {
                    break;
                }
                if (readPackage) {
                    spackage += s + "/";
                }
            }

            this.debug = LogManager.addLogger(getPackage() + "/" + getName() + "-debug", new DebugLogger(seeDebug));
            this.logger = LogManager.addLogger(getPackage() + "/" + getName() + "", new DefaultLogger());
            this.profiler = LogManager.addProfiler(getPackage() + "/" + getName(), new Profiler());

            logger.important("Script%loadToMemory()", "Loading " + file.getName() + " to memory!");

            profiler.startSection("scanning_lexemes");
            this.lexer = new Lexer(file);
            while (!lexer.isExhausted()) {
                lexemes.add(new Lexeme(lexer.currentToken(), lexer.currentLexeme(), lexer.currentPos() + 1, lexer.currentLine() + 1));
                lexer.next();
            }

            if (!lexer.isSuccessful()) {
                profiler.crash(lexer.errorMessage(), new Exception("Lexer failed!"));
            }

            bindMemoryStructures();
            return true;
        } catch (Exception e) {
            profiler.crash("Failed: " + e.getMessage(), e);
            return false;
        }
    }

    public final void bindMemoryStructures() {
        try {
            profiler.startSection("bind_imports");
            bindImports();
            profiler.startSection("bind_java_imports");
            bindJavaImports();
            profiler.startSection("bind_variables");
            bindVariables();
            profiler.startSection("bind_uniforms");
            bindUniforms();
            profiler.startSection("bind_methods");
            bindMethods();
            profiler.stopSection("bind_methods");

            for (ScriptImport si : imports) {
                debug.test("Structure", si);
            }

            for (ScriptVariable sv : variables) {
                debug.test("Structure", sv);
            }

            for (ScriptUniform su : uniforms) {
                debug.test("Structure", su);
            }
        } catch (Exception e) {
            profiler.crash("Failed: " + e.getMessage(), e);
        }
    }

    //Memory Binder
    public void bindImports() {
        List<Lexeme> importantLexemes = new ArrayList<>();
        List<ScriptImport> temp = new ArrayList<>();
        boolean listening = false;
        for (int i = 0; i < lexemes.size(); i++) {
            Lexeme l = lexemes.get(i);
            if (l.getToken().equals(Token.IMPORT)) {
                listening = true;
                continue;
            }

            if (l.getToken().equals(Token.END_LINE) && listening) {
                if (!importantLexemes.isEmpty()) {
                    listening = false;
                    ScriptImport e = bindScriptToMemory(importantLexemes, new ScriptImport(this));
                    if (e != null) {
                        temp.add(e);
                    }
                    importantLexemes.clear();
                    continue;
                } else {
                    debug.warning("ImportBinder", "Ran into empty lexeme array!");
                }
            }

            if (listening) {
                importantLexemes.add(l);
                continue;
            }
        }

        for (ScriptImport sv : temp) {
            imports.add(sv);
        }
    }

    public void bindJavaImports() {
        List<Lexeme> importantLexemes = new ArrayList<>();
        List<ScriptJavaImport> temp = new ArrayList<>();
        boolean listening = false;
        for (int i = 0; i < lexemes.size(); i++) {
            Lexeme l = lexemes.get(i);
            if (l.getToken().equals(Token.IMPORT_JAVA)) {
                listening = true;
                continue;
            }

            if (l.getToken().equals(Token.END_LINE) && listening) {
                if (!importantLexemes.isEmpty()) {
                    listening = false;
                    ScriptJavaImport e = bindScriptToMemory(importantLexemes, new ScriptJavaImport(this));
                    if (e != null) {
                        temp.add(e);
                    }
                    importantLexemes.clear();
                    continue;
                } else {
                    debug.warning("JavaImportBinder", "Ran into empty lexeme array!");
                }
            }

            if (listening) {
                importantLexemes.add(l);
                continue;
            }
        }

        for (ScriptJavaImport sv : temp) {
            java_imports.add(sv);
        }
    }

    public void bindVariables() {
        List<Lexeme> importantLexemes = new ArrayList<>();
        List<ScriptVariable> temp = new ArrayList<>();
        boolean listening = false;
        for (int i = 0; i < lexemes.size(); i++) {
            Lexeme l = lexemes.get(i);
            if (l.getToken().equals(Token.DEFINE)) {
                listening = true;
                continue;
            }

            if (l.getToken().equals(Token.END_LINE) && listening) {
                if (!importantLexemes.isEmpty()) {
                    listening = false;
                    ScriptVariable e = bindScriptToMemory(importantLexemes, new ScriptVariable(this));
                    if (e != null) {
                        temp.add(e);
                    }
                    importantLexemes.clear();
                    continue;
                } else {
                    debug.warning("VariableBinder", "Ran into empty lexeme array!");
                }
            }

            if (listening) {
                importantLexemes.add(l);
                continue;
            }
        }

        for (ScriptVariable sv : temp) {
            variables.add(sv);
        }
    }

    public void bindUniforms() {
        List<Lexeme> importantLexemes = new ArrayList<>();
        List<ScriptUniform> temp = new ArrayList<>();
        boolean listening = false;
        for (int i = 0; i < lexemes.size(); i++) {
            Lexeme l = lexemes.get(i);
            if (l.getToken().equals(Token.UNIFORM)) {
                listening = true;
                continue;
            }

            if (l.getToken().equals(Token.END_LINE) && listening) {
                if (!importantLexemes.isEmpty()) {
                    listening = false;
                    ScriptUniform e = bindScriptToMemory(importantLexemes, new ScriptUniform(this));
                    if (e != null) {
                        temp.add(e);
                    }
                    importantLexemes.clear();
                    continue;
                } else {
                    debug.warning("UniformBinder", "Ran into empty lexeme array!");
                }
            }

            if (listening) {
                importantLexemes.add(l);
                continue;
            }
        }

        for (ScriptUniform sv : temp) {
            uniforms.add(sv);
        }
    }

    public void bindMethods() {
        List<ScriptMethod> temp = new ArrayList<>();

        List<ScriptMethodParameter> pars = new ArrayList<>();
        List<Lexeme> body = new ArrayList<>();

        boolean lookingForPars = false;
        boolean lookingForBody = false;
        boolean found = false;

        String methodName = null;
        String returnType = null;

        String cParName = null;
        String cParType = null;

        for (int i = 0; i < lexemes.size(); i++) {
            try {
                Lexeme l = lexemes.get(i);

                if (l.getToken() == Token.STRUCT) {
                    found = true;
                }

                if (found) {
                    if (l.getToken() == Token.IDENTIFIER && lexemes.get(i - 2).getToken() == Token.STRUCT) {
                        methodName = l.getLexeme();
                    }

                    if (l.getToken() == Token.IDENTIFIER && lexemes.get(i - 1).getToken() == Token.STRUCT) {
                        returnType = l.getLexeme();
                    }

                    if (l.getToken() == Token.OPEN) {
                        lookingForPars = true;
                    }

                    if (l.getToken() == Token.CLOSE) {
                        lookingForPars = false;
                    }

                    if (l.getToken() == Token.IDENTIFIER && lexemes.get(i + 1).getToken() == Token.IDENTIFIER && cParName == null && lookingForPars) {
                        cParName = l.getLexeme();
                    }

                    if (l.getToken() == Token.IDENTIFIER && (lexemes.get(i + 1).getToken() == Token.COMMA || lexemes.get(i + 1).getToken() == Token.CLOSE) && cParType == null && lookingForPars) {
                        cParType = l.getLexeme();
                        pars.add(new ScriptMethodParameter(cParName, cParType));
                        cParName = null;
                        cParType = null;
                    }

                    if (l.getToken() == Token.OPEN_BRACKET) {
                        lookingForBody = true;
                        continue;
                    }

                    if (l.getToken() == Token.CLOSE_BRACKET) {
                        lookingForBody = false;
                        methods.add(bindScriptToMemory(body, new ScriptMethod(this, methodName, returnType, pars)));
                        found = false;
                    }

                    if (lookingForBody) {
                        body.add(l);
                    }


                }
            } catch (IndexOutOfBoundsException e) {}
        }

        for (ScriptMethod sv : temp) {
            methods.add(sv);
        }
    }

    //Uniform
    public void bindUniforms(HashMap<String, String> uniform) {
        for (Map.Entry me : uniform.entrySet()) {
            if (getUniform((String) me.getKey()) != null) {
                bindUniform((String) me.getKey(), (String) me.getValue());
            }
        }
    }

    public boolean bindUniform(String name, String value) {
        for (ScriptUniform su : uniforms) {
            if (su.getName().equalsIgnoreCase(name)) {
                debug.test("UniformBinder", "Bound " + name + " to " + value + "!");
                su.setValue(value);
                return true;
            }
        }
        debug.error("UniformBinder", "Could not find " + name + "!");
        return false;
    }

    //Getters
    public ScriptUniform getUniform(String name) {
        for (ScriptUniform su : uniforms) {
            if (su.getName().equalsIgnoreCase(name)) {
                return su;
            }
        }

        for (ScriptImport si : imports) {
            if (getLoader().getScript(si.getPackage()) == null) {
                try {
                    throw new ScriptNotLoadedException("Failed to find script " + si.getPackage() + "!", this);
                } catch (ScriptNotLoadedException e) {
                    e.printStackTrace();
                }
            }
            for (ScriptUniform su : getLoader().getScript(si.getPackage()).uniforms) {
                if (su.getName().equalsIgnoreCase(name)) {
                    return su;
                }
            }
        }
        return null;
    }

    public ScriptVariable getVariable(String name) {
        for (ScriptVariable su : variables) {
            if (su.getName().equalsIgnoreCase(name)) {
                return su;
            }
        }

        for (ScriptImport si : imports) {
            if (getLoader().getScript(si.getPackage()) == null) {
                try {
                    throw new ScriptNotLoadedException("Failed to find script " + si.getPackage() + "!", this);
                } catch (ScriptNotLoadedException e) {
                    e.printStackTrace();
                }
            }
            for (ScriptVariable su : getLoader().getScript(si.getPackage()).variables) {
                if (su.getName().equalsIgnoreCase(name)) {
                    return su;
                }
            }
        }
        return null;
    }

    public ScriptMethod getMethod(String name) {
        for (ScriptMethod sm : methods) {
            if (sm.getName().equalsIgnoreCase(name)) {
                return sm;
            }
        }

        for (ScriptImport si : imports) {
            if (getLoader().getScript(si.getPackage()) == null) {
                try {
                    throw new ScriptNotLoadedException("Failed to find script " + si.getPackage() + "!", this);
                } catch (ScriptNotLoadedException e) {
                    e.printStackTrace();
                }
            }
            for (ScriptMethod sm : getLoader().getScript(si.getPackage()).methods) {
                if (sm.getName().equalsIgnoreCase(name)) {
                    return sm;
                }
            }
        }
        return null;
    }

    public Method getJavaMethod(String name, Class<?>... pars) {
        for (ScriptJavaImport sji : java_imports) {
            try {
                return sji.getClazz().getMethod(name, pars);
            } catch (NoSuchMethodException e) {
                continue;
            }
        }
        return null;
    }

    public Method getJavaMethod(String clazz, String name, Class<?>... pars) {
        try {
            try {
                Class<?> fclazz = Class.forName(clazz);
                Method m = fclazz.getDeclaredMethod(name, pars);
                return m;
            } catch (ClassNotFoundException e) {
                throw new ScriptRuntimeException("Failed to find class " + clazz + "!", this);
            } catch (NoSuchMethodException e) {
                throw new ScriptRuntimeException("Failed to find method " + name + " in class " + clazz + "!", this);
            }
        } catch (ScriptRuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }
    //

    public <T> T bindScriptToMemory(List<Lexeme> lexemes, MemoryBoundStructure mbs) {
        debug.test("MemoryStructureManager", "Starting binder!");
        T t = null;
        try {
            t = (T) mbs.loadToMemory(lexemes);
        } catch (ScriptRuntimeException e) {
            e.printStackTrace();
        }
        if (t != null) {
            debug.test("MemoryStructureManager", "Binding memory structure " + mbs.getClass().getName() + " to " + mbs.toString() + "!");
        } else {
            logger.error("MemoryStructureManager", "Returned null! (Aborting) (Failed to bind " + mbs.getClass().getName() + " to memory!)");
        }
        return t;
    }

    public String getName() {
        return name;
    }

    public String getPackage() {
        return spackage.replace("null", "");
    }

    public ScriptLoader getLoader() {
        return loader;
    }

    public ILogger logger() {
        return logger;
    }

    public ILogger debug() {
        return debug;
    }

    public Profiler profiler() {
        return profiler;
    }
}
