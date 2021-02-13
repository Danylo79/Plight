package dev.dankom.agent;

import dev.dankom.agent.type.Agent;
import dev.dankom.agent.type.interfaces.IAgent;
import dev.dankom.util.reflection.ReflectionUtil;

import java.util.ArrayList;
import java.util.List;

public class AgentLoader {
    private final String dir;
    private final Class<?> type;
    private List<IAgent> agents;

    public AgentLoader(String dir, Class<?> type) {
        this.dir = dir;
        this.type = type;
        this.agents = new ArrayList<>();

        for (Class<?> c : ReflectionUtil.getAllClasses(getDirectory(), getType())) {
            agents.add(new Agent(this, c));
        }
    }

    public AgentLoader(String dir) {
        this.dir = dir;
        this.type = Object.class;
        this.agents = new ArrayList<>();

        for (Class<?> c : (type == null ? ReflectionUtil.getAllClasses(getDirectory(), getType()) : ReflectionUtil.getAllClasses(getDirectory()))) {
            agents.add(new Agent(this, c));
        }
    }

    public void run() {
        for (IAgent agent : agents) {
            agent.newSilentInstance();
        }
    }

    public void run(Object... args) {
        for (IAgent agent : agents) {
            agent.newSilentInstance(args);
        }
    }

    public String getDirectory() {
        return dir;
    }

    public Class<?> getType() {
        return type;
    }
}
