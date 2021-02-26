package dev.dankom.script.engine.hot.tranformer;

import dev.dankom.script.engine.Script;

public abstract class ITransformer {
    private Script s;

    public ITransformer(Script s) {
        this.s = s;
    }

    public abstract void apply();

    public Script getScript() {
        return s;
    }
}
