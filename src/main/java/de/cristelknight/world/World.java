package de.cristelknight.world;

import de.cristelknight.Player;
import de.cristelknight.util.Vec2;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class World {

    private Player player;

    private WorldGenerator generator;
    public World(Player player){
        this.player = player;
        this.generator = new WorldGenerator(new Random().nextLong());
    }

    public void tick(){

    }

    public static Map<Vec2, String> WORLD = new HashMap<>();

    public void draw(Graphics g, Graphics2D g2d, int w, int h, int blockPx) {
        int startX = (int) player.getX() - w / 2;
        int startY = (int) player.getY() - h / 2;

        g2d.setColor(Color.GRAY);

        for(int x = 0; x <= w; x++){
            for(int y = 0; y <= h; y++){
                String block = null;
                Vec2 pos = new Vec2(x, y);
                for(Vec2 vec2 : WORLD.keySet()){
                    if(vec2.equals(pos)){
                        block = WORLD.get(vec2);
                    }
                }

                if(block == null){
                    block = generator.getBlock(x, y);
                    WORLD.put(pos, block);
                }

                if(block.equals("air")) continue;

                g2d.fillRect(x * blockPx, y * blockPx, blockPx, blockPx);
            }
        }

    }
}
