package dev.dankom.script.util;

import dev.dankom.lexer.Lexeme;
import dev.dankom.script.lexer.Token;
import dev.dankom.script.engine.Script;

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
        for (int i = 0; i < lexemes.size(); i++) {
            try {
                Lexeme l = lexemes.get(i);
                if (l.getToken() == Token.IDENTIFIER || isValidParType(l)) {
                    out = getValue(loader, l.getLexeme());
                }
            } catch (IndexOutOfBoundsException e) {}
        }
        return out.replace("\"", "");
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

    public static String getMethodValue(Script loader, String name, List<String> pars) {
        return loader.getMethod(name).call(pars);
    }

    public static boolean hasReturn(List<Lexeme> lexemes) {
        for (Lexeme l : lexemes) {
            if (l.getToken() == Token.RETURN) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidParType(Lexeme l) {
        return l.getToken().equals(Token.IDENTIFIER) || l.getToken().equals(Token.STRING) || isBool(l) || isInt(l);
    }
}
