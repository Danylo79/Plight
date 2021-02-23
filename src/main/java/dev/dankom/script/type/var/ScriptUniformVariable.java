package dev.dankom.script.type.var;

import dev.dankom.script.ScriptLoader;

public class ScriptUniformVariable extends ScriptVariable {
    public ScriptUniformVariable(ScriptLoader parent, String name) {
        super(parent, name, "String", "uniform_not_bound");
    }
}
