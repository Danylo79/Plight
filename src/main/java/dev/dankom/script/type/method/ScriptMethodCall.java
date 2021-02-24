package dev.dankom.script.type.method;

import dev.dankom.lexer.Lexeme;
import dev.dankom.lexer.Token;
import dev.dankom.script.interfaces.MemoryBoundStructure;

import java.util.ArrayList;
import java.util.List;

public class ScriptMethodCall implements MemoryBoundStructure<ScriptMethodCall> {

    private List<Integer> openBracketsPointers = new ArrayList<>();
    private List<Integer> closeBracketPointers = new ArrayList<>();
    private List<Lexeme> lexemes;

    private String method;
    private List<List<Lexeme>> pars = new ArrayList<>();

    public ScriptMethodCall(List<Lexeme> lexemes) {
        this.lexemes = lexemes;
    }

    @Override
    public ScriptMethodCall loadToMemory(List<Lexeme> lexemes) {
        boolean lookingForPars = false;
        List<Lexeme> pars = new ArrayList<>();

        if (!lexemes.isEmpty()) {
            for (int i = 0; i < lexemes.size(); i++) {
                Lexeme l = lexemes.get(i);
                if (l.getToken() == Token.IDENTIFIER && lexemes.get(i + 1).getToken() == Token.OPEN) {
                    method = l.getLexeme();
                    System.out.println(method + ": " + lexemes);
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

                    if (i == closeBracketPointers.size() - 1) {
                        lookingForPars = false;
                        continue;
                    }
                }

                if (l.getToken() == Token.COMMA || i == lexemes.size()) {
                    addPar(pars);
                    pars.clear();
                    System.out.println(pars);
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

    public void addPar(List<Lexeme> pars) {
        this.pars.add(pars);
    }

    public List<List<Lexeme>> getPars() {
        return this.pars;
    }
}
