package main;

import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile (common tile size for pixel 2d games)
    final int scale = 3; // scales originalTileSize because it's too small on modern screens
    public final int tileSize = originalTileSize * scale; // originalTileSize scaled up to 48x48 tile

    // 4:3 Aspect Ratio
    public final int maxScreenCol = 16; // 48x48 * 16 = 768 for the column
    public final int maxScreenRow = 12; // 48x48 * 12 = 576 for the row
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // World Settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    // FPS
    int FPS = 60;

    TileManager tileManager = new TileManager(this);
    KeyHandler keyHandler = new KeyHandler(); // instantiates the key handler
    Thread gameThread; // keeps the game running until it is stopped
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public Player player = new Player(this, keyHandler);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // sets the preferred size of the game panel to be YOUR screen's width and height
        this.setBackground(Color.black); // sets the background color of the game panel to black
        this.setDoubleBuffered(true); // enabling this can improve game's rendering performance
        this.addKeyListener(keyHandler); // adds the event listener for key presses
        this.setFocusable(true); // allows main.GamePanel to be "focused" to receive key input
    }

    public void startGameThread() {
        gameThread = new Thread(this); // uses the main.GamePanel as argument to initialize the game thread
        gameThread.start(); // starts the gameThread and calls the run() method
    }

    @Override
    public void run() { // game loop
        double drawInterval = (double) 1000000000 / FPS; // 0.01666 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        while(gameThread != null) { // while gameThread exists, it repeats the process inside the while loop
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (delta >= 1) {
                update(); // UPDATE: information such as character positions
                repaint(); // DRAW: draw the screen with the update information (calls the paintComponent method)
                delta--;
            }
        }
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics; // changed the Graphics into a Graphics2D to have more functions than Graphics class
        tileManager.draw(graphics2D);
        player.draw(graphics2D);
        graphics2D.dispose(); // disposes the graphics to save system resources that it is using
    }
}
