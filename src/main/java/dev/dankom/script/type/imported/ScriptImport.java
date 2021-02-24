package dev.dankom.script.type.imported;

public class ScriptImport {
    private final String cpackage;

    public ScriptImport(String cpackage) {
        this.cpackage = cpackage;
    }

    public String getCpackage() {
        return cpackage;
    }
}
