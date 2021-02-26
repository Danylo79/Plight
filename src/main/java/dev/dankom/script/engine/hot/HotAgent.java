package dev.dankom.script.engine.hot;

import dev.dankom.script.engine.Script;
import dev.dankom.script.exception.ScriptRuntimeException;
import dev.dankom.script.interfaces.MemoryBoundStructure;
import dev.dankom.script.lexer.Lexeme;
import dev.dankom.script.lexer.Lexer;
import dev.dankom.script.lexer.Token;
import dev.dankom.script.type.imported.ScriptImport;
import dev.dankom.script.type.imported.ScriptJavaImport;
import dev.dankom.script.type.method.ScriptMethod;
import dev.dankom.script.type.method.ScriptMethodParameter;
import dev.dankom.script.type.var.ScriptUniform;
import dev.dankom.script.type.var.ScriptVariable;
import dev.dankom.util.general.ExceptionUtil;

import java.util.ArrayList;
import java.util.List;

public class HotAgent {
    private final Script script;
    private final Injector injector;

    public HotAgent(Script script) {
        this.script = script;
        this.injector = new Injector();
    }

    public void refresh() {
        if (script.isLoaded) {
            script.flush();
            script.rebind();
        }
    }

    public void refreshAll() {
        for (Script s : script.getLoader().scripts()) {
            s.getHotAgent().refresh();
        }
    }

    public Script getScript() {
        return script;
    }

    public Injector getInjector() {
        return injector;
    }

    public class Injector {
        //Advanced
        public void injectCustom(List<Lexeme> lexemes, MemoryBoundStructure mbs) {
            script.debug().info("Script%getHotAgent()%injectCustom()", "Injecting custom structure " + mbs.toString());
            if (lexemes.isEmpty()) {
                ExceptionUtil.throwCompactException(new ScriptRuntimeException("Lexemes is empty!", script));
            }
            script.custom.add(script.bindStructureToMemory(lexemes, mbs));
        }

        public void injectMethod(List<Lexeme> lexemes, ScriptMethod mbs) {
            script.debug().important("Script%getHotAgent()%injectMethod()", "Injecting method structure " + mbs.toString());
            if (lexemes.isEmpty()) {
                ExceptionUtil.throwCompactException(new ScriptRuntimeException("Lexemes is empty!", script));
            }
            script.methods.add(script.bindStructureToMemory(lexemes, mbs));
        }

        public void injectVariable(List<Lexeme> lexemes, ScriptVariable mbs) {
            script.debug().important("Script%getHotAgent()%injectVariable()", "Injecting variable structure " + mbs.toString());
            if (lexemes.isEmpty()) {
                ExceptionUtil.throwCompactException(new ScriptRuntimeException("Lexemes is empty!", script));
            }
            script.variables.add(script.bindStructureToMemory(lexemes, mbs));
        }

        public void injectUniform(List<Lexeme> lexemes, ScriptUniform mbs) {
            script.debug().important("Script%getHotAgent()%injectUniform()", "Injecting uniform structure " + mbs.toString());
            if (lexemes.isEmpty()) {
                ExceptionUtil.throwCompactException(new ScriptRuntimeException("Lexemes is empty!", script));
            }
            script.uniforms.add(script.bindStructureToMemory(lexemes, mbs));
        }

        public void injectImport(List<Lexeme> lexemes, ScriptImport mbs) {
            script.debug().important("Script%getHotAgent()%injectImport()", "Injecting import structure " + mbs.toString());
            if (lexemes.isEmpty()) {
                ExceptionUtil.throwCompactException(new ScriptRuntimeException("Lexemes is empty!", script));
            }
            script.imports.add(script.bindStructureToMemory(lexemes, mbs));
        }

        public void injectJavaImport(List<Lexeme> lexemes, ScriptJavaImport mbs) {
            script.debug().important("Script%getHotAgent()%injectJavaImport()", "Injecting java import structure " + mbs.toString());
            if (lexemes.isEmpty()) {
                ExceptionUtil.throwCompactException(new ScriptRuntimeException("Lexemes is empty!", script));
            }
            script.java_imports.add(script.bindStructureToMemory(lexemes, mbs));
        }

        //Simple
        public void injectImport(String input) {
            List<Lexeme> lexemes = Lexer.simpleLex(input);
            List<Lexeme> importantLexemes = new ArrayList<>();

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
                        injectImport(importantLexemes, new ScriptImport(script));
                        importantLexemes.clear();
                        continue;
                    } else {
                        script.debug().warning("ImportBinder", "Ran into empty lexeme array!");
                    }
                }

                if (listening) {
                    importantLexemes.add(l);
                    continue;
                }
            }
        }

        public void injectJavaImport(String input) {
            List<Lexeme> lexemes = Lexer.simpleLex(input);
            List<Lexeme> importantLexemes = new ArrayList<>();
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
                        injectJavaImport(importantLexemes, new ScriptJavaImport(script));
                        importantLexemes.clear();
                        continue;
                    } else {
                        script.debug().warning("JavaImportBinder", "Ran into empty lexeme array!");
                    }
                }

                if (listening) {
                    importantLexemes.add(l);
                    continue;
                }
            }
        }

        public void injectVariable(String input) {
            List<Lexeme> lexemes = Lexer.simpleLex(input);
            List<Lexeme> importantLexemes = new ArrayList<>();
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
                        injectVariable(importantLexemes, new ScriptVariable(script));
                        importantLexemes.clear();
                        continue;
                    } else {
                        script.debug().warning("VariableBinder", "Ran into empty lexeme array!");
                    }
                }

                if (listening) {
                    importantLexemes.add(l);
                    continue;
                }
            }
        }

        public void injectUniform(String input) {
            List<Lexeme> lexemes = Lexer.simpleLex(input);
            List<Lexeme> importantLexemes = new ArrayList<>();
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
                        injectUniform(importantLexemes, new ScriptUniform(script));
                        importantLexemes.clear();
                        continue;
                    } else {
                        script.debug().warning("UniformBinder", "Ran into empty lexeme array!");
                    }
                }

                if (listening) {
                    importantLexemes.add(l);
                    continue;
                }
            }
        }

        public void injectMethod(String input) {
            List<Lexeme> lexemes = Lexer.simpleLex(input);

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
                            injectMethod(body, new ScriptMethod(script, methodName, returnType, pars));
                            found = false;
                        }

                        if (lookingForBody) {
                            body.add(l);
                        }


                    }
                } catch (IndexOutOfBoundsException e) {}
            }
        }
    }
}
