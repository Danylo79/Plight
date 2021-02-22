package dev.dankom.script;

import java.util.HashMap;

public enum Type {
    //Define
    END_LINE(";"),

    OPEN_BODY("{"), CLOSE_BODY("}"),

    OPEN_PARAMETERS("("), CLOSE_PARAMETERS(")"),

    OPEN_STRUCT("["), CLOSE_STRUCT("]"),

    OPEN_VAR_REFERENCE("<"), CLOSE_VAR_REFERENCE(">"),

    SEPARATOR(","),
    AND("&"),
    OR("||"),
    AT("@"),
    CONNECT("#"),
    //Math
    PLUS("+"),
    MINUS("-"),
    DIVIDE("/"),
    MULTIPLY("*"),
    PERC("%"),
    EQUALS("="),
    EQUALS_COLON(":"),
    //Type
    STRING_QUOTE("\""),
    //Important
    INVALID("❓"),
    //Define
    STRUCT("struct"),
    FILE_DEFAULT_STRUCT("file_default"),
    START_STRUCT("start"),
    VAR("define"),
    UNIFORM("uniform"),
    ;

    private final String token;
    private final String key;

    Type(String token) {
        this.token = token;
        this.key = name();
    }

    Type(Type token) {
        this(token.getToken());
    }

    public String getToken() {
        return token;
    }

    public String getKey() {
        return key;
    }

    public static Type get(String c) {
        for (Type t : values()) {
            if (t.getToken().equals(c)) {
                return t;
            }
        }
        return null;
    }

    public static Type getNoNull(String c) {
        for (Type t : values()) {
            if (t.getToken().equals(c)) {
                return t;
            }
        }
        return INVALID;
    }
}
