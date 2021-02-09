package dev.dankom.triggered.test;

import dev.dankom.annotation.TriggerTarget;
import dev.dankom.triggered.manager.TriggerManager;
import dev.dankom.triggered.trigger.Trigger;
import dev.dankom.triggered.type.TriggerType;

public class Test {
    public static void main(String[] args) {
        new Test();
    }

    public Test() {
        TriggerManager.register(this);
        new Trigger("Test", TriggerType.DURING);
    }

    @TriggerTarget(trigger = "Test")
    public void onTest(Trigger trigger) {
        System.out.println("Working!");
    }
}
