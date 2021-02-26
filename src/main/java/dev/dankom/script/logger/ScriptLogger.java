package dev.dankom.script.logger;

import dev.dankom.logger.abztract.DefaultLogger;
import dev.dankom.logger.type.LogLevel;
import dev.dankom.script.engine.Script;

public class ScriptLogger extends DefaultLogger {
    private Script script;

    public ScriptLogger(Script script) {
        this.script = script;
    }

    @Override
    public void log(LogLevel level, String loc, Object msg) {
        super.log(level, loc, "[" + script.getName() + ".plight] " + msg);
    }
}
