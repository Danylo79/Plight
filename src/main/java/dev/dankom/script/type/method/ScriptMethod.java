package dev.dankom.script.type.method;

import dev.dankom.script.exception.ScriptRuntimeException;
import dev.dankom.lexer.Lexeme;
import dev.dankom.script.lexer.Token;
import dev.dankom.script.engine.Script;
import dev.dankom.script.interfaces.MemoryBoundStructure;
import dev.dankom.script.pointer.Pointer;
import dev.dankom.script.type.var.ScriptVariable;
import dev.dankom.script.util.ScriptHelper;

import java.util.ArrayList;
import java.util.List;

public class ScriptMethod implements MemoryBoundStructure<ScriptMethod> {

    private String name;
    private String returnType;
    private List<ScriptMethodParameter> pars;
    private Script script;

    public List<ScriptMethodCall> methodCalls = new ArrayList<>();
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
                    methodCalls.add(getScript().bindStructureToMemory(storage, new ScriptMethodCall(this)));
                    storage.clear();
                }
            }
            return this;
        } else {
            return null;
        }
    }

    @Override
    public void unload() {
        name = null;
        returnType = null;
        returnPointer = Pointer.UNLOADED;
        for (ScriptMethodCall smc : methodCalls) {
            smc.unload();
        }
    }

    public String call(List<String> pars) {
        List<String> parsedPars = new ArrayList<>();
        for (String par : pars) {
            parsedPars.add(ScriptHelper.getValue(getScript(), par));
        }

        for (ScriptMethodCall smc : methodCalls) {
            getScript().debug().debug("ScriptMethod%call()", "Running method call " + smc.toString());
            String returned = smc.call();
            if (!returned.equalsIgnoreCase("void")) {
                return returned;
            }
        }
        return "null";
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
