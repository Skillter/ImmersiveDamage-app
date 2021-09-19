package skillter.immersivedamage.util;

public class MoreMath {

    public static int constrainToRange(int value, int min, int max) {
        if (!(min <= max)) {
            throw new IllegalArgumentException("min " + min + " must be less than or equal to max " + max);
        }
        return Math.min(Math.max(value, min), max);
    }

}
