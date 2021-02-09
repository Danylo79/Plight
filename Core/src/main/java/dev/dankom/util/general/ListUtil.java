package dev.dankom.util.general;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {
    public static <T> List<T> toList(T... objects) {
        List<T> out = new ArrayList<>();
        for (T o : objects) {
            out.add(o);
        }
        return out;
    }

    public static <T> T[] toArray(T... objects) {
        T[] out = (T[]) objects;
        return out;
    }

    public static boolean contains(String[] list, Object o) {
        return toList(list).contains(o);
    }
}
