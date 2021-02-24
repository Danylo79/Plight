package dev.dankom.script.type.imported;

import dev.dankom.script.Script;

public class ScriptJavaImport extends ScriptImport {
    public ScriptJavaImport(Script parent, String cpackage) {
        super(parent, cpackage);
    }

    public Class<?> getTargetClass() {
        try {
            return Class.forName(getCpackage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        getParent().log().error("JavaImport", "Could not find class " + getCpackage() + "!");
        return null;
    }
}
