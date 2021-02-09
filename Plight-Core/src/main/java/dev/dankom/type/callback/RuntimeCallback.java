package dev.dankom.type.callback;

import dev.dankom.exception.CallbackFailException;

public class RuntimeCallback<T> implements Callback<T> {

    private final T o;

    public RuntimeCallback(T o) {
        this.o = o;
    }

    @Override
    public T get() {
        if (o == null) {
            try {
                throw new CallbackFailException();
            } catch (CallbackFailException e) {
                e.printStackTrace();
            }
        }
        return o;
    }
}
