package dev.dankom.script.type.var;

import dev.dankom.lexer.Lexeme;
import dev.dankom.lexer.Token;
import dev.dankom.script.Script;
import dev.dankom.script.interfaces.MemoryBoundStructure;

import java.util.List;

public class ScriptUniform implements MemoryBoundStructure<ScriptUniform> {

    private String name;
    private String value;
    private int namePointer;
    private Script script;

    public ScriptUniform(Script script) {
        this.script = script;
    }

    @Override
    public ScriptUniform loadToMemory(List<Lexeme> lexemes) {
        if (!lexemes.isEmpty()) {
            for (int i = 0; i < lexemes.size(); i++) {
                try {
                    Lexeme l = lexemes.get(i);

                    if (lexemes.get(i - 1).getToken() == Token.OPEN && l.getToken() == Token.IDENTIFIER && lexemes.get(i + 1).getToken() == Token.CLOSE && name == null) {
                        name = l.getLexeme();
                        script.logger().test("Uniform%Binder", "Set uniform key to " + name + "!");
                        this.namePointer = i;
                        continue;
                    }
                } catch (IndexOutOfBoundsException e) {}
            }

            this.value = "unbound";

            if (namePointer == -1) {
                script.logger().error("Uniform%MemoryBinder", "Failed to bind name!");
            }

            return this;
        } else {
            return null;
        }
    }

    public String getName() {
        return name;
    }

    public int getNamePointer() {
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
