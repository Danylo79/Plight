package dev.dankom.interfaces;

public interface Copyable<T> extends Cloneable {
    T copy();
}
