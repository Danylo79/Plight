package dev.dankom.triggered.trigger;

import java.util.ArrayList;
import java.util.List;

public class Triggers {
    public static List<Trigger> triggers = new ArrayList<>();

    public static void add(Trigger t) {
        if (!triggers.contains(t)) {
            triggers.add(t);
        }
    }

    public static Trigger getTrigger(String trigger) {
        for (Trigger t : triggers) {
            if (t.getTrigger().equalsIgnoreCase(trigger)) {
                return t;
            }
        }
        return null;
    }
}
