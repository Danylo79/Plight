package dev.dankom.script.type.imported;

import dev.dankom.script.engine.Script;
import dev.dankom.script.interfaces.MemoryBoundStructure;
import dev.dankom.script.lexer.Lexeme;
import dev.dankom.script.lexer.Token;
import dev.dankom.script.pointer.Pointer;

import java.util.List;

public class ScriptJavaImport implements MemoryBoundStructure<ScriptJavaImport> {
    private Script script;
    private String spackage;
    private Pointer packagePointer = new Pointer(0, new Lexeme(Token.IMPORT, "return", -1, -1));

    public ScriptJavaImport(Script script) {
        this.script = script;
    }

    @Override
    public ScriptJavaImport loadToMemory(List<Lexeme> lexemes) {
        if (!lexemes.isEmpty()) {
            for (int i = 0; i < lexemes.size(); i++) {
                Lexeme l = lexemes.get(i);

                if (!l.getToken().equals(Token.IMPORT_JAVA)) {
                    spackage += l.getLexeme();
                }
            }

            spackage = spackage.replace("null", "");

            if (spackage == null) {
                script.logger().error("Uniform%MemoryBinder", "Failed to bind package!");
            }

            return this;
        } else {
            return null;
        }
    }

    @Override
    public void unload() {
        spackage = null;
        packagePointer = Pointer.UNLOADED;
    }

    public Class<?> getClazz() {
        try {
            return Class.forName(spackage);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "ScriptImport{" +
                "script=" + script +
                ", spackage='" + spackage + '\'' +
                ", packagePointer=" + packagePointer +
                '}';
    }
}
