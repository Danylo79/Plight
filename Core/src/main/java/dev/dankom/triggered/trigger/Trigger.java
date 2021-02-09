package dev.dankom.triggered.trigger;

import dev.dankom.triggered.manager.TriggerManager;
import dev.dankom.triggered.type.TriggerType;

public class Trigger {
    private final String trigger;
    private final TriggerType type;

    public Trigger(String trigger, TriggerType type) {
        this.trigger = trigger;
        this.type = type;
        Triggers.add(this);
        call();
    }

    public String getTrigger() {
        return trigger;
    }

    public TriggerType getType() {
        return type;
    }

    void call() {
        TriggerManager.call(getTrigger());
    }
}