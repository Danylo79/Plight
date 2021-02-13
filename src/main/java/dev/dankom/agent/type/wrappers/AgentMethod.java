package dev.dankom.agent.type.wrappers;

import dev.dankom.agent.type.Agent;
import dev.dankom.type.ReflectionData;
import dev.dankom.util.reflection.ReflectionUtil;

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

    public ReflectionData getData() {
        return ReflectionUtil.getMethodData(method);
    }
}
