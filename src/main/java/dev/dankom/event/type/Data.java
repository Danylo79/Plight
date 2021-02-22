package dev.dankom.event.type;

import dev.dankom.event.annotation.EventTarget;
import dev.dankom.exception.InvalidEventException;
import jdk.nashorn.internal.ir.annotations.Ignore;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Data {
    private EventTarget et;
    private boolean useAdvanced;
    private Object source;
    private Method parent;
    private Class<? extends Event> event;

    private boolean narrow;
    private Class<?>[] narrowClasses;

    public Data(EventTarget et, Object source, Method m) throws InvalidEventException {
        this.et = et;
        this.source = source;
        this.parent = m;
        this.event = et.event();
        this.useAdvanced = et.advanced().isUsed();

        this.narrowClasses = et.advanced().narrow();
        this.narrow = !getNarrowClasses().contains(Ignore.class);
    }

    public EventTarget getEventTarget() {
        return et;
    }

    public Method getParent() {
        return parent;
    }

    public boolean isAdvanced() {
        return useAdvanced;
    }

    public boolean isNarrowed() {
        return narrow;
    }

    public List<Class<?>> getNarrowClasses() {
        List<Class<?>> out = new ArrayList<>();
        for (Class<?> c : narrowClasses) {
            out.add(c);
        }
        return out;
    }

    public Object getSource() {
        return source;
    }
}
