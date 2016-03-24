package covers1624.lib.math;

import java.util.Formatter;
import java.util.Locale;

/**
 * Created by covers1624 on 1/12/2016.
 */
public class Vector3I {
	public int x;
	public int y;
	public int z;

	public Vector3I() {
	}

	public Vector3I(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3I(Vector3I vec) {
		this.x = vec.x;
		this.y = vec.y;
		this.z = vec.z;
	}

	@Override
	public Object clone() {
		return new Vector3I(this);
	}

	public void set(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void set(Vector3I vec) {
		this.x = vec.x;
		this.y = vec.y;
		this.z = vec.z;
	}

	public void add(int x, int y, int z) {
		this.x += x;
		this.y += y;
		this.z += z;
	}

	public void add(Vector3I vec) {
		this.x += vec.x;
		this.y += vec.y;
		this.z += vec.z;
	}

	public void subtract(Vector3I vec) {
		this.x -= vec.x;
		this.y -= vec.y;
		this.z -= vec.z;
	}

	public void subtract(int x, int y, int z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
	}

	@SuppressWarnings("resource")
	@Override
	public String toString() {
		StringBuilder var1 = new StringBuilder();
		Formatter var2 = new Formatter(var1, Locale.US);
		var2.format("Vector:\n");
		var2.format("  < %s %s %s >\n", this.x, this.y, this.z);
		return var1.toString();
	}
}