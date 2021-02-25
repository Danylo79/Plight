package dev.dankom.script.lexer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Token {
    MINUS("-"),
    PLUS("\\+"),
    MULTIPLY("\\*"),
    DIVISION("/"),
    MOD("%"),
    NOT("~"),
    AND("&"),
    OR("\\|"),
    LESS("<"),
    LEG("<="),
    GT(">"),
    GEQ(">="),
    EQUALS("=="),
    ASSIGN("="),
    OPEN("\\("),
    CLOSE("\\)"),
    END_LINE(";"),
    COMMA(","),
    DEFINE("define"),
    UNIFORM("uniform"),
    STRUCT("struct"),
    RETURN("return"),
    IMPORT("import"),
    IMPORT_JAVA("jimport"),
    IMPORT_SEPARATOR("\\."),
    AS("as"),
    IS("is"),
    IF("if"),
    THEN("then"),
    ELSE("else"),
    ENDIF("endif"),
    OPEN_BRACKET("\\{"),
    CLOSE_BRACKET("\\}"),
    DIFFERENT("<>"),

    STRING("\"[^\"]+\""),
    INTEGER("\\d+"),

    UNKNOWN(""),

    IDENTIFIER("\\w+");

    private final Pattern pattern;

    Token(String regex) {
        pattern = Pattern.compile("^" + regex);
    }

    int endOfMatch(String s) {
        Matcher m = pattern.matcher(s);

        if (m.find()) {
            return m.end();
        }
        return -1;
    }
}
