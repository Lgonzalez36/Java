import java.lang.Math; 
/**
 * Percolation Class
 * Feb 4, 2021
 * @author Luis Gonzalez
 */
public class Percolation {
    private WeightedQuickUnionUF w;
    private int grid[][];
    private int gridSize;

    /**
     * Ctor for Percolation
     * Responsible for creating the gride to be NxN
     * and setting all elements to 0 (full/closed)
     */
    public Percolation(int N) {
        gridSize = (N * N);
        w = new WeightedQuickUnionUF(gridSize+2);
        grid = new int[N][N];
    }

    /**
     * Method will open the closed location and check its adjacent locations to unionize 
     * @param i The randomized row
     * @param j The randomized col
     */
    public void open(int i, int j) { 
        grid[i][j] = 1;
        
        // Connect the location in the first row to the top imaginary location
        if (i == 0){
            w.union(0, coordXYto1D(i,j));
        }
        
        // Connect the location in the last row to the bottom imaginary location
        if (i == ((int)(Math.sqrt(gridSize) - 1))){
            w.union((gridSize+1), coordXYto1D(i,j));
        }

        // Connect the location and the location to the bottom of it
        if (i+1 < ((int)(Math.sqrt(gridSize)))){
            if (grid[i+1][j] == 1){
                w.union(coordXYto1D(i, j), coordXYto1D(i+1, j));
            }
        }

        // Connect the location and the location to the top
        if (i-1 > -1){
            if (grid[i-1][j] == 1){
                w.union(coordXYto1D(i, j), coordXYto1D(i-1, j));
            }
        }

        // Connect the location and the location to the right
        if (j+1 < ((int)(Math.sqrt(gridSize)))){
            if (grid[i][j+1] == 1){
                w.union(coordXYto1D(i, j), coordXYto1D(i, j+1));
            }
        }

        // Connect the location and the location to the left
        if (j-1 > -1){
            if (grid[i][j-1] == 1){
                w.union(coordXYto1D(i, j), coordXYto1D(i, j-1));
            }
        }
    }

    /**
     * Method converts XY coordinates to numberical 1 to (N+1)
     * @param i The randomized row
     * @param j The randomized col
     * @return The numerical conversion from 1 to (N+1)
     */
    private int coordXYto1D(int i, int j){
        return (int)((i*(Math.sqrt(gridSize))) + j) +1;
    }

    /**
     * Method checks if random location is opened
     * @param i The randomized row
     * @param j The randomized col
     * @return True if location is open
     * @return False if location is not open
     */
    public boolean isOpen(int i, int j){
        if ( grid[i][j] == 1){ return true;}
        return false;
    }

    /**
     * Method checks if random location is closed
     * @param i The randomized row
     * @param j The randomized col
     * @return True if location is closed
     * @return False if location is open
     */
    public boolean isFull(int i, int j){ 
        if ( grid[i][j] == 0){ return true;}
        return false;
    }

    /**
     * Method check if grid percolated by checking if
     * both virtual locations top and bottom are in the same set
     * @return True if both points are in the same set
     * @return False if both points are not in the same set
     */
    public boolean percolates(){ return w.connected(0, gridSize+1);}

    /**
     * Method prints grid if needed
     */
	public void print(){
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid.length; j++){
                System.out.printf("%2d ", grid[i][j]);
        }
            System.out.println();
        }
	}
}
