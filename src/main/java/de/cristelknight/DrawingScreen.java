package de.cristelknight;

import de.cristelknight.world.World;

import javax.swing.*;
import java.awt.*;

public class DrawingScreen extends JFrame {
    private final JPanel canvas;
    private final World world;
    private final Player player;
    private int tick = 0;

    public final int blockPx;

    public DrawingScreen() {
        setTitle("Game LOL");

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        setSize((int) (screenSize.width * 0.75), (int) (screenSize.height * 0.75));
        blockPx = screenSize.height / 200;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Set the frame to full-screen mode


        player = new Player();
        world = new World(player);


        canvas = new JPanel(true) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                world.draw(g, (Graphics2D) g, getWidth() / blockPx, getHeight() / blockPx, blockPx);
            }
        };


        add(canvas);

        Timer timer = new Timer(10, e -> {
            tick++;
            if(tick >= 5){
                world.tick();
                tick = 0;
            }
            canvas.repaint();
        });
        timer.start();
    }




    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DrawingScreen screen = new DrawingScreen();
            screen.setVisible(true);
        });
    }

}
