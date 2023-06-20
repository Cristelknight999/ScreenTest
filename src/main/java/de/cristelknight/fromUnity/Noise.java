package de.cristelknight.fromUnity;

import java.util.Random;

public class Noise {
    private int[][] m_aNoise;
    private int m_nNoiseWidth, m_nNoiseHeight;
    private float m_fScaleX, m_fScaleY;

    public Noise() {
        m_nNoiseWidth = 100;
        m_nNoiseHeight = 100;
        m_fScaleX = 1.0F;
        m_fScaleY = 1.0F;
        Random rnd = new Random();
        m_aNoise = new int[m_nNoiseWidth][m_nNoiseHeight];
        for (int x = 0; x < m_nNoiseWidth; x++) {
            for (int y = 0; y < m_nNoiseHeight; y++) {
                m_aNoise[x][y] = rnd.nextInt(255);
            }
        }
    }

    public float noise(float x) {
        return noise(x, 0.5F);
    }

    public float noise(float x, float y) {
        int Xint = (int) x;
        int Yint = (int) y;
        float Xfrac = x - Xint;
        float Yfrac = y - Yint;
        float x0y0 = smoothNoise(Xint, Yint); // find the noise values of the four corners
        float x1y0 = smoothNoise(Xint + 1, Yint);
        float x0y1 = smoothNoise(Xint, Yint + 1);
        float x1y1 = smoothNoise(Xint + 1, Yint + 1);
        // interpolate between those values according to the x and y fractions
        float v1 = interpolate(x0y0, x1y0, Xfrac); // interpolate in x direction (y)
        float v2 = interpolate(x0y1, x1y1, Xfrac); // interpolate in x direction (y+1)
        float fin = interpolate(v1, v2, Yfrac); // interpolate in y direction
        return fin;
    }

    private float interpolate(float x, float y, float a) {
        float b = 1 - a;
        float fac1 = (float) (3 * b * b - 2 * b * b * b);
        float fac2 = (float) (3 * a * a - 2 * a * a * a);
        return x * fac1 + y * fac2; // add the weighted factors
    }

    private float getRandomValue(int x, int y) {
        x = (x + m_nNoiseWidth) % m_nNoiseWidth;
        y = (y + m_nNoiseHeight) % m_nNoiseHeight;
        float fVal = (float) m_aNoise[(int) (m_fScaleX * x)][(int) (m_fScaleY * y)];
        return fVal / 255 * 2 - 1f;
    }

    private float smoothNoise(int x, int y) {
        float corners =
                (noise2d(x - 1, y - 1) + noise2d(x + 1, y - 1) + noise2d(x - 1, y + 1) + noise2d(x + 1, y + 1)) / 16.0f;
        float sides = (noise2d(x - 1, y) + noise2d(x + 1, y) + noise2d(x, y - 1) + noise2d(x, y + 1)) / 8.0f;
        float center = noise2d(x, y) / 4.0f;
        return corners + sides + center;
    }

    private float noise2d(int x, int y) {
        if(x < 0 || y < 0) return 0;
        x = (x + m_nNoiseWidth) % m_nNoiseWidth;
        y = (y + m_nNoiseHeight) % m_nNoiseHeight;
        float fVal = (float) m_aNoise[(int) (m_fScaleX * x)][(int) (m_fScaleY * y)];
        return fVal / 255 * 2 - 1f;
    }
}