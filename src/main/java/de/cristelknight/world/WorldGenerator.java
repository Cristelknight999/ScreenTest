package de.cristelknight.world;

import java.awt.*;
import java.util.Random;

public class WorldGenerator {

    private final ChunkProviderSky sky;
    private final Random random;
    public WorldGenerator(long seed){
        this.random = new Random();
        random.setSeed(seed);
        sky = new ChunkProviderSky(seed);
    }

    public String getBlock(int x, int y){

    }

    public double generateWorld(int x, int y) {
        return noise(x * DENSITY, y * DENSITY, random);
    }

}
