package dev.dankom.script.type.method;

import dev.dankom.lexer.Token;
import dev.dankom.script.ScriptLoader;
import dev.dankom.script.type.struct.ScriptStructure;
import dev.dankom.script.type.var.ScriptVariable;

import java.util.List;

public class ScriptMethod extends ScriptStructure {
    private final String returnType;
    private final List<ScriptMethodParameter> pars;

    public ScriptMethod(ScriptLoader parent, String name, String returnType, List<ScriptMethodParameter> pars, List<Token> bodyTokens, List<String> bodyLexemes) {
        super(parent, name, bodyTokens, bodyLexemes);
        this.returnType = returnType;
        this.pars = pars;
    }

    public String getReturnType() {
        return returnType;
    }

    public List<ScriptMethodParameter> getPars() {
        return pars;
    }

    public static boolean isValidReturnType(Token t, String lexeme) {
        return ScriptVariable.isValidTokenValue(t, lexeme) || lexeme.equalsIgnoreCase("void");
    }
}
