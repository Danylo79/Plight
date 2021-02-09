package dev.dankom.exception;

import java.lang.reflect.Method;

public class InvalidTriggerMethodException extends Exception {
    public InvalidTriggerMethodException(Method m) {
        super("Attempted to register invalid trigger method " + m.getName() + "!");
    }
}
