package dev.dankom.script.util;

import dev.dankom.lexer.Lexeme;
import dev.dankom.lexer.Token;
import dev.dankom.script.Script;

import java.util.List;

public class ScriptHelper {

    public static boolean isBool(Lexeme l) {
        return l.getLexeme().equalsIgnoreCase("true") || l.getLexeme().equalsIgnoreCase("false");
    }

    public static boolean isInt(Lexeme l) {
        try {
            Integer.parseInt(l.getLexeme());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String getFullValue(Script loader, List<Lexeme> lexemes) {
        String out = "";
        for (Lexeme l : lexemes) {
            if (l.getToken() == Token.IDENTIFIER || isValidParType(l)) {
                out = getValue(loader, l.getLexeme());
            }
        }
        return out;
    }

    public static String getValue(Script loader, String lexeme) {
        if (loader.getVariable(lexeme) != null) {
            return loader.getVariable(lexeme).getValue();
        }
        if (loader.getUniform(lexeme) != null) {
            return loader.getUniform(lexeme).getValue();
        }
        return lexeme;
    }

    public static boolean isValidParType(Lexeme l) {
        return l.getToken().equals(Token.IDENTIFIER) || l.getToken().equals(Token.STRING) || isBool(l) || isInt(l);
    }
}
