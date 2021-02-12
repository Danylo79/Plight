package dev.dankom.interfaces.impl;

import dev.dankom.interfaces.runner.MethodRunner;

public class ThreadMethodRunner extends Thread {
    private MethodRunner runner;

    public ThreadMethodRunner(MethodRunner runner) {
        this.runner = runner;
    }

    @Override
    public void run() {
        runner.run();
    }
}
