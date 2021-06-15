package dev.dankom.math.vector.d;

public class Vector3D {
    private double x;
    private double y;
    private double z;

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public void add(Vector3D vec) {
        x += vec.getX();
        y += vec.getY();
        z += vec.getZ();
    }

    public void minus(Vector3D vec) {
        x -= vec.getX();
        y -= vec.getY();
        z -= vec.getZ();
    }

    public void multiply(Vector3D vec) {
        x *= vec.getX();
        y *= vec.getY();
        z *= vec.getZ();
    }

    public void divide(Vector3D vec) {
        x /= vec.getX();
        y /= vec.getY();
        z /= vec.getZ();
    }
}
