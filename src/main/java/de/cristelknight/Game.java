package de.cristelknight;

import de.cristelknight.parts.Part;
import de.cristelknight.parts.Shapes;
import de.cristelknight.util.Mth;
import de.cristelknight.util.Vec2i;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Random;

public class Game {

    public HashMap<Vec2i, Color> MAP = new HashMap<>();

    private Part currentPart = null;

    public boolean currentPartDown = false;

    public boolean moveQuick = false;

    private Action action = Action.NONE;

    public void setAction(Action action) {
        this.action = action;
    }

    public void tick(boolean isSlow){
        if(!isSlow && !moveQuick) return;

        if(currentPartDown){
            for(Vec2i vec2 : currentPart.getPositions()){
                MAP.put(vec2, currentPart.getColor());
            }
            currentPart = null;
            currentPartDown = false;
            action = Action.NONE;
        }


        int i = new Random().nextInt(Shapes.SHAPES.length - 1);


        if(currentPart == null) currentPart = new Part(Shapes.SHAPES[i], new Vec2i(Tetris.GAME_WIDTH / 2, 0), Shapes.COLORS[i], this);

        if(action.equals(Action.R_RIGHT)){
            currentPart.rotateClockwise(MAP.keySet());
            System.out.println("rotated");
        }
        else if(action.equals(Action.R_LEFT)){
            currentPart.rotateCounterClockwise(MAP.keySet());
        }
        action = Action.NONE;

        currentPart.tick(MAP.keySet());
    }

    public void keyPressed(KeyEvent e) {
        int kC = e.getKeyCode();

        if(kC == KeyEvent.VK_SPACE){
            moveQuick = true;
        } else if (kC == KeyEvent.VK_DOWN) {
            setAction(Action.PUT_DOWN);
        } else if (kC == KeyEvent.VK_A) {
            setAction(Action.R_LEFT);
        } else if (kC == KeyEvent.VK_D) {
            setAction(Action.R_RIGHT);
        } else if (kC == KeyEvent.VK_LEFT) {
            if(currentPart != null && !currentPartDown){
                currentPart.left(MAP.keySet());
            }
        } else if (kC == KeyEvent.VK_RIGHT) {
            if(currentPart != null && !currentPartDown){
                currentPart.right(MAP.keySet());
            }
        }
    }

    public void keyReleased(KeyEvent e){
        int kC = e.getKeyCode();
        if(kC == KeyEvent.VK_SPACE){
            moveQuick = false;
        }

    }

    public void draw(Graphics g, Graphics2D g2d, int w, int h, int blockPx) {
        g2d.drawRect((w / 2) - (Tetris.GAME_WIDTH / 2) * blockPx, 0, Tetris.GAME_WIDTH * blockPx, h);

        if(currentPart != null){
            int[][] matrix = currentPart.getShape();

            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    if (matrix[i][j] != 1) {
                        continue; // Skip empty cells
                    }

                    Vec2i pos = currentPart.getPos().minus(2);
                    drawSingleCube(g2d, blockPx, w, currentPart.getColor(), pos.x + j, pos.y + i);
                }
            }
        }


        for(Vec2i vec2 : MAP.keySet()){
            drawSingleCube(g2d, blockPx, w, MAP.get(vec2), vec2.x, vec2.y);
        }
    }


    public void drawSingleCube(Graphics2D g2d, int blockPx, int w, Color color, int x, int y){
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color colorS = color.darker(); //make middle color darker
        Color up = Mth.color(colorS, 0.3, 0.3, true); //make upper color brighter
        Color down = Mth.color(colorS, 0.3, 0.3, false); //make down color even darker


        Vec2i startPos = getPosOnScreen(w, blockPx, x, y);
        int startX = startPos.x;
        int startY = startPos.y;

        int outerPixels = blockPx / (Tetris.GAME_HEIGHT / 2);

        g2d.setColor(up);
        g2d.fillRect(startX, startY, blockPx, blockPx);
        g2d.setColor(down);
        g2d.fillRect(startX + outerPixels, startY + outerPixels, blockPx - outerPixels, blockPx - outerPixels);
        g2d.setColor(colorS);
        g2d.fillRect(startX + outerPixels, startY + outerPixels, blockPx - outerPixels * 2, blockPx - outerPixels * 2);
    }

    public Vec2i getPosOnScreen(int w, int blockPx, int x, int y){
        int startX = (w / 2) - 5 * blockPx;
        return new Vec2i(startX + x * blockPx, y * blockPx);
    }



}
