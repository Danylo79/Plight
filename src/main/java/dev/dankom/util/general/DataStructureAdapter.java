package dev.dankom.util.general;

import java.util.*;

public class DataStructureAdapter {
    public static <T> List<T> listFromLinkedList(LinkedList<T> ll) {
        List<T> out = new ArrayList<>();
        for (T o : ll) {
            out.add(o);
        }
        return out;
    }

    public static <T> T[] arrayFromLinkedList(LinkedList<T> ll) {
        return (T[]) listFromLinkedList(ll).toArray(listFromLinkedList(ll).toArray());
    }

    public static <T> LinkedList<T> listToLinkedList(List<T> l) {
        LinkedList<T> out = new LinkedList<>();
        for (T o : l) {
            out.add(o);
        }
        return out;
    }

    public static <T> LinkedList<T> arrayToLinkedList(T[] l) {
        LinkedList<T> out = new LinkedList<>();
        for (T o : l) {
            out.add(o);
        }
        return out;
    }

    public static <T> List<T> listFromQueue(Queue<T> queue) {
        List<T> out = new ArrayList<>();
        for (T o : queue) {
            out.add(o);
        }
        return out;
    }

    public static <T> T[] arrayFromQueue(Queue<T> queue) {
        return (T[]) listFromQueue(queue).toArray(listFromQueue(queue).toArray());
    }

    public static <T> T[] arrayFromArray(T... array) {
        return array;
    }

    public static <T> Queue<T> listToQueue(List<T> l) {
        Queue<T> out = new LinkedList<>();
        for (T o : l) {
            out.add(o);
        }
        return out;
    }

    public static <T> Queue<T> arrayToQueue(T[] l) {
        Queue<T> out = new LinkedList<>();
        for (T o : l) {
            out.add(o);
        }
        return out;
    }

    public static <T> List<T> arrayToList(T... objects) {
        List<T> out = new ArrayList<>();
        for (T o : objects) {
            out.add(o);
        }
        return out;
    }

    public static <T, K> Map<T, K> arrayToMap(T[] objects, K[] objects1) {
        Map<T, K> out = new HashMap<>();
        for (int i = 0; i < objects.length; i++) {
            out.put(objects[i], objects1[i]);
        }
        return out;
    }

    public static <T> T[] listToArray(List<T> objects) {
        return (T[]) objects.toArray(objects.toArray());
    }
}
