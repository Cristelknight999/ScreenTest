package de.cristelknight.world;

import de.cristelknight.util.PerlinOctaveNoise;

import java.util.Random;

public class ChunkProviderSky {
    private final PerlinOctaveNoise surfaceOctaveNoise;

    private Random random;

    public ChunkProviderSky(long seed) {
        random = new Random(seed);
        this.surfaceOctaveNoise = new PerlinOctaveNoise(this.random, 4, true);
    }

    public int[] provideSurface(Chunk chunk) {
        double scale = 0.03125D;

        int chunkX = chunk.getX();

        int[] heights;

        Random rand = this.createSurfaceRandom(chunkX);

        double[] surfaceNoise = surfaceOctaveNoise.sampleBeta(
                chunkX * 16, 0.0D, 0.0D,
                16, 16, 1,
                scale * 2D, scale * 2D, scale * 2D
        );

        for (int localX = 0; localX < 16; localX++) {
            int surfaceDepth = (int) (surfaceNoise[localX * 16] / 3D + 3D + rand.nextDouble() * 0.25D);

        }
    }

    protected Random createSurfaceRandom(int chunkX) {
        long seed = (long)chunkX * 0x4f9939f508L;
        return new Random(seed);
    }

}
