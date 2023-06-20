package de.cristelknight.util;

import de.cristelknight.Tetris;

public class GeneralUtil {

    public static boolean isInRange(Vec2i vec2){
        int x = vec2.x;
        int y = vec2.y;
        return x <= Tetris.GAME_WIDTH && y <= Tetris.GAME_HEIGHT && x >= 0 && y >= 0;
    }

    public static boolean isOutOfRange(Vec2i vec2){
        int x = vec2.x;
        int y = vec2.y;
        return x >= Tetris.GAME_WIDTH || x < 0 || y >= Tetris.GAME_HEIGHT;
    }

    public static boolean isDown(Vec2i vec2){
        int y = vec2.y;
        System.out.println("is down");
        return y >= Tetris.GAME_HEIGHT;
    }


}
