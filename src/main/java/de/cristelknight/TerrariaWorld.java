package de.cristelknight;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class TerrariaWorld extends JPanel {
    // World dimensions
    private static final int WIDTH = 2000;
    private static final int HEIGHT = 1200;

    // Tile dimensions
    private static final int TILE_SIZE = 32;
    private static final int NUM_TILES_X = WIDTH / TILE_SIZE;
    private static final int NUM_TILES_Y = HEIGHT / TILE_SIZE;

    // Colors
    private static final Color WHITE = Color.WHITE;
    private static final Color GREEN = Color.GREEN;
    private static final Color BROWN = new Color(165, 42, 42);

    // World map
    private int[][] worldMap;

    public TerrariaWorld() {
        // Generate world map
        worldMap = new int[NUM_TILES_Y][NUM_TILES_X];
        Random random = new Random();

        // Generate Perlin noise values
        float scale = 0.1f;
        float[][] noiseMap = generatePerlinNoise(NUM_TILES_X, NUM_TILES_Y, scale, random);

        // Generate terrain using Perlin noise
        float threshold = 0.5f;  // Adjust this value to control the terrain roughness
        for (int col = 0; col < NUM_TILES_X; col++) {
            int terrainHeight = (int) (NUM_TILES_Y * noiseMap[0][col] * threshold);
            for (int row = NUM_TILES_Y - 1; row > NUM_TILES_Y - terrainHeight - 1; row--) {
                worldMap[row][col] = 1;
            }
        }
    }

    // Generate Perlin noise using Simplex noise algorithm
    private float[][] generatePerlinNoise(int width, int height, float scale, Random random) {
        float[][] noiseMap = new float[height][width];
        float maxNoiseHeight = Float.MIN_VALUE;
        float minNoiseHeight = Float.MAX_VALUE;

        int octaveCount = 4;  // Number of Perlin noise octaves
        float amplitude = 1;
        float frequency = 1;

        for (int octave = 0; octave < octaveCount; octave++) {
            int samplePeriod = (int) (1 / frequency);
            if (samplePeriod <= 0) {
                samplePeriod = 1;
            }

            float[][] octaveNoise = new float[height][width];

            // Generate Perlin noise for this octave
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int sampleX = (int) (x / scale * frequency);
                    int sampleY = (int) (y / scale * frequency);
                    float sampleValue = (float) random.nextDouble() * 2 - 1;

                    octaveNoise[y][x] = sampleValue;
                }
            }

            // Scale and accumulate the noise
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    noiseMap[y][x] += octaveNoise[y][x] * amplitude;
                }
            }

            maxNoiseHeight = Math.max(maxNoiseHeight, amplitude);
            minNoiseHeight = Math.min(minNoiseHeight, amplitude);

            amplitude *= 0.5;
            frequency *= 2;
        }

        // Normalize the noise values to range from 0 to 1
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                noiseMap[y][x] = (noiseMap[y][x] - minNoiseHeight) / (maxNoiseHeight - minNoiseHeight);
            }
        }

        return noiseMap;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw the world
        for (int row = 0; row < NUM_TILES_Y; row++) {
            for (int col = 0; col < NUM_TILES_X; col++) {
                int tileX = col * TILE_SIZE;
                int tileY = row * TILE_SIZE;

                if (worldMap[row][col] == 1) {
                    g2d.setColor(GREEN);
                } else {
                    g2d.setColor(BROWN);
                }

                g2d.fillRect(tileX, tileY, TILE_SIZE, TILE_SIZE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Terraria World");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(WIDTH, HEIGHT);
            frame.setLocationRelativeTo(null);

            TerrariaWorld world = new TerrariaWorld();
            frame.add(world);

            frame.setVisible(true);
        });
    }
}
