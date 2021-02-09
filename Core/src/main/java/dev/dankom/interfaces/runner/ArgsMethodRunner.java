package dev.dankom.interfaces.runner;

public interface ArgsMethodRunner extends MethodRunner {

    void setArgs(Object... args);
    Object[] getArgs();

    default void run(Object... args) {
        setArgs(args);
        run();
    }

    @Override
    void run();
}
