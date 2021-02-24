package dev.dankom.script.type.method;

import dev.dankom.lexer.Token;
import dev.dankom.script.Script;
import dev.dankom.script.type.struct.ScriptStructure;
import dev.dankom.script.type.var.ScriptVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ScriptMethod extends ScriptStructure {
    private final String returnType;
    private Script parent;
    private List<ScriptMethodParameter> pars;

    private List<Token> retT;
    private List<String> retL;

    public ScriptMethod(Script parent, String name, String returnType, List<ScriptMethodParameter> pars, List<Token> bodyTokens, List<String> bodyLexemes) {
        super(parent, name, bodyTokens, bodyLexemes);
        this.parent = parent;
        this.returnType = returnType;
        this.pars = pars;

        if (pars == null) {
            this.pars = new ArrayList<>();
        }
    }

    public String getResult(HashMap<String, String> pars) {
        if (retT == null || retL == null) {
            run();
        }

        for (ScriptMethodParameter smp : this.pars) {
            if (pars.get(smp.getName()) != null) {
                smp.setValue(pars.get(smp.getName()));
            }
        }

        System.out.println("retT: " + retT);
        System.out.println("retL: " + retL);

        return parent.helper.getValue(parent, this.pars, retT, retL);
    }

    public void setRet(List<Token> tokens, List<String> lexemes) {
        this.retT = tokens;
        this.retL = lexemes;
    }

    @Override
    public void evaluate(List<Token> tokens, List<String> lexemes) {
        System.out.println("Returned with these tokens " + tokens + " and these lexemes " + lexemes + "!");
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
