package dev.dankom.type.promise;

public interface PromiseBiConsumer<T, V> {
    void then(T value, V value2);
}
