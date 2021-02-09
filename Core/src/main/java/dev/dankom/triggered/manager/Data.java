package dev.dankom.triggered.manager;

import java.lang.reflect.Method;

public class Data {
    private final String trigger;
    private final Method method;
    private final Object source;

    public Data(String trigger, Method method, Object source) {
        this.trigger = trigger;
        this.method = method;
        this.source = source;
    }

    public String trigger() {
        return trigger;
    }

    public Method method() {
        return method;
    }

    public Object source() {
        return source;
    }
}
