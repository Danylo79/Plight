package dev.dankom.tests.agent;

import dev.dankom.agent.type.Agent;
import dev.dankom.util.general.Validation;

public class AgentLoaderTest {
    public static void main(String[] args) {
        try {
            Agent<AgentTest> agent = new Agent<>(Class.forName("dev.dankom.tests.agent.AgentTest"));
            Validation.assertObject("Agent with no args failed!", testAgent(agent), true);
            Validation.assertObject("Agent with args failed!", testAgent(agent, "Instance with args test!"), true);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static boolean isClassGood(Class<?> clazz, Object o) {
        return clazz.getName().equalsIgnoreCase(o.getClass().getName());
    }

    private static boolean testAgent(Agent a) {
        return isClassGood(a.getClazz(), a.newSilentInstance());
    }

    private static boolean testAgent(Agent a, Object... args) {
        return isClassGood(a.getClazz(), a.newSilentInstance(args));
    }
}
