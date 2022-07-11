/**
 * Percolation Class
 * Feb 4, 2021
 * @author Luis Gonzalez
 */
public class PercolationStats{
    private double[] statArray;
    private double myMean;
    private double mySD = 0.0;
    private double sum = 0.0;
    private double Ntest;

    /**
     * Ctor of the class
     * Check N and T are valid inputs
     * @param n Grid size
     * @param T Number of test
     */
    public PercolationStats(int n, int T){
        Ntest = T;
        if (n <= 0 || T <= 0)
        throw new IllegalArgumentException("n or trials is an illegal argument");
        statArray = new double[T];
    }

    /**
     * Method calculates the mean
     * @return mean
     */
    public double mean(){
        myMean = sum / Ntest;
        return myMean;
    }

    /**
     * Method calculates the standard deviation
     * @return mySD
     */
    public double stddev(){
        for (int i=0; i < Ntest; i++){
            mySD = mySD + Math.pow((statArray[i] - myMean), 2);
        }
        return mySD = Math.sqrt(mySD/Ntest);
    }

    /**
     * Method calculates the confidence low
     * @return confidenceLo
     */
    public double confidenceLo(){ return (myMean - ((1.96 * mySD)/(Math.sqrt(Ntest))));}

    /**
     * Method calculates the confidence high
     * @return confidenceHi
     */
    public double confidenceHi(){ return (myMean + ((1.96 * mySD)/(Math.sqrt(Ntest))));}

    /**
     * Method add the sum and stores the mean of each trial in an array
     * @param fac % of what it took to percolate the grid
     * @param counter Trial number used as the index for the array
     */
	public void addStatArray(double fac, int counter) {
        statArray[counter] = fac;
        sum = sum + fac;
	}
}