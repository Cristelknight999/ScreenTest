package de.cristelknight;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Tetris extends JFrame {

    public static int GAME_HEIGHT = 20;
    public static int GAME_WIDTH = 10;

    public static int TICK_P_S = 20;

    public static int SLOW_TICKS_P_S = 5;

    private final JPanel canvas;
    private int tick = (1000 / TICK_P_S) / SLOW_TICKS_P_S;

    public Game game;

    public boolean isFullscreen = true;



    public Tetris() {
        setTitle("Tetris?");
        toggleFullscreen(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                int kC = e.getKeyCode();

                if (kC == KeyEvent.VK_F11) {
                    isFullscreen = !isFullscreen;
                    toggleFullscreen(true);
                    return;
                }


                game.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                game.keyReleased(e);
            }
        });


        game = new Game();

        canvas = new JPanel(true) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                game.draw(g, (Graphics2D) g, getWidth(), getHeight(), getHeight() / GAME_HEIGHT);
            }
        };
        canvas.setBackground(Color.GRAY);


        add(canvas);
        Timer client = new Timer(10, e -> {
            canvas.repaint();
        });
        client.start();

        Timer server = new Timer(1000 / TICK_P_S, e -> {
            boolean bl = false;
            tick++;
            if(tick >= (1000 / TICK_P_S) / SLOW_TICKS_P_S){
                bl = true;
                tick = 0;
            }
            game.tick(bl);

            canvas.repaint();
        });
        server.start();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Tetris screen = new Tetris();
            screen.setVisible(true);
        });
    }



    public void toggleFullscreen(boolean hide) {
        if(hide) setVisible(false);
        dispose();

        if (isFullscreen) {
            setUndecorated(true);
            setResizable(false);
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Dimension screenSize = toolkit.getScreenSize();
            setBounds(0, 0, screenSize.width, screenSize.height);
        } else {
            setUndecorated(false);
            setResizable(true);
            setSize((int) (getWidth() * 0.75), (int) (getHeight() * 0.75));
            setLocationRelativeTo(null);
        }

        if(hide) setVisible(true);
    }

}