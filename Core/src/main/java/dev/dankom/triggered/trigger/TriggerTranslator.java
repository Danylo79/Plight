package dev.dankom.triggered.trigger;

public class TriggerTranslator {
    public static String translate(Trigger trigger) {
        return "Trigger={" + trigger.getTrigger() + ", Type={" + trigger.getType().toString() + "}}";
    }
}
