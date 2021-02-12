package dev.dankom.test;

import dev.dankom.core.Core;
import dev.dankom.logger.Logger;
import dev.dankom.util.reflection.ReflectionUtil;

public class TestRunner {

    public Logger logger;
    private String[] testRunners;

    /**
     * @see RuntimeTest
     * @param testRunners The directories that contains all the test classes
     */
    public TestRunner(String... testRunners) {
        this.testRunners = testRunners;
    }

    /**
     * Goes through all the directories that were presented in the constructor finds test classes and runs them
     */
    public void start() {
        this.logger = Core.getLogger();
        logger.test("TestRunner", "Starting TestRunner");
        for (String dir : testRunners) {
            for (Class<? extends RuntimeTest> test : ReflectionUtil.getAllClasses(dir, RuntimeTest.class)) {
                try {
                    RuntimeTest testInstance = test.newInstance();
                    testInstance.run();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
