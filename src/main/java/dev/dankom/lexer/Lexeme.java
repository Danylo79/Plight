package dev.dankom.lexer;

import dev.dankom.script.lexer.Token;

public class Lexeme {
    private final Token token;
    private final String lexeme;

    private final int line;
    private final int pos;

    public Lexeme(Token token, String lexeme, int pos, int line) {
        this.token = token;
        this.lexeme = lexeme;

        this.pos = pos;
        this.line = line;
    }

    public Token getToken() {
        return token;
    }

    public String getLexeme() {
        return lexeme;
    }

    public int getLine() {
        return line;
    }

    public int getPos() {
        return pos;
    }

    public String line() {
        return (line == -1 ? "?" : "" + line);
    }

    public String pos() {
        return (pos == -1 ? "?" : "" + pos);
    }

    @Override
    public String toString() {
        return "Lexeme{" +
                "token=" + token +
                ", lexeme='" + lexeme + '\'' +
                ", line=" + line() +
                ", pos=" + pos() +
                '}';
    }
}
