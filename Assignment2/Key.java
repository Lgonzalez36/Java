/**
 * Key class for Assignment 2
 * March 4, 2020
 * @author Luis Gonzalez
 */
public class Key implements Key340 {
    private int fileSize;

    /**
     * Key Ctor initilating the file size by int i
     * @param i
     */
    public Key(int i){
        this.fileSize = i;
    }

    /**
     * Defult Key
     */
    public Key() {
	}

    /**
     * Sets this key object's file size
     * @param k
     */
    public void setKey(int k){
        this.fileSize = k;
    }

    /**
     * @return key
     */
    public Key getKey() {return this;}

    /**
     * returns the key size
     */
    public int getKeyInt(){
        return this.fileSize;
    }

    /**
     * compares the key by its int file sizes
     */
    @Override
    public int compareTo(Key o) {
        return Integer.compare(this.fileSize, o.getKeyInt());
    }

    /**
     * checks to see if the file is >= .5GB
     * returns true if it is and false if it is not
     */
	public boolean moreThanHalf() {
        if (this.fileSize >= (500000))
            return true;
		return false;
	}
}
