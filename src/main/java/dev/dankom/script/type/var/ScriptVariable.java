package dev.dankom.script.type.var;

import dev.dankom.lexer.Lexeme;
import dev.dankom.lexer.Token;
import dev.dankom.script.Script;
import dev.dankom.script.interfaces.MemoryBoundStructure;
import dev.dankom.script.util.ScriptHelper;

import java.util.List;

public class ScriptVariable implements MemoryBoundStructure<ScriptVariable> {

    private String name = null;
    private String type = null;
    private String value = null;
    private Script script;

    private int namePointer = -1;
    private int typePointer = -1;
    private int valuePointer = -1;

    public ScriptVariable(Script script) {
        this.script = script;
    }

    @Override
    public ScriptVariable loadToMemory(List<Lexeme> lexemes) {
        if (!lexemes.isEmpty()) {
            for (int i = 0; i < lexemes.size(); i++) {
                try {
                    Lexeme l = lexemes.get(i);

                    if (lexemes.get(i - 1).getToken() == Token.OPEN && l.getToken() == Token.IDENTIFIER && lexemes.get(i + 1).getToken() == Token.COMMA && name == null) {
                        name = l.getLexeme();
                        script.logger().test("Variable%Binder", "Set name to " + name + "!");
                        this.namePointer = i;
                        continue;
                    }

                    if (lexemes.get(i - 1).getToken() == Token.COMMA && l.getToken() == Token.IDENTIFIER && lexemes.get(i + 1).getToken() == Token.COMMA && type == null) {
                        type = l.getLexeme();
                        script.logger().test("Variable%Binder", "Set type to " + type + " for variable " + name + "!");
                        this.typePointer = i;
                        continue;
                    }

                    if (lexemes.get(i - 1).getToken() == Token.COMMA && ScriptHelper.isValidParType(l) && lexemes.get(i + 1).getToken() == Token.CLOSE && value == null) {
                        value = l.getLexeme().replace("\"", "");
                        script.logger().test("Variable%Binder", "Set value to " + value + " for variable " + name + "!");
                        this.valuePointer = i;
                        continue;
                    }
                } catch (IndexOutOfBoundsException e) {
                }
            }

            if (namePointer == -1) {
                script.logger().error("ScriptVariable%MemoryBinder", "Failed to bind name!");
            }

            if (typePointer == -1) {
                script.logger().error("ScriptVariable%MemoryBinder", "Failed to bind type for " + name + "!");
            }

            if (valuePointer == -1) {
                script.logger().error("ScriptVariable%MemoryBinder", "Failed to bind value for " + name + "!");
            }

            return this;
        } else {
            return null;
        }
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Script getScript() {
        return script;
    }

    public int getNamePointer() {
        return namePointer;
    }

    public int getTypePointer() {
        return typePointer;
    }

    public int getValuePointer() {
        return valuePointer;
    }

    @Override
    public String toString() {
        return "ScriptVariable{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", value='" + value + '\'' +
                ", script=" + script +
                ", namePointer=" + namePointer +
                ", typePointer=" + typePointer +
                ", valuePointer=" + valuePointer +
                '}';
    }

    public static boolean isValidTokenValue(Token t, String lexeme) {
        return lexeme.equalsIgnoreCase("string") || lexeme.equalsIgnoreCase("str") || t.equals(Token.INTEGER) || t.equals(Token.IDENTIFIER);
    }
}
