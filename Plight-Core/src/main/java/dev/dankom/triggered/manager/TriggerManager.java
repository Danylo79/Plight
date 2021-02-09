package dev.dankom.triggered.manager;

import dev.dankom.annotation.TriggerTarget;
import dev.dankom.triggered.trigger.Trigger;
import dev.dankom.triggered.trigger.Triggers;
import dev.dankom.triggered.type.TriggerType;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TriggerManager {
    private static List<Data> dataList;

    public static void register(Object o) {
        try {
            Class<?> clazz = o.getClass();
            for (Method m : clazz.getDeclaredMethods()) {
                if (isMethodApplicable(m)) {
                    TriggerTarget a = m.getAnnotation(TriggerTarget.class);
                    Data d = new Data(a.trigger(), m, o);
                    dataList.add(d);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void unregister(Object o) {
        try {
            Class<?> clazz = (Class<?>) o;
            for (Method m : clazz.getDeclaredMethods()) {
                for (Data d : dataList) {
                    if (d.method().equals(m)) {
                        dataList.remove(d);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean isMethodApplicable(Method m) {
        return m.isAnnotationPresent(TriggerTarget.class) && (m.getParameterTypes()[0].equals(Trigger.class));
    }

    public static void call(String trigger) {
        try {
            for (Data d : dataList) {
                if (d.trigger().equalsIgnoreCase(trigger)) {
                    d.method().invoke(d.source(), Triggers.getTrigger(trigger));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startup() {
        dataList = new ArrayList<>();
        new Trigger("TriggerManagerStartup", TriggerType.DURING);
    }

    public static void shutdown() {
        dataList.clear();
        new Trigger("TriggerManagerShutdown", TriggerType.DURING);
    }

    static {
        startup();
    }
}
