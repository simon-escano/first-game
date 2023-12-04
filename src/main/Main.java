package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("First Game");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack(); // causes this window to be sized to fit the preferred size and layouts of its subcomponents

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
    }
}
