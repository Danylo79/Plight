package dev.dankom.script;

import dev.dankom.lexer.Token;
import dev.dankom.script.type.method.ScriptMethod;
import dev.dankom.script.type.var.ScriptUniformVariable;

import java.util.List;

public class ScriptHelper {
    public boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isBool(String s) {
        return s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false");
    }

    public int getTokenIndex(List<Token> input, int s, Token token) {
        for (int i = s; i < input.size(); i++) {
            if (input.get(i) == token) {
                return i;
            }
        }
        return -1;
    }

    public String getValue(ScriptLoader loader, List<Token> tokens, List<String> lexemes) {
        String out = null;
        for (int i = 0; i < tokens.size(); i++) {
            Token t = tokens.get(i);
            String l = lexemes.get(i).replace("\"", "");

            if (t == Token.RETURN) {
                continue;
            }

            if (out == null && (t == Token.IDENTIFIER || t == Token.STRING || isBool(l) || isInt(l))) {
                out = getFullValue(loader, l);
            }

            if (t == Token.PLUS) {
                if (isInt(lexemes.get(i + 1).replace("\"", "")) || isInt(out)) {
                    out = String.valueOf(Integer.parseInt(out) + Integer.parseInt(getFullValue(loader, lexemes.get(i + 1).replace("\"", ""))));
                } else {
                    out = out + getFullValue(loader, lexemes.get(i + 1).replace("\"", ""));
                    continue;
                }
            }

            if (t == Token.MINUS) {
                try {
                    out = String.valueOf(Integer.parseInt(out) - Integer.parseInt(getFullValue(loader, lexemes.get(i + 1).replace("\"", ""))));
                } catch (NumberFormatException e) {
                    loader.log().error("ValueParser", "Cannot apply subtract operation to a string!");
                }
            }

            if (t == Token.DIVISION) {
                try {
                    out = String.valueOf(Integer.parseInt(out) / Integer.parseInt(getFullValue(loader, lexemes.get(i + 1).replace("\"", ""))));
                } catch (NumberFormatException e) {
                    loader.log().error("ValueParser", "Cannot apply subtract operation to a string!");
                }
            }

            if (t == Token.MULTIPLY) {
                try {
                    out = String.valueOf(Integer.parseInt(out) * Integer.parseInt(getFullValue(loader, lexemes.get(i + 1).replace("\"", ""))));
                } catch (NumberFormatException e) {
                    loader.log().error("ValueParser", "Cannot apply subtract operation to a string!");
                }
            }
        }
        return out;
    }

    public String getFullValue(ScriptLoader loader, String value) {
        value = value.replace("\"", "");
        ScriptMethod method = loader.getMethod(value);
        if (method != null) {
            if (method.getResult() != null) {
                return method.getResult().replace("\"", "");
            } else {
                loader.log().error("Evaluator", "Failed to evaluate the method " + value + "!");
                return "null";
            }
        }

        else if (loader.getVariable(value) != null) {
            if (loader.getVariableValue(value) != null) {
                return loader.getVariableValue(value);
            } else {
                loader.log().error("Evaluator", "Failed to evaluate the variable " + value + "!");
                return "null";
            }
        }

        else {
            ScriptUniformVariable uniform = loader.getUniform(value);
            if (uniform != null) {
                if (uniform.getValue() != null) {
                    return uniform.getValue();
                } else {
                    loader.log().error("Evaluator", "Failed to evaluate the uniform " + value + "!");
                    return "null";
                }
            }
        }

        return value;
    }
}
