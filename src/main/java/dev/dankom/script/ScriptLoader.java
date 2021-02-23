package dev.dankom.script;

import dev.dankom.lexer.Lexer;
import dev.dankom.lexer.Token;
import dev.dankom.logger.LogManager;
import dev.dankom.logger.abztract.DefaultLogger;
import dev.dankom.logger.interfaces.ILogger;
import dev.dankom.logger.profiler.Profiler;
import dev.dankom.script.type.method.ScriptMethod;
import dev.dankom.script.type.method.ScriptMethodParameter;
import dev.dankom.script.type.struct.ScriptStructure;
import dev.dankom.script.type.var.ScriptUniformVariable;
import dev.dankom.script.type.var.ScriptVariable;
import dev.dankom.util.general.JavaUtil;
import dev.dankom.util.general.ListUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScriptLoader {

    private Lexer lexer;
    public ScriptHelper helper;

    private final List<Token> tokens = new ArrayList<>();
    private final List<String> lexemes = new ArrayList<>();

    private final List<ScriptStructure> structs = new ArrayList<>();
    private final List<ScriptMethod> methods = new ArrayList<>();
    private final List<ScriptVariable> variables = new ArrayList<>();
    private final List<ScriptUniformVariable> uniforms = new ArrayList<>();

    private ILogger logger;
    private Profiler profiler;

    public void loadFromResource(String pathToFile) {
        if (!pathToFile.contains(".plight")) {
            pathToFile += ".plight";
        }
        loadFile(new File(ScriptLoader.class.getClassLoader().getResource("scripts/" + pathToFile).getPath()));
    }

    public void loadFile(File file) {
        try {
            this.lexer = new Lexer(file);
            this.helper = new ScriptHelper();
            this.logger = LogManager.addLogger(file.getName().replace(".plight", ""), new DefaultLogger());
            this.profiler = LogManager.addProfiler(file.getName().replace(".plight", ""), new Profiler());

            profiler.startSection("find_tokens");
            while (!lexer.isExhausted()) {
                tokens.add(lexer.currentToken());
                lexemes.add(lexer.currentLexeme());
                lexer.next();
            }

            if (!lexer.isSuccessful()) {
                logger.error("Lexer", "Failed to lex!");
                return;
            }

            profiler.startSection("find_structs");
            findStructs();
            profiler.startSection("find_methods");
            findMethods();

            profiler.startSection("find_uniform_vars");
            findUniformVars();
            profiler.startSection("bind_default_uniforms");
            HashMap<String, String> duniforms = new HashMap<>();
            duniforms.put("name", file.getName().replace(".plight", ""));
            duniforms.put("java_version", JavaUtil.version());
            bindUniforms(duniforms);

            profiler.startSection("find_vars");
            findVars();
            profiler.stopSection("find_vars");

            methods.get(0).run();
        } catch (Exception e) {
            profiler.crash("Failed: " + e.getMessage(), e);
        }
    }

    public void findMethods() {
        List<Token> importantTokens = null;
        List<String> importantLexemes = null;
        for (int i = 0; i < tokens.size(); i++) {
            Token t = tokens.get(i);
            String l = lexemes.get(i);
            if (importantTokens == null && t == Token.STRUCT) {
                importantTokens = new ArrayList<>();
                importantLexemes = new ArrayList<>();
                importantTokens.add(t);
                importantLexemes.add(l);
            } else if (importantTokens != null && t == Token.CLOSE_BRACKET) {
                importantTokens.add(t);


                String name = null;
                String returnType = null;
                List<Token> bodyt = null;
                List<String> bodyl = null;

                List<ScriptMethodParameter> pars = null;
                boolean lookingForPars = false;
                boolean hasLookedIn = false;
                String cParName = null;
                String cParType = null;

                for (int j = 0; j < importantTokens.size(); j++) {
                    try {
                        Token ct = importantTokens.get(j);
                        String cl = importantLexemes.get(j);

                        //Parameters
                        if (ct == Token.OPEN && !lookingForPars && !hasLookedIn) {
                            lookingForPars = true;
                            pars = new ArrayList<>();
                            continue;
                        }
                        if (ct == Token.COMMA) {
                            pars.add(new ScriptMethodParameter(this, cParName, cParType, "unset"));
                            cParName = null;
                            cParType = null;
                            continue;
                        }
                        if (ScriptVariable.isValidTokenValue(ct, cl) && cParType == null && lookingForPars) {
                            cParType = cl;
                            continue;
                        }
                        if (ct == Token.IDENTIFIER && cParName == null && lookingForPars) {
                            cParName = cl;
                            continue;
                        }
                        if (ct == Token.CLOSE && lookingForPars) {
                            lookingForPars = false;
                            hasLookedIn = true;
                            pars.add(new ScriptMethodParameter(this, cParName, cParType, "unset"));
                            cParName = null;
                            cParType = null;
                            continue;
                        }
                        //Name and ReturnType
                        if (ct == Token.IDENTIFIER && returnType == null && ScriptMethod.isValidReturnType(ct, cl)) {
                            returnType = cl;
                            continue;
                        }
                        if (ct == Token.IDENTIFIER && name == null) {
                            name = cl;
                            continue;
                        }
                        //Body
                        if (ct == Token.OPEN_BRACKET && bodyt == null) {
                            bodyt = new ArrayList<>();
                            bodyl = new ArrayList<>();
                            continue;
                        }
                        if (ct == Token.CLOSE_BRACKET && bodyt != null) {
                            methods.add(new ScriptMethod(this, name, returnType, pars, bodyt, bodyl));
                            break;
                        }
                        if (bodyt != null) {
                            bodyt.add(ct);
                            bodyl.add(cl);
                        }
                    } catch (IndexOutOfBoundsException e) {
                        methods.add(new ScriptMethod(this, name, returnType, pars, bodyt, bodyl));
                        break;
                    }
                }

                importantTokens = null;
                continue;
            } else if (importantTokens != null) {
                importantTokens.add(t);
                importantLexemes.add(l);
            }
        }
    }

    public void findStructs() {
        List<Token> importantTokens = null;
        List<String> importantLexemes = null;
        for (int i = 0; i < tokens.size(); i++) {
            Token t = tokens.get(i);
            String l = lexemes.get(i);
            if (importantTokens == null && t == Token.STRUCT) {
                importantTokens = new ArrayList<>();
                importantLexemes = new ArrayList<>();
                importantTokens.add(t);
                importantLexemes.add(l);
            } else if (importantTokens != null && t == Token.CLOSE_BRACKET) {
                importantTokens.add(t);


                String name = null;
                List<Token> bodyt = null;
                List<String> bodyl = null;
                for (int j = 0; j < importantTokens.size(); j++) {
                    try {
                        Token ct = importantTokens.get(j);
                        String cl = importantLexemes.get(j);
                        if (ct == Token.IDENTIFIER && name == null && !ScriptMethod.isValidReturnType(ct, cl)) {
                            name = cl;
                            continue;
                        }

                        if (ct == Token.OPEN_BRACKET && bodyt == null) {
                            bodyt = new ArrayList<>();
                            bodyl = new ArrayList<>();
                            continue;
                        }

                        if (ct == Token.CLOSE_BRACKET && bodyt != null) {
                            structs.add(new ScriptStructure(this, name, bodyt, bodyl));
                            break;
                        }

                        if (bodyt != null) {
                            bodyt.add(ct);
                            bodyl.add(cl);
                        }
                    } catch (IndexOutOfBoundsException e) {
                        structs.add(new ScriptStructure(this, name, bodyt, bodyl));
                        break;
                    }
                }

                importantTokens = null;
                continue;
            } else if (importantTokens != null) {
                importantTokens.add(t);
                importantLexemes.add(l);
            }
        }
    }

    public final void findUniformVars() {
        List<Token> importantTokens = null;
        List<String> importantLexemes = null;
        for (int i = 0; i < tokens.size(); i++) {
            Token t = tokens.get(i);
            String l = lexemes.get(i);
            if (importantTokens == null && t == Token.UNIFORM) {
                importantTokens = new ArrayList<>();
                importantLexemes = new ArrayList<>();
                importantTokens.add(t);
                importantLexemes.add(l);
            } else if (importantTokens != null && t == Token.END_LINE) {
                importantTokens.add(t);

                String name = "";
                for (int j = 0; j < importantTokens.size(); j++) {
                    try {
                        Token it = importantTokens.get(j);
                        if (it == Token.UNIFORM && importantTokens.get(j + 1) == Token.OPEN && ScriptUniformVariable.isValidTokenValue(importantTokens.get(j + 2), importantLexemes.get(j + 2)) && importantTokens.get(j + 3) == Token.CLOSE) {
                            name = importantLexemes.get(j + 2);
                            uniforms.add(new ScriptUniformVariable(this, name));
                        }
                    } catch (IndexOutOfBoundsException e) {
                        uniforms.add(new ScriptUniformVariable(this, name));
                        break;
                    }
                }

                importantTokens = null;
                continue;
            } else if (importantTokens != null) {
                importantTokens.add(t);
                importantLexemes.add(l);
            }
        }
    }

    public final void findVars() {
        List<Token> importantTokens = null;
        List<String> importantLexemes = null;
        for (int i = 0; i < tokens.size(); i++) {
            Token t = tokens.get(i);
            String l = lexemes.get(i);
            if (importantTokens == null && t == Token.DEFINE) {
                importantTokens = new ArrayList<>();
                importantLexemes = new ArrayList<>();
                importantTokens.add(t);
                importantLexemes.add(l);
            } else if (importantTokens != null && t == Token.END_LINE) {
                importantTokens.add(t);

                String name = null;
                String type = null;
                String value = null;
                for (int j = 0; j < importantTokens.size() - 1; j++) {
                    try {
                        Token it = importantTokens.get(j);
                        String il = importantLexemes.get(j);

                        if (name == null && it == Token.DEFINE && importantTokens.get(j + 1) == Token.OPEN) {
                            name = importantLexemes.get(j + 2);
                            continue;
                        }

                        if (type == null && it == Token.COMMA && importantTokens.get(j + 1) == Token.IDENTIFIER && importantTokens.get(j + 2) == Token.COMMA) {
                            type = importantLexemes.get(j + 1);
                            continue;
                        }

                        if (value == null && it == Token.COMMA) {
                            value = helper.getValue(this, ListUtil.getSub(importantTokens, j, importantTokens.size()), ListUtil.getSub(importantLexemes, j, importantLexemes.size()));
                            addVar(new ScriptVariable(this, name, type, value));
                        }
                    } catch (IndexOutOfBoundsException e) {
                        addVar(new ScriptVariable(this, name, type, value));
                    }
                }

                importantTokens = null;
                continue;
            } else if (importantTokens != null) {
                importantTokens.add(t);
                importantLexemes.add(l);
            }
        }
    }

    public void addVar(ScriptVariable variable) {
        if (!variables.contains(variable)) {
            variables.add(variable);
        }
    }

    public final void bindUniforms(Map<String, String> map) {
        for (Map.Entry me : map.entrySet()) {
            if (getUniform((String) me.getKey()) != null) {
                bindUniform((String) me.getKey(), (String) me.getValue());
            }
        }
    }

    public final void bindUniform(String name, String value) {
        getUniform(name).setValue(value);
    }

    public final ScriptUniformVariable getUniform(String name) {
        for (ScriptUniformVariable v : uniforms) {
            if (v.getName().equalsIgnoreCase(name)) {
                return v;
            }
        }
        return null;
    }

    public final void setVariableValue(String name, String value) {
        getVariable(name).setValue(value);
    }

    public final String getVariableValue(String name) {
        return getVariable(name).getValue();
    }

    public final ScriptVariable getVariable(String name) {
        for (ScriptVariable v : variables) {
            if (v.getName().equalsIgnoreCase(name)) {
                return v;
            }
        }
        return null;
    }

    public final ScriptMethod getMethod(String name) {
        for (ScriptMethod sm : methods) {
            if (sm.getName().equals(name)) {
                return sm;
            }
        }
        return null;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public List<String> getLexemes() {
        return lexemes;
    }

    public ILogger log() {
        return logger;
    }

    public Profiler profiler() {
        return profiler;
    }

    public static void main(String[] args) {
        ScriptLoader sl = new ScriptLoader();
        sl.loadFromResource("test");
    }
}
