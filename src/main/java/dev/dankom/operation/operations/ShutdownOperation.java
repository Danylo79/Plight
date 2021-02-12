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
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void run() {
        getLog().info("ShutdownOperation$" + getName(), "Starting Operation . . .");
        try {
            Runtime.getRuntime().addShutdownHook(runner);
        } catch (Exception e) {
            getLog().error("ShutdownOperation$" + getName(), "Operation Failed!");
            e.printStackTrace();
            return;
        }
        getLog().info("ShutdownOperation$" + getName(), "Operation Successful!");
    }
}
