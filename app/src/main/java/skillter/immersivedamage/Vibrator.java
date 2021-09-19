package skillter.immersivedamage;


import static skillter.immersivedamage.MainActivity.instance;

import android.os.Build;
import android.os.VibrationEffect;

import skillter.immersivedamage.util.MoreMath;

public class Vibrator {

    public static int maxDuration = 10000; // in ms

    public static void doVibration(int duration, int strength) throws UnsupportedOperationException {

        duration = constrainDuration(duration);
        strength = constrainStrength(strength);

        if (!((android.os.Vibrator) instance.getSystemService(instance.VIBRATOR_SERVICE)).hasVibrator()) {
            throw new UnsupportedOperationException("The device doesn't have a vibrator, therefore it doesn't support vibrations.");
        }

        if (Build.VERSION.SDK_INT >= 26) {
            ((android.os.Vibrator) instance.getSystemService(instance.VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(duration, strength));
        } else {
            ((android.os.Vibrator) instance.getSystemService(instance.VIBRATOR_SERVICE)).vibrate(duration);
        }

    }

    public static int constrainDuration(int duration) {
        return MoreMath.constrainToRange(duration, 1, maxDuration);
    }
    public static int constrainStrength(int strength) {
        return MoreMath.constrainToRange(strength, 1, 255);
    }


}
