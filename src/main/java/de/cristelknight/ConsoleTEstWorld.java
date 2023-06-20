package de.cristelknight;

import java.util.Random;

public class ConsoleTEstWorld {
    private static final int WORLD_WIDTH = 100;
    private static final int WORLD_HEIGHT = 10;
    private static final double SCALE = 0.1; // Adjust this to change the terrain density

    public static void main(String[] args) {
        double[][] world = generateWorld();
        printWorld(world);
    }

    public static double[][] generateWorld() {
        double[][] world = new double[WORLD_HEIGHT][WORLD_WIDTH];
        Random random = new Random();

        // Generate Perlin noise values
        for (int i = 0; i < WORLD_HEIGHT; i++) {
            for (int j = 0; j < WORLD_WIDTH; j++) {
                world[i][j] = noise(j * SCALE, i * SCALE, random);
            }
        }

        return world;
    }

    public static void printWorld(double[][] world) {
        for (int i = 0; i < WORLD_HEIGHT; i++) {
            for (int j = 0; j < WORLD_WIDTH; j++) {
                double value = world[i][j];
                if (value < 0.3) {
                    System.out.print("#"); // Wall
                } else {
                    System.out.print("."); // Ground
                }
            }
            System.out.println();
        }
    }

    // Perlin noise function
    public static double noise(double x, double y, Random random) {
        int X = (int) Math.floor(x) & 255;
        int Y = (int) Math.floor(y) & 255;

        x -= Math.floor(x);
        y -= Math.floor(y);

        double u = fade(x);
        double v = fade(y);

        int A = random.nextInt(256);
        int B = random.nextInt(256);
        int AA = (A + X) & 255;
        int AB = (A + X + 1) & 255;
        int BA = (B + Y) & 255;
        int BB = (B + Y + 1) & 255;

        double gradAA = grad(pseudoRandom(AB, BA), x, y);
        double gradAB = grad(pseudoRandom(BA, AB), x - 1, y);
        double gradBA = grad(pseudoRandom(AB, BA + 1), x, y - 1);
        double gradBB = grad(pseudoRandom(BA + 1, AB + 1), x - 1, y - 1);

        double lerp1 = lerp(gradAA, gradAB, u);
        double lerp2 = lerp(gradBA, gradBB, u);
        return lerp(lerp1, lerp2, v);
    }

    public static double fade(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    public static double lerp(double a, double b, double t) {
        return a + t * (b - a);
    }

    public static double grad(int hash, double x, double y) {
        int h = hash & 15;
        double u = h < 8 ? x : y;
        double v = h < 4 ? y : (h == 12 || h == 14) ? x : 0;
        return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
    }

    public static int pseudoRandom(int a, int b) {
        return (2920 * a + 1013 * b) % 1024;
    }
}
