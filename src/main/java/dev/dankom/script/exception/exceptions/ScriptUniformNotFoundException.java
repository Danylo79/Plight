package dev.dankom.script.exception.exceptions;

import dev.dankom.script.engine.Script;
import dev.dankom.script.exception.ScriptRuntimeException;

public class ScriptUniformNotFoundException extends ScriptRuntimeException {
    public ScriptUniformNotFoundException(String msg, Script script) {
        super(msg, script);
    }
}
