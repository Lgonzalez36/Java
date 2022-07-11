import java.util.Comparator;
/**
 * Disk Comparator class for Assignment 2
 * March 4, 2020
 * @author Luis Gonzalez
 */
public class DiskComparator implements Comparator<Disk> {
    /** DiskComparator Compares the integer value of Disk */

    /**
     * Ctor
     */
    public DiskComparator() {
    }

    /** This Method Compares the integer value of Disk*/
    public int compare(Disk o1, Disk o2) {
        return (o1.getspaceLeft() - o2.getspaceLeft());
    }
}