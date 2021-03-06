package dev.dankom.operation;

import dev.dankom.logger.abztract.AbstractLogger;
import dev.dankom.logger.interfaces.ILogger;

public abstract class IOperation {
    private ILogger logger;

    public abstract String getName();

    public IOperation(ILogger logger) {
        this.logger = logger;
    }

    protected AbstractLogger getLog() {
        return new AbstractLogger(logger);
    }

    protected void log(String msg) {
        getLog().info(getClass().getSimpleName() + "$" + getName(), msg);
    }
}
