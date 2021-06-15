package dev.dankom.type.consumer;

import java.util.Objects;

public interface QuadConsumer<T, U, K, V> {
    void accept(T t, U u, K k, V v);

    default QuadConsumer<T, U, K, V> andThen(QuadConsumer<? super T, ? super U, ? super K, ? super V> after) {
        Objects.requireNonNull(after);

        return (l, r, k, v) -> {
            accept(l, r, k, v);
            after.accept(l, r, k, v);
        };
    }
}
