package dev.dankom.agent.type;

import java.lang.reflect.Field;

public class AgentField {
    private final Agent parent;
    private final Field field;

    public AgentField(Agent parent, Field field) {
        this.parent = parent;
        this.field = field;
    }

    public String getName() {
        return getClazz().getSimpleName();
    }

    public Package getPackage() {
        return getClazz().getPackage();
    }

    public Class<?> getClazz() {
        return field.getType();
    }

    public Agent getParent() {
        return parent;
    }
}
