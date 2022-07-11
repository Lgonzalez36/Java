import java.util.Comparator;
/**
 * Key Comparator class for Assignment 2
 * March 4, 2020
 * @author Luis Gonzalez
 */
public class KeyComparator implements Comparator<Key> {
    /** KeyComparator Compares the integer value of Key */

    /**
     * Ctor for Results to use
     */
    public KeyComparator() {
    }

    /** This Method Compares the integer value of Key*/
    public int compare(Key o1, Key o2) {
        return (int) (o1.getKeyInt() - o2.getKeyInt());
    }
}