package dev.dankom.agent.type;

import java.lang.reflect.Method;

public class AgentMethod {
    private final Agent parent;
    private final Method method;

    public AgentMethod(Agent parent, Method method) {
        this.parent = parent;
        this.method = method;
    }

    public String getName() {
        return method.getName();
    }

    public Class<?> getReturnType() {
        return method.getReturnType();
    }

    public Agent getParent() {
        return parent;
    }
}
