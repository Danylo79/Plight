package dev.dankom.event;

import dev.dankom.event.annotation.EventTarget;
import dev.dankom.event.type.Data;
import dev.dankom.event.type.Event;
import dev.dankom.exception.InvalidEventException;
import dev.dankom.logger.LogManager;
import dev.dankom.logger.interfaces.ILogger;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class EventManager {
    private static List<Data> validMethods = new ArrayList<>();
    private static final ILogger logger = LogManager.getLogger("EventManager");

    private static void register(Data d) {
        validMethods.add(d);
    }

    private static void unregister(Data d) {
        validMethods.remove(d);
    }

    private static void register(Object o, Method m) {
        if (m.isAnnotationPresent(EventTarget.class)) {
            try {
                register(new Data(m.getAnnotation(EventTarget.class), o, m));
            } catch (InvalidEventException e) {
                e.printStackTrace();
            }
        }
    }

    public static void register(Object o) {
        register(o, o.getClass());
    }

    private static void register(Object o, Class<?> c) {
        for (Method m : c.getDeclaredMethods()) {
            if (checkMethod(m)) register(o, m);
        }
    }

    private static void unregister(Class<?> c) {
        for (Method m : c.getDeclaredMethods()) {
            unregister(get(m));
        }
    }

    public static void unregister(Object o) {
        unregister(o.getClass());
    }

    public static void call(Object caller, Event e) {
        for (Data d : validMethods) {
            if (d.isAdvanced()) {
                e.callInsideAdvanced(caller, d);
            } else {
                e.callInside(d);
            }
        }
    }

    public static Data get(Method m) {
        for (Data d : validMethods) {
            if (d.getParent().equals(m)) {
                return d;
            }
        }
        return null;
    }

    private static boolean checkMethod(Method m) {
        boolean b = m.getParameterTypes().length == 1 || m.isAnnotationPresent(EventTarget.class);
        return b;
    }
}
