package dev.dankom.math.vector.f;

public class Vector2F {
    private float x;
    private float y;

    public Vector2F(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void add(Vector2F vec) {
        x += vec.getX();
        y += vec.getY();
    }

    public void minus(Vector2F vec) {
        x -= vec.getX();
        y -= vec.getY();
    }

    public void multiply(Vector2F vec) {
        x *= vec.getX();
        y *= vec.getY();
    }

    public void divide(Vector2F vec) {
        x /= vec.getX();
        y /= vec.getY();
    }
}
