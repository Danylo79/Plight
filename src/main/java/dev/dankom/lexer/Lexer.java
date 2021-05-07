package dev.dankom.lexer;

import dev.dankom.script.lexer.Token;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Stream;

public class Lexer {
    private StringBuilder input = new StringBuilder();
    private Token token;
    private String lexeme;
    private boolean exhausted = false;
    private String errorMessage = "";
    private Set<Character> blankChars = new HashSet<Character>();

    private int line;
    private int pos;

    public Lexer(File file) {
        try (Stream<String> st = Files.lines(file.toPath())) {
            st.forEach(input::append);
        } catch (IOException ex) {
            exhausted = true;
            errorMessage = "Could not read file: " + file;
            return;
        }

        blankChars.add('\r');
        blankChars.add('\n');
        blankChars.add((char) 8);
        blankChars.add((char) 9);
        blankChars.add((char) 11);
        blankChars.add((char) 12);
        blankChars.add((char) 32);

        next();
    }

    public Lexer(String s) {
        input.append(s);

        blankChars.add('\r');
        blankChars.add('\n');
        blankChars.add((char) 8);
        blankChars.add((char) 9);
        blankChars.add((char) 11);
        blankChars.add((char) 12);
        blankChars.add((char) 32);

        next();
    }

    public void next() {
        if (exhausted) {
            return;
        }

        if (input.length() == 0) {
            exhausted = true;
            return;
        }

        ignoreWhiteSpaces();

        if (findNextToken()) {
            return;
        }

        exhausted = true;

        if (input.length() > 0) {
            errorMessage = "Unexpected symbol: '" + input.charAt(0) + "'";
        }
    }

    private void ignoreWhiteSpaces() {
        int charsToDelete = 0;

        while (blankChars.contains(input.charAt(charsToDelete))) {
            charsToDelete++;
        }

        if (charsToDelete > 0) {
            input.delete(0, charsToDelete);
        }
    }

    private boolean findNextToken() {
        for (Token t : Token.values()) {
            if (t.equals(Token.UNKNOWN)) {
                continue;
            }
            int end = t.endOfMatch(input.toString());

            if (end != -1) {
                token = t;
                lexeme = input.substring(0, end);
                input.delete(0, end);
                pos++;
                if (t == Token.END_LINE || t == Token.CLOSE_BRACKET || t == Token.OPEN_BRACKET) {
                    line++;
                }
                return true;
            }
        }

        return false;
    }

    public Token currentToken() {
        return token;
    }

    public String currentLexeme() {
        return lexeme;
    }

    public int currentPos() {
        return pos;
    }

    public int currentLine() {
        return line;
    }

    public boolean isSuccessful() {
        return errorMessage.isEmpty();
    }

    public String errorMessage() {
        return errorMessage;
    }

    public boolean isExhausted() {
        return exhausted;
    }

    public static List<Lexeme> simpleLex(String s) {
        Lexer l = new Lexer(s);
        List<Lexeme> out = new ArrayList<>();
        while (!l.isExhausted()) {
            out.add(new Lexeme(l.currentToken(), l.currentLexeme(), l.currentPos(), l.currentLine()));
            l.next();
        }
        return out;
    }

    public static List<Lexeme> advancedLex(File f) {
        Lexer l = new Lexer(f);
        List<Lexeme> out = new ArrayList<>();
        while (!l.isExhausted()) {
            out.add(new Lexeme(l.currentToken(), l.currentLexeme(), l.currentPos(), l.currentLine()));
            l.next();
        }
        return out;
    }
}
