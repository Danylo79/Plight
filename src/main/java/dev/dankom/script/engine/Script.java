package dev.dankom.script.engine;

import dev.dankom.logger.LogManager;
import dev.dankom.logger.abztract.DebugLogger;
import dev.dankom.logger.abztract.DefaultLogger;
import dev.dankom.logger.interfaces.ILogger;
import dev.dankom.logger.profiler.Profiler;
import dev.dankom.script.engine.hot.tranformer.ITransformer;
import dev.dankom.script.engine.loader.ScriptLoader;
import dev.dankom.script.exception.ScriptRuntimeException;
import dev.dankom.script.exception.exceptions.ScriptNotLoadedException;
import dev.dankom.script.engine.hot.HotAgent;
import dev.dankom.script.interfaces.MemoryBoundStructure;
import dev.dankom.script.lexer.Lexeme;
import dev.dankom.script.lexer.Lexer;
import dev.dankom.script.lexer.Token;
import dev.dankom.script.logger.ScriptDebugLogger;
import dev.dankom.script.logger.ScriptLogger;
import dev.dankom.script.type.imported.ScriptImport;
import dev.dankom.script.type.imported.ScriptJavaImport;
import dev.dankom.script.type.method.ScriptMethod;
import dev.dankom.script.type.method.ScriptMethodParameter;
import dev.dankom.script.type.var.ScriptUniform;
import dev.dankom.script.type.var.ScriptVariable;
import dev.dankom.util.general.ExceptionUtil;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Script {

    public static final String LOADER_VERSION = "1.0";

    private File file;
    private String name;
    private String spackage;
    private ScriptLoader loader;
    private HotAgent hotAgent;

    public boolean seeDebug;
    public boolean isLoaded = false;

    private String loadType;

    private ILogger logger;
    private ILogger debug;
    private Profiler profiler;

    private Lexer lexer;
    private List<Lexeme> lexemes = new ArrayList<>();

    public List<ScriptImport> imports = new ArrayList<>();
    public List<ScriptJavaImport> java_imports = new ArrayList<>();
    public List<ScriptVariable> variables = new ArrayList<>();
    public List<ScriptUniform> uniforms = new ArrayList<>();
    public List<ScriptMethod> methods = new ArrayList<>();
    public List<MemoryBoundStructure> custom = new ArrayList<>();

    public Script(ScriptLoader loader, boolean seeDebug) {
        this.loader = loader;
        this.seeDebug = seeDebug;
    }

    public boolean bindScriptToMemory(File file) {
        try {
            this.file = file;
            this.name = file.getName().replace(".plight", "");
            this.hotAgent = new HotAgent(this);

            boolean readPackage = true;
            for (String s : file.getPath().split("\\\\")) {
                if (s.contains(".plight")) {
                    break;
                }
                if (readPackage) {
                    spackage += s + "/";
                }
            }

            this.debug = LogManager.addLogger(getPackage() + "/" + getName() + "-debug", new ScriptDebugLogger(seeDebug, this));
            this.logger = LogManager.addLogger(getPackage() + "/" + getName() + "", new ScriptLogger(this));
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

            if (loadType == null) {
                loadType = "default";
            }

            bindMemoryStructures();
            isLoaded = true;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            isLoaded = false;
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
            //Uniforms
            profiler.startSection("bind_uniforms");
            bindUniforms();

            bindUniform("name", getName());
            bindUniform("package", getPackage());
            bindUniform("version", LOADER_VERSION);
            //
            profiler.startSection("bind_methods");
            bindMethods();
            profiler.stopSection("bind_methods");

            for (ScriptImport si : imports) {
                debug.debug("Structure", si);
            }

            for (ScriptVariable sv : variables) {
                debug.debug("Structure", sv);
            }

            for (ScriptUniform su : uniforms) {
                debug.debug("Structure", su);
            }
        } catch (Exception e) {
            profiler.crash("Failed: " + e.getMessage(), e);
        }
    }

    public void flush() {
        logger.important("Script%flush", "Unloading and Cleaning script! (WARNING: Using this operation is risky and may corrupt the in memory script)");
        for (ScriptImport so : imports) {
            so.unload();
        }
        imports.clear();

        for (ScriptJavaImport so : java_imports) {
            so.unload();
        }
        java_imports.clear();

        for (ScriptVariable so : variables) {
            so.unload();
        }
        variables.clear();

        for (ScriptUniform so : uniforms) {
            so.unload();
        }
        uniforms.clear();

        for (ScriptMethod so : methods) {
            so.unload();
        }
        methods.clear();

        for (MemoryBoundStructure mbs : custom) {
            mbs.unload();
        }
        custom.clear();

        lexemes.clear();
        isLoaded = false;
    }

    public void rebind() {
        try {
            logger.important("Script%rebind", "Rebinding using the \'" + loadType + "\' load type!");
            bindScriptToMemory(file);
        } catch (NullPointerException e) {
            ExceptionUtil.throwCompactException(new ScriptRuntimeException("Failed to rebind! (Aborting)", e, this));
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
                    ScriptImport e = bindStructureToMemory(importantLexemes, new ScriptImport(this));
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
                    ScriptJavaImport e = bindStructureToMemory(importantLexemes, new ScriptJavaImport(this));
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
                    ScriptVariable e = bindStructureToMemory(importantLexemes, new ScriptVariable(this));
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
                    ScriptUniform e = bindStructureToMemory(importantLexemes, new ScriptUniform(this));
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
                        methods.add(bindStructureToMemory(body, new ScriptMethod(this, methodName, returnType, pars)));
                        found = false;
                    }

                    if (lookingForBody) {
                        body.add(l);
                    }


                }
            } catch (IndexOutOfBoundsException e) {
            }
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
                debug.debug("UniformBinder", "Bound " + name + " to " + value + "!");
                su.setValue(value);
                return true;
            }
        }
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

    public <T> T bindStructureToMemory(List<Lexeme> lexemes, MemoryBoundStructure mbs) {
        debug.debug("MemoryStructureManager", "Starting binder!");
        T t = null;
        try {
            t = (T) mbs.loadToMemory(lexemes);
        } catch (ScriptRuntimeException e) {
            e.printStackTrace();
        }
        if (t != null) {
            debug.debug("MemoryStructureManager", "Binding memory structure " + mbs.getClass().getName() + " to " + mbs.toString() + "!");
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

    public HotAgent getHotAgent() {
        return hotAgent;
    }

    @Override
    public String toString() {
        return "Script{" +
                "file=" + file +
                ", name='" + name + '\'' +
                ", spackage='" + spackage + '\'' +
                ", loader=" + loader +
                ", hotAgent=" + hotAgent +
                ", seeDebug=" + seeDebug +
                ", isLoaded=" + isLoaded +
                ", loadType='" + loadType + '\'' +
                ", logger=" + logger +
                ", debug=" + debug +
                ", profiler=" + profiler +
                ", lexer=" + lexer +
                '}';
    }
}
