package dev.dankom.script.exception;

import dev.dankom.exception.BaseException;
import dev.dankom.script.engine.Script;
import dev.dankom.script.pointer.Pointer;

public class ScriptRuntimeException extends BaseException {
    private Script script;
    private Pointer p;

    public ScriptRuntimeException(String msg, Script script, Pointer p) {
        super(msg + " (" + p.getPointer().line() + ":" + p.getPointer().pos() + ") (Script: " + script.getPackage() + script.getName() + ".plight)");
        this.script = script;
        this.p = p;
    }

    public ScriptRuntimeException(String msg, Throwable ex, Script script, Pointer p) {
        super(msg + " (" + p.getPointer().line() + ":" + p.getPointer().pos() + ") (Script: " + script.getPackage() + "/" + script.getName() + ")", ex);
        this.script = script;
        this.p = p;
    }

    public ScriptRuntimeException(String msg, Script script) {
        super(msg + " (Script: " + script.getPackage() + script.getName() + ".plight)");
        this.script = script;
        this.p = p;
    }

    public ScriptRuntimeException(String msg, Throwable ex, Script script) {
        super(msg + " (Script: " + script.getPackage() + "/" + script.getName() + ")", ex);
        this.script = script;
        this.p = p;
    }

    public Pointer getPointer() {
        return p;
    }
}
