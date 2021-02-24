package dev.dankom.script.type.var;

import dev.dankom.script.Script;

public class ScriptUniformVariable extends ScriptVariable {
    public ScriptUniformVariable(Script parent, String name) {
        super(parent, name, "String", "uniform_not_bound");
    }
}
