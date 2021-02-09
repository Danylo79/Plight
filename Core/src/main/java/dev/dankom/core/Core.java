package dev.dankom.core;

import dev.dankom.logger.Logger;
import dev.dankom.test.TestRunner;

/**
 * @author Dankom
 */
public class Core {

    private static Logger logger;

    public Core(TestRunner runner) {
        getLogger().info("Core", "Starting Core by Dankom");
        runner.start();
    }

    public Core() {
        this(new TestRunner("dev.dankom.test.tests"));
    }

    public static Logger getLogger() {
        if (logger == null) {
            logger = new Logger();
        }
        return logger;
    }

    public static void main(String[] args) {
        new Core();
    }
}
