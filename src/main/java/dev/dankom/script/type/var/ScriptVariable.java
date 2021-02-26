package dev.dankom.script.type.var;

import dev.dankom.script.exception.ScriptRuntimeException;
import dev.dankom.script.lexer.Lexeme;
import dev.dankom.script.lexer.Token;
import dev.dankom.script.engine.Script;
import dev.dankom.script.interfaces.MemoryBoundStructure;
import dev.dankom.script.pointer.Pointer;
import dev.dankom.script.util.ScriptHelper;
import dev.dankom.util.general.ListUtil;

import java.util.List;

public class ScriptVariable implements MemoryBoundStructure<ScriptVariable> {

    private String name = null;
    private String type = null;
    private List<Lexeme> value = null;
    private Script script;

    private Pointer namePointer = Pointer.NOT_SET;
    private Pointer typePointer = Pointer.NOT_SET;
    private Pointer valuePointer = Pointer.NOT_SET;

    public ScriptVariable(Script script) {
        this.script = script;
    }

    @Override
    public ScriptVariable loadToMemory(List<Lexeme> lexemes) throws ScriptRuntimeException {
        if (!lexemes.isEmpty()) {
            for (int i = 0; i < lexemes.size(); i++) {
                try {
                    Lexeme l = lexemes.get(i);

                    if (lexemes.get(i - 1).getToken() == Token.OPEN && l.getToken() == Token.IDENTIFIER && lexemes.get(i + 1).getToken() == Token.COMMA && name == null) {
                        name = l.getLexeme();
                        script.debug().test("Variable%Binder", "Set name to " + name + "!");
                        this.namePointer = new Pointer(i, l);
                        continue;
                    }

                    if (lexemes.get(i - 1).getToken() == Token.COMMA && l.getToken() == Token.IDENTIFIER && lexemes.get(i + 1).getToken() == Token.COMMA && type == null) {
                        type = l.getLexeme();
                        script.debug().test("Variable%Binder", "Set type to " + type + " for variable " + name + "!");
                        this.typePointer = new Pointer(i, l);
                        continue;
                    }

                    if (lexemes.get(i - 1).getToken() == Token.COMMA && ScriptHelper.isValidParType(l) && value == null) {
                        try {
                            value = ListUtil.getSub(lexemes, i, lexemes.size() - 1);
                        } catch (IndexOutOfBoundsException e) {
                            value = ListUtil.getSub(lexemes, i, lexemes.size());
                        }
                        script.debug().test("Variable%Binder", "Set value to " + value + " for variable " + name + "!");
                        this.valuePointer = new Pointer(i, l);
                        continue;
                    }
                } catch (IndexOutOfBoundsException e) {}
            }

            if (namePointer == Pointer.NOT_SET) {
                script.logger().error("ScriptVariable%MemoryBinder", "Failed to bind name!");
                throw new ScriptRuntimeException("Failed to bind name!", script, namePointer);
            }

            if (typePointer == Pointer.NOT_SET) {
                script.logger().error("ScriptVariable%MemoryBinder", "Failed to bind type for " + name + "!");
                throw new ScriptRuntimeException("Failed to bind type for " + name + "!", script, typePointer);
            }

            if (valuePointer == Pointer.NOT_SET) {
                script.logger().error("ScriptVariable%MemoryBinder", "Failed to bind value for " + name + "!");
                throw new ScriptRuntimeException("Failed to bind value for " + name + "!", script, valuePointer);
            }

            return this;
        } else {
            return null;
        }
    }

    @Override
    public void unload() {
        namePointer = Pointer.UNLOADED;
        typePointer = Pointer.UNLOADED;
        valuePointer = Pointer.UNLOADED;

        name = null;
        type = null;
        value = null;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return ScriptHelper.getFullValue(script, value);
    }

    public void setValue(List<Lexeme> value) {
        this.value = value;
    }

    public Script getScript() {
        return script;
    }

    public Pointer getNamePointer() {
        return namePointer;
    }

    public Pointer getTypePointer() {
        return typePointer;
    }

    public Pointer getValuePointer() {
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
