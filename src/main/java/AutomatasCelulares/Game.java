package AutomatasCelulares;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class Game extends JPanel implements Runnable{

    // Defining the variables
    private static final long NANOS_IN_SECOND = 1_000_000_000;
    private final int width;
    private final int height;
    private Thread gameThread;
    private double targetFps;
    private int cellWidth;
    private int cellHeight;
    private int numCols;
    private int numRows;

    // Defining the objects
    private CellBehaviour cellBehaviour;
    MouseListener mouseListener;

    // This variable will be used to prevent the grid from being updated while it is being drawn
    boolean isPainting = false;

    // Constructor
    public Game(int width, int height, double targetFps, int numCols, int numRows) {
        this.width = width;
        this.height = height;
        this.cellWidth = width / numCols;
        this.cellHeight = height / numRows;
        this.numCols = numCols;
        this.numRows = numRows;
        // Mouse reading speed will be the same as the game speed
        this.targetFps = targetFps;
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.WHITE);
        setDoubleBuffered(true);
        setFocusable(true);
        cellBehaviour = new CellBehaviour(numCols, numRows);
        mouseListener = new MouseListener();
    }

    // Method to start the game thread
    public void startThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // Method to update the game
    @Override
    public void run() {
        final double NANOS_BETWEEN_UPDATES = NANOS_IN_SECOND / targetFps;
        long currentFrame, lastFrame;
        boolean exit = false;
        double deltaTime;
        lastFrame = System.nanoTime();

        while (!exit) {
            currentFrame = System.nanoTime();
            while (currentFrame - lastFrame > NANOS_BETWEEN_UPDATES) {
                deltaTime = (double)(currentFrame - lastFrame) / NANOS_IN_SECOND;
                repaint();
                update(deltaTime);
                lastFrame = currentFrame;
            }
        }
    }

    // Overriding the paintComponent method to draw the grid and the cells
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        // The grid is drawn
        drawGrid(g2);
        // When the grid is being drawn, the game is paused
        if (!isPainting){
            cellBehaviour.processNextGeneration();
        }

        // The mouse input is processed
        processInput();

        // The cells are drawn
        drawGeneration(g2, cellBehaviour.getGrid());
    }

    // ???
    public void update(double deltaTime) {
    }

    /**
     * Processes the mouse input and changes the cell behaviour accordingly
     */
    public void processInput() {
        // The mouse position is read
        int x = MouseInfo.getPointerInfo().getLocation().x - getLocationOnScreen().x;
        int y = MouseInfo.getPointerInfo().getLocation().y - getLocationOnScreen().y;

        // If the mouse is being pressed, the coordinates define the cell that will be changed
        if (x >= 0 && x < width && y >= 0 && y < height) {
            if (mouseListener.x >= 0 || mouseListener.y >= 0) {
                //Set painting state to true
                isPainting = true;
                // Paint the cell
                cellBehaviour.setCellState(x / cellWidth, y / cellHeight, true);
            } else{
                // If the mouse is not being pressed, the painting state is set to false
                isPainting = false;
            }
        }
    }

    /**
     * Draws the grid
     * @param g Graphics2D object
     */
    public void drawGrid(Graphics2D g){
        g.setColor(Color.BLACK);
        for (int x = 0; x < numCols; x++) {
            g.drawLine(x * cellWidth, 0, x * cellWidth, height);
        }
        for (int y = 0; y < numRows; y++) {
            g.drawLine(0, y * cellHeight, width, y * cellHeight);
        }
    }

    /**
     * Draws the cells
     * @param g Graphics2D object
     * @param generation 2D boolean array containing the cells
     */
    public void drawGeneration(Graphics2D g, boolean[][] generation){
        for (int x = 0; x < numCols; x++) {
            for (int y = 0; y < numRows; y++) {
                if(generation[x][y]){
                    g.fillRect(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
                }
            }
        }
    }
}
