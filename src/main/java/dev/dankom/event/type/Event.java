package dev.dankom.event.type;

import java.lang.reflect.InvocationTargetException;

public class Event {
    public final void callInsideAdvanced(Object caller, Data d) {
        if (d.isNarrowed()) {
            if (!d.getNarrowClasses().contains(caller.getClass())) {
                return;
            }
        }
        callInside(d);
    }

    public final void callInside(Data d) {
        if (getClass().equals(d.getEventTarget().event())) {
            try {
                d.getParent().invoke(d.getSource(), this);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
