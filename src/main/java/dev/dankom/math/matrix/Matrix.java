package dev.dankom.math.matrix;

import dev.dankom.math.vector.i.Vector2I;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Matrix<T> {
    private final T[][] matrix;
    private final int width, height;

    public Matrix(int width, int height) {
        this.matrix = (T[][]) new Object[width][height];
        this.width = width;
        this.height = height;
    }

    public Matrix(int even) {
        this((int) Math.sqrt(even), (int) Math.sqrt(even));
    }

    public Matrix(List<T> ts) {
        this(ts.size());
        each((j, i) -> set(i, j, ts.get(i + j)));
    }

    public Matrix(List<T> ts, int width, int height) {
        this(width, height);
        each((j, i) -> set(i, j, ts.get(i + j)));
    }

    public void each(Consumer<T> c) {
        each((i1, i2) -> c.accept(get(i1, i2)));
    }

    public void each(BiConsumer<Integer, Integer> c) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                c.accept(j, i);
            }
        }
    }

    public void set(int width, int height, T o) {
        matrix[width][height] = o;
    }

    public void set(Vector2I n, T o) {
        matrix[n.getX()][n.getY()] = o;
    }

    public T get(int width, int height) {
        return matrix[width][height];
    }

    public T get(Vector2I n) {
        return matrix[n.getX()][n.getY()];
    }

    public List<T> all() {
        List<T> out = new ArrayList<>();
        each(t -> {
            out.add(t);
        });
        return out;
    }
}
