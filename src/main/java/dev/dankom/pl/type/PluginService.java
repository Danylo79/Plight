package dev.dankom.pl.type;

import dev.dankom.logger.abztract.AbstractLogger;
import dev.dankom.logger.abztract.DefaultLogger;
import dev.dankom.logger.interfaces.ILogger;

public class PluginService {
    private final AbstractLogger abstractLogger;

    public PluginService(ILogger logger) {
        this.abstractLogger = new AbstractLogger(logger);
    }

    public PluginService() {
        this(new DefaultLogger());
    }

    public AbstractLogger getLog() {
        return abstractLogger;
    }
}
