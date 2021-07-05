package units;

import java.util.Random;

public class Utilits {

    public static double roundDouble(double d) {
        return Math.rint(10.0 * d) / 10.0;
    }

    public static String cutDouble(double d) {
        return String.format("%.0f", d);
    }

    public static String makeBold(String text) {
        return "*" + text + "*";
    }

    public static String makeItalic(String text) {
        return "_" + text + "_";
    }

    public static int getRandom(int max) {
        int min = 0;
        return new Random().nextInt((max - min)) + min;
    }
}
