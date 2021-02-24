package dev.dankom.script.util;

import dev.dankom.lexer.Token;
import dev.dankom.script.Script;
import dev.dankom.script.type.method.ScriptMethod;
import dev.dankom.script.type.var.ScriptUniformVariable;
import dev.dankom.script.type.var.ScriptVariable;

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

    public String getValue(Script loader, List<Token> tokens, List<String> lexemes) {
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
                    loader.log().error("ValueParser", "Cannot apply - operation to a string!");
                }
            }

            if (t == Token.DIVISION) {
                try {
                    out = String.valueOf(Integer.parseInt(out) / Integer.parseInt(getFullValue(loader, lexemes.get(i + 1).replace("\"", ""))));
                } catch (NumberFormatException e) {
                    loader.log().error("ValueParser", "Cannot apply / operation to a string!");
                }
            }

            if (t == Token.MULTIPLY) {
                try {
                    out = String.valueOf(Integer.parseInt(out) * Integer.parseInt(getFullValue(loader, lexemes.get(i + 1).replace("\"", ""))));
                } catch (NumberFormatException e) {
                    loader.log().error("ValueParser", "Cannot apply * operation to a string!");
                }
            }

            if (t == Token.MOD) {
                try {
                    out = String.valueOf(Integer.parseInt(out) % Integer.parseInt(getFullValue(loader, lexemes.get(i + 1).replace("\"", ""))));
                } catch (NumberFormatException e) {
                    loader.log().error("ValueParser", "Cannot apply % operation to a string!");
                }
            }
        }
        return out;
    }

    public String getFullValue(Script loader, String value) {
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

        else if (loader.getUniform(value) != null) {
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

        else {
            for (Script s : loader.getParent().getScripts()) {
                ScriptMethod m = s.getMethod(value);
                if (m != null) {
                    if (m.getResult() != null) {
                        return m.getResult().replace("\"", "");
                    } else {
                        s.log().error("Evaluator", "Failed to evaluate the method " + value + "!");
                        return "null";
                    }
                }

                else if (s.getVariable(value) != null) {
                    if (s.getVariableValue(value) != null) {
                        return s.getVariableValue(value);
                    } else {
                        s.log().error("Evaluator", "Failed to evaluate the variable " + value + "!");
                        return "null";
                    }
                }

                else if (s.getUniform(value) != null) {
                    ScriptUniformVariable uniform = s.getUniform(value);
                    if (uniform != null) {
                        if (uniform.getValue() != null) {
                            return uniform.getValue();
                        } else {
                            s.log().error("Evaluator", "Failed to evaluate the uniform " + value + "!");
                            return "null";
                        }
                    }
                }
            }
        }

        return value;
    }
}
