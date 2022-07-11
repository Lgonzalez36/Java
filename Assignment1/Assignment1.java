import java.util.Scanner;
import java.util.Random;
/**
 * Main class for Assignment 1
 * Feb 4, 2021
 * @author Luis Gonzalez
 */
public class Assignment1 {
    /**
     * main method for Program 2
     * will call Freq and run that class
     * @param args unused
     */
    public static void main(String[] args){
        Random rand = new Random(); 
        int gridSize;
        int counter = 0;
        int timeCounter = 0;
        double stopWatch;
        int T;
        double[] time = new double[4]; // four data points to check the speed of percolatoin
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nHello, Program 2!!\n");
        System.out.println("\nPlease Enter the grid size. Grid size will be the user input squared: ");
        gridSize = scanner.nextInt();
        System.out.println("\nPlease Enter the amount of Trials: ");
        T = scanner.nextInt();
        scanner.close();
        Stopwatch timer = new Stopwatch();
        PercolationStats statsClass = new PercolationStats((gridSize * gridSize),T);
        
        // Will loop the amount user entered. T is the amount of trials
        // Each iteration of the outer loop will instanciate a new Perclation class and rest the vars
        while(counter < T){
            Percolation p = new Percolation(gridSize);
            double numOpen = 0;
            double fac = 0;
            
            // Inner While will loop until grid percolates
            while (p.percolates() == false){
                int iRand = rand.nextInt(gridSize);
                int jRand = rand.nextInt(gridSize);
                if (p.isFull(iRand, jRand) == true){
                    p.open(iRand, jRand);
                    numOpen++;
                }
            }

            // Record the speed of percolation from 4 trails
            if (timeCounter < 4){
                stopWatch = timer.elapsedTime();
                time[timeCounter] = stopWatch;
                System.out.println("Time:              " + stopWatch);
            }
            // End of the each trail will calculate the % percolated, send % to PercolationStats, and increase counters
            fac = numOpen / ((double)gridSize * gridSize);
            statsClass.addStatArray(fac, counter);
            counter++;
            timeCounter++;
        }

        // Print the Data
        System.out.println("Mean:                " + statsClass.mean());
        System.out.println("Standard Devation:   " + statsClass.stddev());
        System.out.println("Confi Hi:            " + statsClass.confidenceHi());
        System.out.println("Confi Low:           " + statsClass.confidenceLo());
    }
}
