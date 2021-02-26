package dev.dankom.script.exception.exceptions;

import dev.dankom.script.engine.Script;
import dev.dankom.script.exception.ScriptRuntimeException;
import dev.dankom.script.pointer.Pointer;

public class ScriptInjectException extends ScriptRuntimeException {
    public ScriptInjectException(String msg, Script script, Pointer p) {
        super(msg, script, p);
    }

    public ScriptInjectException(String msg, Throwable ex, Script script, Pointer p) {
        super(msg, ex, script, p);
    }

    public ScriptInjectException(String msg, Script script) {
        super(msg, script);
    }

    public ScriptInjectException(String msg, Throwable ex, Script script) {
        super(msg, ex, script);
    }
}
