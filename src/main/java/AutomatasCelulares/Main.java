package AutomatasCelulares;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Setting up the window and its graphic properties
        System.setProperty("sun.java2d.opengl", "true");
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Game of life");
        // Creating the game and adding it to the window
        Game game = new Game(600, 600, 20, 50, 50);
        window.add(game);
        // Adding the mouse listener to the window
        window.addMouseListener(game.mouseListener);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        // Starting the game thread
        game.startThread();
    }
}
