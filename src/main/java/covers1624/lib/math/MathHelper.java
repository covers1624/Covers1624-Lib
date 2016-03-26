package covers1624.lib.math;

/**
 * Created by covers1624 on 1/12/2016.
 */
public class MathHelper {

    public static int clampInt(int value, int min, int max) {
        if (value > max) {
            return max;
        }
        if (value < min) {
            return min;
        }
        return value;
    }
}
