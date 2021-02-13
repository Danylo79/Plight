package dev.dankom.agent.type.interfaces;

import dev.dankom.agent.AgentLoader;
import dev.dankom.agent.type.wrappers.AgentAnnotation;
import dev.dankom.agent.type.wrappers.AgentField;
import dev.dankom.agent.type.wrappers.AgentMethod;

import java.util.List;

public interface IAgent<T> {
    boolean isFromAgentLoader();

    List<AgentAnnotation> annotations();
    List<AgentField> fields();
    List<AgentMethod> methods();

    AgentLoader getParent();
    Class<?> getClazz();

    T newInstance() throws Exception;
    T newInstance(Class<?>[] argTypes, Object[] args) throws Exception;
    T newInstance(Object[] args) throws Exception;

    default T newSilentInstance(Object... args) {
        try {
            return newInstance(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    default T newSilentInstance(Class<?>[] argTypes, Object... args) {
        try {
            return newInstance(argTypes, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    default T newSilentInstance() {
        try {
            return newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
