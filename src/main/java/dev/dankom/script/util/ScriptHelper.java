package dev.dankom.script.util;

import dev.dankom.lexer.Token;
import dev.dankom.script.Script;
import dev.dankom.script.type.method.ScriptMethod;
import dev.dankom.script.type.method.ScriptMethodParameter;
import dev.dankom.script.type.var.ScriptUniformVariable;

import java.util.HashMap;
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

    public String getValue(Script loader, HashMap<String, String> pars, List<Token> tokens, List<String> lexemes) {
        String out = null;
        if (tokens != null) {
            if (tokens.isEmpty() || lexemes.isEmpty()) {
                return "null";
            }

            System.out.println("Tokens: " + tokens);
            System.out.println("Lexemes: " + lexemes);

            for (int i = 0; i < tokens.size(); i++) {
                Token t = tokens.get(i);
                String l = lexemes.get(i).replace("\"", "");

                if (t == Token.IDENTIFIER && tokens.get(i + 1) == Token.OPEN) {
                    out = getFullValue(loader, l, pars);
                    System.out.println("Method");
                    System.out.println("Token: " + t);
                    System.out.println("Lexeme: " + l);
                    System.out.println("Out: " + out);
                    System.out.println("----------------");
                    continue;
                }

                if ((t == Token.IDENTIFIER || isInt(l) || isBool(l)) && tokens.get(i + 1) != Token.OPEN) {
                    out = getFullValue(loader, l, new HashMap<>());
                    System.out.println("Var");
                    System.out.println("Token: " + t);
                    System.out.println("Lexeme: " + l);
                    System.out.println("Out: " + out);
                    System.out.println("----------------");
                    continue;
                }
            }
            return out;
        }
        loader.log().error("ValueParser", "Attempted to get value of null!");
        return "null";
    }

    public String getValue(Script loader, List<Token> tokens, List<String> lexemes) {
        return getValue(loader, new HashMap<>(), tokens, lexemes);
    }

    public String getValue(Script loader, List<ScriptMethodParameter> smps, List<Token> tokens, List<String> lexemes) {
        HashMap<String, String> pars = new HashMap<>();
        for (ScriptMethodParameter smp : smps) {
            pars.put(smp.getName(), smp.getValue());
        }
        return getValue(loader, pars, tokens, lexemes);
    }

    public String getFullValue(Script loader, String value, HashMap<String, String> pars) {
        value = value.replace("\"", "");

        ScriptMethod method = loader.getMethod(value);
        if (method != null && !method.getReturnType().equalsIgnoreCase("void")) {
            if (method.getResult(pars) != null) {
                return method.getResult(pars).replace("\"", "");
            } else {
                loader.log().error("Evaluator", "Failed to evaluate the method " + value + "!");
                return "null";
            }
        } else if (loader.getVariable(value) != null) {
            if (loader.getVariableValue(value) != null) {
                return loader.getVariableValue(value);
            } else {
                loader.log().error("Evaluator", "Failed to evaluate the variable " + value + "!");
                return "null";
            }
        } else if (loader.getUniform(value) != null) {
            ScriptUniformVariable uniform = loader.getUniform(value);
            if (uniform != null) {
                if (uniform.getValue() != null) {
                    return uniform.getValue();
                } else {
                    loader.log().error("Evaluator", "Failed to evaluate the uniform " + value + "!");
                    return "null";
                }
            }
        } else if (pars.containsKey(value)) {
            return pars.get(value);
        }

        return value;
    }
}
