package dev.dankom.script.type.method;

import dev.dankom.lexer.Lexeme;
import dev.dankom.lexer.Token;
import dev.dankom.script.Script;
import dev.dankom.script.interfaces.MemoryBoundStructure;
import dev.dankom.script.type.var.ScriptVariable;
import dev.dankom.util.general.ListUtil;

import java.util.ArrayList;
import java.util.List;

public class ScriptMethod implements MemoryBoundStructure<ScriptMethod> {

    private String name;
    private List<ScriptMethodParameter> pars;
    private Script script;

    private List<ScriptMethodCall> methodCalls = new ArrayList<>();
    private int returnPointer = -1;

    public ScriptMethod(Script script, String name, List<ScriptMethodParameter> pars) {
        this.name = name;
        this.pars = pars;
        this.script = script;
    }

    @Override
    public ScriptMethod loadToMemory(List<Lexeme> lexemes) {
        if (!lexemes.isEmpty()) {
            for (int i = 0; i < lexemes.size(); i++) {
                Lexeme l = lexemes.get(i);

                if (l.getToken() == Token.RETURN) {
                    returnPointer = i;
                }

                List<List<Lexeme>> lines = split(lexemes, new Lexeme(Token.END_LINE, ";"));
                System.out.println(lines);
            }
            return this;
        } else {
            return null;
        }
    }

    public List<List<Lexeme>> split(List<Lexeme> input, Lexeme object) {
        List<List<Lexeme>> out = new ArrayList<>();
        List<Lexeme> storage = new ArrayList<>();
        for (Lexeme o : input) {
            storage.add(o);
            if (o.getToken() == object.getToken() && o.getLexeme().equalsIgnoreCase(object.getLexeme())) {
                out.add(storage);
                for (int i = 0 ; i < storage.size(); i++) {
                    storage.remove(i);
                }
            }
        }
        return out;
    }

    public static boolean isValidReturnType(Token t, String lexeme) {
        return ScriptVariable.isValidTokenValue(t, lexeme) || lexeme.equalsIgnoreCase("void");
    }

    @Override
    public String toString() {
        return "ScriptMethod{" +
                "name='" + name + '\'' +
                ", pars=" + pars +
                ", script=" + script +
                ", methodCalls=" + methodCalls +
                ", returnPointer=" + returnPointer +
                '}';
    }
}
