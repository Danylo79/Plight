package dev.dankom.type;

public class Token implements java.io.Serializable, Comparable<String>, CharSequence {

    public static Token QUOTE = new Token("\"");
    public static Token JSON_QUOTE = new Token(QUOTE.token());

    private String token;

    public Token(String token) {
        this.token = token;
    }

    @Override
    public int length() {
        return token.length();
    }

    @Override
    public char charAt(int index) {
        return token.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return token.substring(start, end);
    }

    @Override
    public int compareTo(String o) {
        return token.compareTo(o);
    }

    public String token() {
        return token;
    }

    public char tokenChar() {
        return token.charAt(0);
    }
}
