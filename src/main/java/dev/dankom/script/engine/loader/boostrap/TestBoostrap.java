package dev.dankom.script.engine.loader.boostrap;

import dev.dankom.script.engine.Script;

public class TestBoostrap implements ScriptBoostrap {
    @Override
    public void onInit() {

    }

    @Override
    public void onShutdown() {

    }

    @Override
    public void onLoadScript(Script s) {

    }

    @Override
    public boolean seeDebug() {
        return true;
    }

    @Override
    public boolean isSimulated() {
        return true;
    }
}
