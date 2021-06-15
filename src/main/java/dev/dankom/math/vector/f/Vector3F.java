package dev.dankom.math.vector.f;

public class Vector3F {
    private float x;
    private float y;
    private float z;

    public Vector3F(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public void add(Vector3F vec) {
        x += vec.getX();
        y += vec.getY();
        z += vec.getZ();
    }

    public void minus(Vector3F vec) {
        x -= vec.getX();
        y -= vec.getY();
        z -= vec.getZ();
    }

    public void multiply(Vector3F vec) {
        x *= vec.getX();
        y *= vec.getY();
        z *= vec.getZ();
    }

    public void divide(Vector3F vec) {
        x /= vec.getX();
        y /= vec.getY();
        z /= vec.getZ();
    }
}
