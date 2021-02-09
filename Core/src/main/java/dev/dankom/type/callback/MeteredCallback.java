package dev.dankom.type.callback;

public class MeteredCallback<T> extends RuntimeCallback<T> {

    private int meter;
    private int max;

    public MeteredCallback(T o, int max) {
        super(o);
        this.max = max;
        this.meter = 1;
    }

    @Override
    public T get() {
        increase();
        if (meter <= max) {
            return super.get();
        } else {
            return null;
        }
    }

    public void increase() {
        meter++;
    }
}
