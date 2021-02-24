package dev.dankom.lexer;

public class Lexeme {
    private final Token token;
    private final String lexeme;

    public Lexeme(Token token, String lexeme) {
        this.token = token;
        this.lexeme = lexeme;
    }

    public Token getToken() {
        return token;
    }

    public String getLexeme() {
        return lexeme;
    }

    @Override
    public String toString() {
        return "Lexeme{" +
                "token=" + token +
                ", lexeme='" + lexeme + '\'' +
                '}';
    }
}
