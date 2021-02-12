package dev.dankom.tests.agent;

import dev.dankom.agent.AgentLoader;

public class AgentLoaderTest {
    public static void main(String[] args) {
        AgentLoader loader = new AgentLoader("dev.dankom.tests.agent");
        loader.run();
    }
}
