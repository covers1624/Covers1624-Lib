package covers1624.lib.math;

import java.util.Formatter;
import java.util.Locale;

/**
 * Created by covers1624 on 1/12/2016.
 */
@Deprecated//Use CCL.
public class Vector3F {
    public float x;
    public float y;
    public float z;

    public Vector3F() {
    }

    public Vector3F(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3F(Vector3F vec) {
        this.x = vec.x;
        this.y = vec.y;
        this.z = vec.z;
    }

    public void set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void set(Vector3F vec) {
        this.x = vec.x;
        this.y = vec.y;
        this.z = vec.z;
    }

    public void add(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }

    public void add(Vector3 vec) {
        this.x += vec.x;
        this.y += vec.y;
        this.z += vec.z;
    }

    public void subtract(float x, float y, float z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
    }

    public void subtract(Vector3 vec) {
        this.x -= vec.x;
        this.y -= vec.y;
        this.z -= vec.z;
    }

    @SuppressWarnings("resource")
    @Override
    public String toString() {
        StringBuilder var1 = new StringBuilder();
        Formatter var2 = new Formatter(var1, Locale.US);
        var2.format("Vector:\n");
        var2.format("  < %f %f %f >\n", this.x, this.y, this.z);
        return var1.toString();
    }
}
