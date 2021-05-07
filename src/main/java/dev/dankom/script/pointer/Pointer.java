package dev.dankom.script.pointer;

import dev.dankom.lexer.Lexeme;
import dev.dankom.script.lexer.Token;

public class Pointer {
    public static final Pointer NOT_SET = new Pointer(-1, new Lexeme(Token.UNKNOWN, "unknown", -1, -1));
    public static final Pointer UNLOADED = new Pointer(-1, new Lexeme(Token.UNKNOWN, "unloaded", -1, -1));

    private final int index;
    private final Lexeme pointer;

    public Pointer(int index, Lexeme pointer) {
        this.index = index;
        this.pointer = pointer;
    }

    public int getIndex() {
        return index;
    }

    public Lexeme getPointer() {
        return pointer;
    }

    @Override
    public String toString() {
        return "Pointer{" +
                "index=" + (index == -1 ? "?" : index) +
                ", pointer=" + pointer.toString() +
                '}';
    }
}
