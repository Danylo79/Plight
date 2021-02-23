package dev.dankom.script.type.struct;

import dev.dankom.lexer.Token;

import java.util.List;

public class ScriptStructure {
    private final String name;
    private final List<Token> body;
    private final List<String> bodyLexemes;

    public ScriptStructure(String name, List<Token> bodyTokens, List<String> bodyLexemes) {
        this.name = name;
        this.body = bodyTokens;
        this.bodyLexemes = bodyLexemes;
    }

    public String getName() {
        return name;
    }

    public List<Token> getBody() {
        return body;
    }

    public List<String> getBodyLexemes() {
        return bodyLexemes;
    }

    @Override
    public String toString() {
        return "ScriptStructure{" +
                "name='" + name + '\'' +
                ", body=" + body +
                ", bodyLexemes=" + bodyLexemes +
                '}';
    }
}
