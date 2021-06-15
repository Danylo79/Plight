package dev.dankom.util.general;

import dev.dankom.math.vector.d.Vector2D;
import dev.dankom.math.vector.d.Vector3D;
import dev.dankom.math.vector.f.Vector2F;
import dev.dankom.math.vector.f.Vector3F;
import dev.dankom.math.vector.i.Vector2I;
import dev.dankom.math.vector.i.Vector3I;

public class VectorUtil {
    public static final Vector2F vector(float x, float y) {
        return new Vector2F(x, y);
    }

    public static final Vector2D vector(double x, double y) {
        return new Vector2D(x, y);
    }

    public static final Vector2I vector(int x, int y) {
        return new Vector2I(x, y);
    }

    public static final Vector3F vector(float x, float y, float z) {
        return new Vector3F(x, y, z);
    }

    public static final Vector3D vector(double x, double y, double z) {
        return new Vector3D(x, y, z);
    }

    public static final Vector3I vector(int x, int y, int z) {
        return new Vector3I(x, y, z);
    }
}
