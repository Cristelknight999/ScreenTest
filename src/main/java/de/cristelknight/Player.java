package de.cristelknight;

import de.cristelknight.util.Vec2;

public class Player {

    private Vec2 pos;
    public Player(){
        pos = Vec2.ZERO;
    }

    public Vec2 getPos() {
        return pos;
    }

    public float getX(){
        return pos.x;
    }

    public float getY(){
        return pos.y;
    }


    public void setPos(Vec2 pos) {
        this.pos = pos;
    }
}
