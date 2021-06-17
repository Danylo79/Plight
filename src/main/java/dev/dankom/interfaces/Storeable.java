package dev.dankom.interfaces;

public interface Storeable<T> {
    boolean add(T t);
    void insert(T t, int index);
    T get(int index);
}
