import java.util.ArrayList;
/**
 * Disk class for Assignment 2
 * March 4, 2020
 * @author Luis Gonzalez
 */
public class Disk {
    private int id;
    private int spaceLeft;
    private ArrayList<Key> disk;
    
    /**
     * Ctor for the Disk Class
     */
    public Disk() {
        this.spaceLeft = 1000000;
        this.disk = new ArrayList<>();
	}
     
    /**
     * getters for Disk ID and space left
     * @return int id, space left
     */
    public int getID() {return id;}
    public int getspaceLeft() {return spaceLeft;}
    public void setID(int id) {this.id = id;}

    /**
     * Method takes in Key k an subtracks it from
     * the space. The result is the space left from adding a key
     * @param k
     */
    public void addKeytoDisk(Key k){
        this.spaceLeft =  this.spaceLeft - k.getKeyInt();
        disk.add(k);
    }

    /**
     * Method checks to see if the disk has enough space left 
     * to add another key. If true it returns true
     * if false it returns false
     * @param k2
     * @return
     */
	public boolean spaceLeft(Key k2) {
        int tempSpace;
        int size = k2.getKeyInt();
        tempSpace = spaceLeft - size;
        if (tempSpace > -1 ){
            return true;
        }
		return false;
	}
     
    /**
     * Method prints the files from the disk
     */
	public void printfiles() {
        System.out.print("  " + "[ ");
        for (int i=0; i < disk.size(); i++){
            System.out.print(disk.get(i).getKeyInt() + " ");
        }
        System.out.print("]");
	}
}
