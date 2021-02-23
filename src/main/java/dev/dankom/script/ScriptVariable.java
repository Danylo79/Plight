package dev.dankom.script;

import dev.dankom.lexer.Token;

public class ScriptVariable {
    private final String name;
    private final String type;
    private String value;

    public ScriptVariable(String name, String type, String value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isInt() {
        return getType().equalsIgnoreCase("integer") || getType().equalsIgnoreCase("int");
    }

    public boolean isString() {
        return getType().equalsIgnoreCase("string") || getType().equalsIgnoreCase("str");
    }

    public boolean isBoolean() {
        return getType().equalsIgnoreCase("boolean") || getType().equalsIgnoreCase("bool");
    }

    public String strVal() {
        return getValue();
    }

    public int intVal() {
        return Integer.parseInt(getValue());
    }

    public boolean boolVal() {
        return Boolean.getBoolean(getValue());
    }

    @Override
    public String toString() {
        return "ScriptVariable{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public static boolean isValidTokenValue(Token t) {
        return t.equals(Token.STRING) || t.equals(Token.INTEGER) || t.equals(Token.IDENTIFIER);
    }
}
