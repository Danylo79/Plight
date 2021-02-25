package dev.dankom.script.exception.exceptions;

import dev.dankom.script.engine.Script;
import dev.dankom.script.exception.ScriptRuntimeException;

public class ScriptNotLoadedException extends ScriptRuntimeException {
    public ScriptNotLoadedException(String msg, Script script) {
        super(msg, script);
    }
}
