package de.cristelknight.util;

public class Mth {
    public static int floor(float f) {
        int i = (int)f;
        return f < (float)i ? i - 1 : i;
    }

    public static int floor(double d) {
        int i = (int)d;
        return d < (double)i ? i - 1 : i;
    }


    public static int lerpInt(float f, int i, int j) {
        return i + floor(f * (float)(j - i));
    }

    public static float lerp(float f, float g, float h) {
        return g + f * (h - g);
    }

    public static double lerp(double d, double e, double f) {
        return e + d * (f - e);
    }

    public static double lerp2(double d, double e, double f, double g, double h, double i) {
        return lerp(e, lerp(d, f, g), lerp(d, h, i));
    }

    public static double lerp3(double d, double e, double f, double g, double h, double i, double j, double k, double l, double m, double n) {
        return lerp(f, lerp2(d, e, g, h, i, j), lerp2(d, e, k, l, m, n));
    }
}
