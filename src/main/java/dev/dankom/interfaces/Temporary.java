package dev.dankom.interfaces;

public interface Temporary {
    void open();

    void close();

    default void reset() {
        close();
        open();
    }
}
