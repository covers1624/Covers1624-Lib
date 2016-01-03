package covers1624.lib.math;

import java.util.Formatter;
import java.util.Locale;

public class Vector3 {
	public double x;
	public double y;
	public double z;

	public Vector3() {
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

	@Override
	public Object clone() {
		return new Vector3(this);
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

	@Deprecated
	public double dotProduct(Vector3 vec)
	{
		return vec.x * this.x + vec.y * this.y + vec.z * this.z;
	}
	@Deprecated
	public double dotProduct(double x, double y, double z) {
		return x * this.x + y * this.y + z * this.z;
	}
	@Deprecated
	public void crossProduct(Vector3 vec) {
		double p1 = this.y * vec.z - this.z * vec.y;
		double p2 = this.z * vec.x - this.x * vec.z;
		double p3 = this.x * vec.y - this.y * vec.x;
		this.x = p1;
		this.y = p2;
		this.z = p3;
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

	public void subtract(Vector3 vec) {
		this.x -= vec.x;
		this.y -= vec.y;
		this.z -= vec.z;
	}

	public void multiply(double num) {
		this.x *= num;
		this.y *= num;
		this.z *= num;
	}
	@Deprecated
	public double mag() {
		return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
	}
	@Deprecated
	public double magSquared() {
		return this.x * this.x + this.y * this.y + this.z * this.z;
	}
	@Deprecated
	public void normalize() {
		double mag = this.mag();

		if (mag != 0.0D) {
			this.multiply(1.0D / mag);
		}
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
