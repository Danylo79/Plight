package dev.dankom.type.promise;

public interface PromiseConsumer<T> {
    void then(T o);
}
