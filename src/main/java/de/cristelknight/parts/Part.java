package de.cristelknight.parts;

import de.cristelknight.Game;
import de.cristelknight.util.GeneralUtil;
import de.cristelknight.util.Mth;
import de.cristelknight.util.Vec2i;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Part {

    private Vec2i mPos;

    private int[][] shape;

    private final Color color;
    private Game game;

    public Part(int[][] shape, Vec2i mPos, Color color, Game game) {
        this.shape = shape;
        this.mPos = mPos;
        this.color = color;
        this.game = game;
    }



    public void tick(Set<Vec2i> oPos){
        move(0, 1, oPos, true);

        /*
        mPos = mPos.plus(new Vec2i(0, 1));
        if(wouldCollide(oPos, true)){
            mPos = mPos.minus(new Vec2i(0, 1));
        }

         */
    }

    public void move(int x, int y, Set<Vec2i> oPos, boolean stop){
        mPos = mPos.plus(new Vec2i(x, y));
        if(wouldCollide(oPos, stop)){
            mPos = mPos.minus(new Vec2i(x, y));
        }
    }

    public void left(Set<Vec2i> oPos){
        move(-1, 0, oPos, false);
    }

    public void right(Set<Vec2i> oPos){
        move(1, 0, oPos, false);
    }

    private boolean wouldCollide(Set<Vec2i> oPos, boolean stop){
        for(Vec2i vec2 : getPositions()){
            if(GeneralUtil.isOutOfRange(vec2)){
                if(stop){
                    game.currentPartDown = true;
                }
                return true;
            }

            for(Vec2i s : oPos){
                if(s.equals(vec2)){
                    if(stop){
                        game.currentPartDown = true;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public Color getColor() {
        return color;
    }

    public Vec2i getPos() {
        return mPos;
    }

    public int[][] getShape() {
        return shape;
    }



    public void rotateClockwise(Set<Vec2i> oPos) {
        int[][] old = shape.clone();
        Mth.rotateClockwise(shape);
        if(wouldCollide(oPos, false)) shape = old;
    }

    public void rotateCounterClockwise(Set<Vec2i> oPos) {
        int[][] old = shape.clone();
        Mth.rotateClockwise(shape);
        Mth.rotateClockwise(shape);
        Mth.rotateClockwise(shape);
        if(wouldCollide(oPos, false)) shape = old;
    }


    public Set<Vec2i> getPositions(){
        Set<Vec2i> positions = new HashSet<>();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] != 1) {
                    continue; // Skip empty cells
                }
                Vec2i pos = mPos.minus(2);
                positions.add(new Vec2i(pos.x + j, pos.y + i));

            }
        }
        return positions;
    }

}
