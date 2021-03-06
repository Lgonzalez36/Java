/* *****************************************************************************
 *  Read points from a file and
 *  draw to standard draw. Highlight the closest point to the mouse.
 *
 *  The nearest neighbor according to the brute-force algorithm is drawn
 *  in red; the nearest neighbor using the k-d tree algorithm is drawn in blue.
 **************************************************************************** */
 
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class NearestNeighborVisualizer {
    public static void main(String[] args) throws FileNotFoundException {
        File myFile = new File("input1M.txt");
        Scanner input = new Scanner(myFile);
 
        // initialize the two data structures with point from file
        PointST<Point2D,Integer> brute = new PointST<Point2D,Integer>();
        KdTreeST<Point2D,Integer> kdtree = new KdTreeST<Point2D,Integer>();
        for (int i = 0; input.hasNextLine(); i++) {
            double x = input.nextDouble();
            double y = input.nextDouble();
            Point2D p = new Point2D(x, y);
            kdtree.put(p);
            brute.put(p, i);
        }
        
        input.close();        
        // process nearest neighbor queries
        Draw.enableDoubleBuffering();
        while (true) {
            
            // the location (x, y) of the mouse
            double x = Draw.mouseX();
            double y = Draw.mouseY();
            Point2D queryRandom = new Point2D(x, y);
            // random points
            // Point2D queryRandom = new Point2D(RefinedRandom.uniform(0.0, 1.0), RefinedRandom.uniform(0.0, 1.0));

            // draw all of the points
            Draw.clear();
            Draw.setPenColor(Draw.BLACK);
            Draw.setPenRadius(0.01);
            for (Point2D p : brute.keys()) {
                p.draw();
            }
 
            // draw in red the nearest neighbor according to the brute-force algorithm
            Draw.setPenRadius(0.03);
            Draw.setPenColor(Draw.RED);
            Point2D bruteNearest = brute.nearest(queryRandom);
            if (bruteNearest != null) bruteNearest.draw();
            Draw.setPenRadius(0.02);
 
            // draw in blue the nearest neighbor according to the k-d tree algorithm
            Draw.setPenColor(Draw.BLUE);
            Point2D kdtreeNearest = kdtree.nearest(queryRandom);
            if (kdtreeNearest != null) kdtreeNearest.draw();
            Draw.show();
            Draw.pause(20);
        }
    }
}