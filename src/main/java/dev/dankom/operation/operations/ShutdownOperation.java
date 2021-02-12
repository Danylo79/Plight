package dev.dankom.operation.operations;

import dev.dankom.interfaces.impl.ThreadMethodRunner;
import dev.dankom.logger.interfaces.ILogger;
import dev.dankom.operation.IOperation;

public class ShutdownOperation extends IOperation {
    private ThreadMethodRunner runner;
    private String name;

    public ShutdownOperation(ThreadMethodRunner runner, String name, ILogger logger) {
        super(logger);
        this.runner = runner;
        this.name = name;
        register(getRunner());
    }

    @Override
    public String getName() {
        return name;
    }

    public ThreadMethodRunner getRunner() {
        return runner;
    }

    protected void register(Thread runner) {
        Runtime.getRuntime().addShutdownHook(runner);
    }
}
