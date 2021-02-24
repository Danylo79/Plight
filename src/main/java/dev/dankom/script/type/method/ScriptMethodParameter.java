package dev.dankom.script.type.method;

import dev.dankom.script.Script;
import dev.dankom.script.type.var.ScriptVariable;

public class ScriptMethodParameter extends ScriptVariable {
    public ScriptMethodParameter(Script parent, String name, String type) {
        super(parent, name, type, null);
    }
}
