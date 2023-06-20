package de.cristelknight.util;

import java.awt.*;

public class Mth {
    public static int floor(float f) {
        int i = (int)f;
        return f < (float)i ? i - 1 : i;
    }

    public static int floor(double d) {
        int i = (int)d;
        return d < (double)i ? i - 1 : i;
    }

    public static Color color(Color color, double percentageSat, double percentageBright, boolean add) {
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        float newSat = add ? hsb[1] + (float) percentageSat : hsb[1] - (float) percentageSat;
        float newBright = add ? hsb[2] + (float) percentageBright : hsb[2] - (float) percentageBright;
        return Color.getHSBColor(hsb[0], add ? Math.min(newSat, 1f) : Math.max(newSat, 0f), add ? Math.min(newBright, 1f) : Math.max(newBright, 0f));
    }

    public static void rotateClockwise(int[][] matrix) {
        int n = matrix.length;

        // Transpose the matrix
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        // Reverse each row
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n / 2; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[i][n - 1 - j];
                matrix[i][n - 1 - j] = temp;
            }
        }
    }

}
