package dev.dankom.script.type.method;

import dev.dankom.script.ScriptLoader;
import dev.dankom.script.type.var.ScriptVariable;

public class ScriptMethodParameter extends ScriptVariable {
    public ScriptMethodParameter(ScriptLoader parent, String name, String type, String value) {
        super(parent, name, type, value);
    }
}
