package dev.dankom.agent.type;

import java.lang.annotation.Annotation;

public class AgentAnnotation {
    private Agent parent;
    private Annotation annotation;

    public AgentAnnotation(Agent parent, Annotation annotation) {
        this.parent = parent;
        this.annotation = annotation;
    }

    public String getName() {
        return getClazz().getSimpleName();
    }

    public Package getPackage() {
        return getClazz().getPackage();
    }

    public Class<? extends Annotation> getClazz() {
        return annotation.annotationType();
    }

    public Agent getParent() {
        return parent;
    }
}
