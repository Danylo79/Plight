package dev.dankom.script.engine.loader.boostrap;

import dev.dankom.script.engine.Script;

public class DefaultBoostrap implements ScriptBoostrap {
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
        return false;
    }

    @Override
    public boolean isSimulated() {
        return false;
    }
}
