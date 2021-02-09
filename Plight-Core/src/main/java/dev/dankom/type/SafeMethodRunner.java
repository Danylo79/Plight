package dev.dankom.type;

import dev.dankom.interfaces.runner.MethodRunner;

public class SafeMethodRunner {
    private MethodRunner runner;

    public SafeMethodRunner(MethodRunner runner) {
        this.runner = runner;
    }

    public void run() {
        try {
            runner.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
