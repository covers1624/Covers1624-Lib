package covers1624.lib.math;

import net.minecraft.util.math.Vec3i;

import java.util.Formatter;
import java.util.Locale;

public class Vector3 {
    public double x;
    public double y;
    public double z;

    public Vector3() {
        this(0, 0, 0);
    }

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(Vector3 vec) {
        this.x = vec.x;
        this.y = vec.y;
        this.z = vec.z;
    }

    public Vector3(Vec3i vec3i){
        this.x = vec3i.getX();
        this.y = vec3i.getY();
        this.z = vec3i.getZ();
    }

    public void set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void set(Vector3 vec) {
        this.x = vec.x;
        this.y = vec.y;
        this.z = vec.z;
    }

    public void add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }

    public void add(Vector3 vec) {
        this.x += vec.x;
        this.y += vec.y;
        this.z += vec.z;
    }

    public void subtract(double x, double y, double z) {
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
