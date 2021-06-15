package dev.dankom.math.vector.i;

public class Vector2I {
    private int x;
    private int y;

    public Vector2I(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void add(Vector2I vec) {
        x += vec.getX();
        y += vec.getY();
    }

    public void minus(Vector2I vec) {
        x -= vec.getX();
        y -= vec.getY();
    }

    public void multiply(Vector2I vec) {
        x *= vec.getX();
        y *= vec.getY();
    }

    public void divide(Vector2I vec) {
        x /= vec.getX();
        y /= vec.getY();
    }
}
