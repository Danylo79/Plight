package dev.dankom.script.type.imported;

import dev.dankom.lexer.Lexeme;
import dev.dankom.lexer.Token;
import dev.dankom.script.Script;
import dev.dankom.script.interfaces.MemoryBoundStructure;

import java.util.List;

public class ScriptImport implements MemoryBoundStructure<ScriptImport> {

    private Script script;
    private String spackage;
    private int packagePointer = 1;

    public ScriptImport(Script script) {
        this.script = script;
    }

    @Override
    public ScriptImport loadToMemory(List<Lexeme> lexemes) {
        if (!lexemes.isEmpty()) {
            for (int i = 0; i < lexemes.size(); i++) {
                Lexeme l = lexemes.get(i);

                if (!l.getToken().equals(Token.IMPORT)) {
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

    public String getPackage() {
        return spackage;
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
