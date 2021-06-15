package dev.dankom.math.vector.d;

public class Vector2D {
    private double x;
    private double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void add(Vector2D vec) {
        x += vec.getX();
        y += vec.getY();
    }

    public void minus(Vector2D vec) {
        x -= vec.getX();
        y -= vec.getY();
    }

    public void multiply(Vector2D vec) {
        x *= vec.getX();
        y *= vec.getY();
    }

    public void divide(Vector2D vec) {
        x /= vec.getX();
        y /= vec.getY();
    }
}
