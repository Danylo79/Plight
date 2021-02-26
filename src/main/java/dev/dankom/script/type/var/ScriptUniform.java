package dev.dankom.script.type.var;

import dev.dankom.script.exception.ScriptRuntimeException;
import dev.dankom.script.lexer.Lexeme;
import dev.dankom.script.lexer.Token;
import dev.dankom.script.engine.Script;
import dev.dankom.script.interfaces.MemoryBoundStructure;
import dev.dankom.script.pointer.Pointer;

import java.util.List;

public class ScriptUniform implements MemoryBoundStructure<ScriptUniform> {

    private String name;
    private String value;
    private Pointer namePointer = Pointer.NOT_SET;
    private Script script;

    public ScriptUniform(Script script) {
        this.script = script;
    }

    @Override
    public ScriptUniform loadToMemory(List<Lexeme> lexemes) throws ScriptRuntimeException {
        if (!lexemes.isEmpty()) {
            for (int i = 0; i < lexemes.size(); i++) {
                try {
                    Lexeme l = lexemes.get(i);

                    if (lexemes.get(i - 1).getToken() == Token.OPEN && l.getToken() == Token.IDENTIFIER && lexemes.get(i + 1).getToken() == Token.CLOSE && name == null) {
                        name = l.getLexeme();
                        script.debug().debug("Uniform%Binder", "Set uniform key to " + name + "!");
                        this.namePointer = new Pointer(i, l);
                        continue;
                    }
                } catch (IndexOutOfBoundsException e) {}
            }

            this.value = "unbound";

            if (namePointer == Pointer.NOT_SET) {
                script.logger().error("Uniform%MemoryBinder", "Failed to bind name!");
                throw new ScriptRuntimeException("Failed to bind name!", script, namePointer);
            }

            return this;
        } else {
            return null;
        }
    }

    @Override
    public void unload() {
        name = null;
        namePointer = Pointer.UNLOADED;
        value = null;
    }

    public String getName() {
        return name;
    }

    public Pointer getNamePointer() {
        return namePointer;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ScriptUniform{" +
                "name='" + name + '\'' +
                ", namePointer=" + namePointer +
                ", script=" + script +
                '}';
    }
}
