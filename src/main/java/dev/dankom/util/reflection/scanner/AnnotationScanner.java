package dev.dankom.util.reflection.scanner;

import dev.dankom.agent.type.Agent;
import dev.dankom.agent.type.wrappers.AgentAnnotation;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class AnnotationScanner {

    private Class<?> clazz;

    public AnnotationScanner(Class<?> clazz) {
        this.clazz = clazz;
    }

    public List<Annotation> scan(Class<? extends Annotation> annotationType) {
        List<Annotation> out = new ArrayList<>();
        for (Annotation a : getClazz().getAnnotations()) {
            if (a.annotationType().equals(annotationType)) {
                out.add(a);
            }
        }
        return out;
    }

    public List<AgentAnnotation> scanAgent(Class<? extends Annotation> annotationType) {
        Agent agent = new Agent(getClazz());
        List<AgentAnnotation> out = new ArrayList<>();
        for (Object a : agent.annotations()) {
            AgentAnnotation aa = (AgentAnnotation) a;
            out.add(aa);
        }
        return out;
    }

    public Class<?> getClazz() {
        return clazz;
    }
}
