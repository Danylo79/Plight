package dev.dankom.script.exception.exceptions;

import dev.dankom.script.engine.Script;
import dev.dankom.script.exception.ScriptRuntimeException;

public class ScriptMethodNotFoundException extends ScriptRuntimeException {
    public ScriptMethodNotFoundException(String msg, Script script) {
        super(msg, script);
    }
}
