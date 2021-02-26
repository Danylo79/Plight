package dev.dankom.script.logger;

import dev.dankom.logger.abztract.DebugLogger;
import dev.dankom.logger.type.LogLevel;
import dev.dankom.script.engine.Script;

public class ScriptDebugLogger extends DebugLogger {
    private Script script;

    public ScriptDebugLogger(boolean seeTest, Script script) {
        super(seeTest);
        this.script = script;
    }

    @Override
    public void log(LogLevel level, String loc, Object msg) {
        super.log(level, loc, "[" + script.getName() + ".plight] " + msg);
    }
}
