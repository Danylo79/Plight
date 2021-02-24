package dev.dankom.script.type.method;

import dev.dankom.lexer.Token;
import dev.dankom.script.Script;
import dev.dankom.script.type.struct.ScriptStructure;
import dev.dankom.script.type.var.ScriptVariable;

import java.util.List;

public class ScriptMethod extends ScriptStructure {
    private final String returnType;
    private Script parent;
    private final List<ScriptMethodParameter> pars;

    private List<Token> retT;
    private List<String> retL;

    public ScriptMethod(Script parent, String name, String returnType, List<ScriptMethodParameter> pars, List<Token> bodyTokens, List<String> bodyLexemes) {
        super(parent, name, bodyTokens, bodyLexemes);
        this.parent = parent;
        this.returnType = returnType;
        this.pars = pars;
    }

    public String getResult() {
        if (retT == null || retL == null) {
            run();
        }
        return parent.helper.getValue(parent, retT, retL);
    }

    public void setRet(List<Token> tokens, List<String> lexemes) {
        this.retT = tokens;
        this.retL = lexemes;
    }

    @Override
    public void evaluate(List<Token> tokens, List<String> lexemes) {
        setRet(tokens, lexemes);
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
