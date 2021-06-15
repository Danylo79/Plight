package dev.dankom.math.vector.i;

public class Vector3I {
    private int x;
    private int y;
    private int z;

    public Vector3I(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public void add(Vector3I vec) {
        x += vec.getX();
        y += vec.getY();
        z += vec.getZ();
    }

    public void minus(Vector3I vec) {
        x -= vec.getX();
        y -= vec.getY();
        z -= vec.getZ();
    }

    public void multiply(Vector3I vec) {
        x *= vec.getX();
        y *= vec.getY();
        z *= vec.getZ();
    }

    public void divide(Vector3I vec) {
        x /= vec.getX();
        y /= vec.getY();
        z /= vec.getZ();
    }
}
