package dev.dankom.script.engine.loader.boostrap;

import dev.dankom.script.engine.Script;

public interface ScriptBoostrap {
    void onInit();
    void onShutdown();
    void onLoadScript(Script s);
    boolean seeDebug();
    boolean isSimulated();
}
