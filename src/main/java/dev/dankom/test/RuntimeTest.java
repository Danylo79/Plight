package dev.dankom.test;

import dev.dankom.core.Core;
import dev.dankom.logger.Logger;

import java.lang.reflect.Method;

/**
 * Runs all the methods marked with the @Test annotation
 * @see Test
 */
public class RuntimeTest {

    protected Logger logger;

    public RuntimeTest() {
        this.logger = Core.getLogger();
    }

    public void run() {
        Class<?> clazz = getClass();
        for (Method m : clazz.getDeclaredMethods()) {
            try {
                if (m.isAnnotationPresent(Test.class)) {
                    m.invoke(this);
                }
            } catch (Exception e) {
                logger.error("Test-Manager", "Failed to run test " + m.getName() + " because of " + e.getClass().getSimpleName() + "!");
                e.printStackTrace();
            }
        }
    }

    public void assertResult(Method o, boolean b) {
        if (b) {
            Core.getLogger().fatal("RuntimeTest (" + getClass().getSimpleName() + ")", "Test " + o.getName() + " failed!");
            Runtime.getRuntime().exit(-1);
            return;
        } else {
            Core.getLogger().test("RuntimeTest (" + getClass().getSimpleName() + ")", "Test " + o.getName() + " has passed!");
        }
    }

    public void assertResult(boolean b) {
        if (b) {
            Core.getLogger().fatal("RuntimeTest (" + getClass().getSimpleName() + ")", "Test failed!");
            Runtime.getRuntime().exit(-1);
            return;
        } else {
            Core.getLogger().test("RuntimeTest (" + getClass().getSimpleName() + ")", "Test has passed!");
        }
    }
}
