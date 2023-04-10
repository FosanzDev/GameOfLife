package AutomatasCelulares;

public class CellBehaviour {

    // Defining the grid of cells
    private boolean[][] grid;

    // Defining the grid of cells for the next generation
    private boolean[][] future;


    // Constructor
    public CellBehaviour(int numCols, int numRows) {
        grid = new boolean[numCols][numRows];
    }

    // Method to get the next generation of a cell
    public void cellNextState(int x, int y) {
        // Counting the number of neighbours
        int neighbours = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    //Ignore the cell itself
                    continue;
                }
                try {
                    if (grid[x + i][y + j]) {
                        neighbours++;
                    }
                // Catching the exception when the cell is on the edge of the grid
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
            }
        }

        // Applying the rules of the game
        if (grid[x][y]) {
            // If the cell is alive and has less than 2 or more than 3 neighbours, it dies
            if (neighbours < 2) {
                future[x][y] = false;
            } else if (neighbours > 3) {
                future[x][y] = false;

            // Else, it stays alive
            } else {
                future[x][y] = true;
            }

        // If the cell is dead and has 3 neighbours, it becomes alive
        } else {
            if (neighbours == 3) {
                future[x][y] = true;
            }
        }
    }

    // Method to set the state of a cell from outside the class
    public void setCellState(int x, int y, boolean state) {
        grid[x][y] = state;
    }


    // Method to process the next generation
    public void processNextGeneration() {
        future = new boolean[grid.length][grid[0].length];
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                cellNextState(x, y);
            }
        }
        grid = future;
        future = null;
    }

    // Method to get the grid of cells
    public boolean[][] getGrid() {
        return grid;
    }

}
