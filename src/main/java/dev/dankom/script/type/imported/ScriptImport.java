package dev.dankom.script.type.imported;

import dev.dankom.script.Script;

public class ScriptImport {
    private final Script parent;
    private final String cpackage;

    public ScriptImport(Script parent, String cpackage) {
        this.parent = parent;
        this.cpackage = cpackage;
    }

    public String getCpackage() {
        return cpackage;
    }

    public Script getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return "ScriptImport{" +
                "parent=" + parent +
                ", cpackage='" + cpackage + '\'' +
                '}';
    }
}
