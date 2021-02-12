package dev.dankom.agent.type.interfaces;

import dev.dankom.agent.AgentLoader;
import dev.dankom.agent.type.AgentAnnotation;
import dev.dankom.agent.type.AgentField;
import dev.dankom.agent.type.AgentMethod;

import java.util.List;

public interface IAgent {
    boolean isFromAgentLoader();

    List<AgentAnnotation> annotations();
    List<AgentField> fields();
    List<AgentMethod> methods();

    AgentLoader getParent();
    Class<?> getClazz();

    void run() throws Exception;
    void run(Class<?>[] argTypes, Object[] args) throws Exception;
    void run(Object[] args) throws Exception;

    default void runSilent(Object... args) {
        try {
            run(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    default void runSilent(Class<?>[] argTypes, Object... args) {
        try {
            run(argTypes, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    default void runSilent() {
        try {
            run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
