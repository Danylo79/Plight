package dev.dankom.script.type.method;

import dev.dankom.script.exception.ScriptRuntimeException;
import dev.dankom.script.lexer.Lexeme;
import dev.dankom.script.lexer.Token;
import dev.dankom.script.engine.Script;
import dev.dankom.script.interfaces.MemoryBoundStructure;
import dev.dankom.script.pointer.Pointer;
import dev.dankom.script.type.var.ScriptVariable;
import dev.dankom.script.util.ScriptHelper;
import javassist.expr.MethodCall;

import java.util.ArrayList;
import java.util.List;

public class ScriptMethod implements MemoryBoundStructure<ScriptMethod> {

    private final String name;
    private final String returnType;
    private final List<ScriptMethodParameter> pars;
    private final Script script;

    private List<ScriptMethodCall> methodCalls = new ArrayList<>();
    private Pointer returnPointer = Pointer.NOT_SET;

    public ScriptMethod(Script script, String name, String returnType, List<ScriptMethodParameter> pars) {
        this.name = name;
        this.returnType = returnType;
        this.pars = pars;
        this.script = script;
    }

    @Override
    public ScriptMethod loadToMemory(List<Lexeme> lexemes) throws ScriptRuntimeException {
        if (!lexemes.isEmpty()) {
            List<Lexeme> storage = new ArrayList<>();
            for (int i = 0; i < lexemes.size(); i++) {

                if (!ScriptHelper.hasReturn(lexemes)) {
                    throw new ScriptRuntimeException("No return statement in " + name + "!", script, returnPointer);
                }

                Lexeme l = lexemes.get(i);

                storage.add(l);

                if (l.getToken() == Token.RETURN) {
                    returnPointer = new Pointer(i, l);
                }

                if (l.getToken() == Token.END_LINE) {
                    methodCalls.add(getScript().loadToMemory(storage, new ScriptMethodCall(this)));
                    storage.clear();
                }
            }
            return this;
        } else {
            return null;
        }
    }

    public void call() {
        for (ScriptMethodCall smc : methodCalls) {
            smc.call();
        }
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
                ", returnPointer=" + returnPointer +
                '}';
    }

    public String getReturn() {
        return "@UnderConstruction";
    }

    public String getName() {
        return name;
    }

    public String getReturnType() {
        return returnType;
    }

    public Script getScript() {
        return script;
    }
}
