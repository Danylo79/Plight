package dev.dankom.script.type.method;

import dev.dankom.script.lexer.Lexeme;
import dev.dankom.script.lexer.Token;
import dev.dankom.script.interfaces.MemoryBoundStructure;
import dev.dankom.script.util.ScriptHelper;

import java.util.ArrayList;
import java.util.List;

public class ScriptMethodCall implements MemoryBoundStructure<ScriptMethodCall> {

    private List<Integer> openBracketsPointers = new ArrayList<>();
    private List<Integer> closeBracketPointers = new ArrayList<>();
    private List<Lexeme> lexemes;

    private String method;
    private List<List<Lexeme>> pars = new ArrayList<>();

    private boolean isReturn = false;
    private ScriptMethod parent;

    public ScriptMethodCall(ScriptMethod parent) {
        this.parent = parent;
    }

    @Override
    public ScriptMethodCall loadToMemory(List<Lexeme> lexemes) {
        this.lexemes = lexemes;
        boolean lookingForPars = false;
        List<Lexeme> pars = new ArrayList<>();

        if (!lexemes.isEmpty()) {
            for (int i = 0; i < lexemes.size(); i++) {
                Lexeme l = lexemes.get(i);
                if (l.getToken() == Token.IDENTIFIER && lexemes.get(i + 1).getToken() == Token.OPEN) {
                    method = l.getLexeme();
                }

                if (l.getToken() == Token.OPEN) {
                    openBracketsPointers.add(i);

                    if (openBracketsPointers.size() == 1) {
                        lookingForPars = true;
                        continue;
                    }
                }

                if (l.getToken() == Token.CLOSE) {
                    closeBracketPointers.add(i);

                    if (i == lexemes.size() - 2) {
                        lookingForPars = false;
                        addPar(pars);
                        pars.removeAll(pars);
                        continue;
                    }
                }

                if (l.getToken() == Token.COMMA) {
                    addPar(pars);
                    pars.removeAll(pars);
                    continue;
                }

                if (lookingForPars) {
                    pars.add(l);
                }
            }
            return this;
        } else {
            return null;
        }
    }

    public void call() {
        try {
            List<String> pars = new ArrayList<>();
            for (List<Lexeme> ls : this.pars) {
                pars.add(ScriptHelper.getFullValue(parent.getScript(), ls));
            }
            if (method.equalsIgnoreCase("info")) {
                parent.getScript().logger().info(pars.get(0), pars.get(1));
            }
        } catch (NullPointerException e) {}
    }

    public void addPar(List<Lexeme> pars) {
        this.pars.add(pars);
    }

    public List<List<Lexeme>> getPars() {
        return this.pars;
    }

    public boolean isReturn() {
        return isReturn;
    }

    @Override
    public String toString() {
        return "ScriptMethodCall{" +
                "openBracketsPointers=" + openBracketsPointers +
                ", closeBracketPointers=" + closeBracketPointers +
                ", lexemes=" + lexemes +
                ", method='" + method + '\'' +
                ", pars=" + pars +
                ", isReturn=" + isReturn +
                ", parent=" + parent +
                '}';
    }
}
