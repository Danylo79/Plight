package dev.dankom.test;

import dev.dankom.logger.Logger;

import java.lang.reflect.Method;

/**
 * Runs all the methods marked with the @Test annotation
 * @see Test
 */
public class RuntimeTest {
    public void run() {
        Class<?> clazz = getClass();
        for (Method m : clazz.getDeclaredMethods()) {
            try {
                if (m.isAnnotationPresent(Test.class)) {
                    m.invoke(this);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void assertResult(Method o, boolean b) {
        if (b) {
            Runtime.getRuntime().exit(-1);
            return;
        } else {
        }
    }

    public void assertResult(boolean b) {
        if (b) {
            Runtime.getRuntime().exit(-1);
            return;
        } else {
        }
    }
}
