package dev.dankom.script.type.struct;

import dev.dankom.lexer.Token;
import dev.dankom.script.Script;

import java.util.ArrayList;
import java.util.List;

public class ScriptStructure {
    private Script parent;
    private final String name;
    private final List<Token> bodyTokens;
    private final List<String> bodyLexemes;

    private int returnPointer = -1;
    private List<Token> returnStatement;


    private List<List<Token>> bodyT = new ArrayList<>();
    private List<List<String>> bodyL = new ArrayList<>();

    public ScriptStructure(Script parent, String name, List<Token> bodyTokens, List<String> bodyLexemes) {
        this.parent = parent;
        this.name = name;
        this.bodyTokens = bodyTokens;
        this.bodyLexemes = bodyLexemes;
        validate();
    }

    public void validate() {
        for (int i = 0; i < bodyTokens.size(); i++) {
            Token t = bodyTokens.get(i);
            String l = bodyLexemes.get(i);

            if (t == Token.RETURN) {
                returnPointer = i;
            }
        }
    }

    public void run() {

    }

    public void runInside(List<Token> tokens, List<String> lexemes) {

    }

    public String getName() {
        return name;
    }

    public List<Token> getBodyTokens() {
        return bodyTokens;
    }

    public List<String> getBodyLexemes() {
        return bodyLexemes;
    }

    @Override
    public String toString() {
        return "ScriptStructure{" +
                "name='" + name + '\'' +
                ", body=" + bodyTokens +
                ", bodyLexemes=" + bodyLexemes +
                '}';
    }
}
