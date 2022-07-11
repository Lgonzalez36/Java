import java.io.File;
import java.io.FileNotFoundException;
import java.util.AbstractQueue;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

public class KdTreeST<Key extends Comparable<Key>, Value> {
    private static class Node {
        private Point2D point;
        private RectHV rect;
        private Node left;
        private Node right;
        

        public Node(Point2D p, RectHV rect) {
            RectHV r = rect;
            if (r == null)
                r = new RectHV(0, 0, 1, 1);
            this.rect = r;
            this.point = p;
        }
    }

    private Node root;
    private int size;

    // construct an empty set of points
    public KdTreeST() {
        root = null;
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() { return root == null; }

    // number of points in the set
    public int size() { return size; }

    // larger or equal keys go right
    private Node insertH(Node x, Point2D point, RectHV rect) {
        if (x == null) {
            size++;
            return new Node(point, rect);
        }
        if (x.point.equals(point)) return x;

        RectHV r;
        int cmp = Point2D.Y_ORDER.compare(x.point, point);
        if (cmp > 0) {
            if (x.left == null)
                r = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), x.point.y());
            else
                r = x.left.rect;
            x.left = putV(x.left, point, r);
        } else {
            if (x.right == null)
                r = new RectHV(rect.xmin(), x.point.y(), rect.xmax(), rect.ymax());
            else
                r = x.right.rect;
            x.right = putV(x.right, point, r);
        }

        return x;
    }

    // larger or equal keys go right
    private Node putV(Node x, Point2D point, RectHV rect) {
        if (x == null) {
            size++;
            return new Node(point, rect);
        }
        if (x.point.equals(point)) return x;

        RectHV r;
        int cmp = Point2D.X_ORDER.compare(x.point, point);
        if (cmp > 0) {
            if (x.left == null)
                r = new RectHV(rect.xmin(), rect.ymin(), x.point.x(), rect.ymax());
            else
                r = x.left.rect;
            x.left = insertH(x.left, point, r);
        } else {
            if (x.right == null)
                r = new RectHV(x.point.x(), rect.ymin(), rect.xmax(), rect.ymax());
            else
                r = x.right.rect;
            x.right = insertH(x.right, point, r);
        }

        return x;
    }

    // add the point p to the set (if it is not already in the set)
    public void put(Point2D point) {
        if (isEmpty())
            root = putV(root, point, null);
        else
            root = putV(root, point, root.rect);
    }

    // larger or equal keys go right
    private boolean contains(Node x, Point2D point, boolean vert) {
        if (x == null) return false;
        if (x.point.equals(point)) return true;
        int cmp;
        if (vert) cmp = Point2D.X_ORDER.compare(x.point, point);
        else cmp = Point2D.Y_ORDER.compare(x.point, point);
        if (cmp > 0) return contains(x.left, point, !vert);
        else return contains(x.right, point, !vert);
    }

    // does the set contain the point p?
    public boolean contains(Point2D point) {
        return contains(root, point, true);
    }

    private void range(Node x, RectHV rect, AbstractQueue<Point2D> q) {
        if (x == null)
            return;
        if (rect.contains(x.point)){
            q.add(x.point);
        }
        if (x.left != null && rect.intersects(x.left.rect))
            range(x.left, rect, q);
        if (x.right != null && rect.intersects(x.right.rect))
            range(x.right, rect, q);
    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        AbstractQueue<Point2D> q = new LinkedBlockingQueue<Point2D>();
        range(root, rect, q);
        return q;
    }

    public Point2D nearest(Point2D target) {
        if (target == null) throw new java.lang.NullPointerException(
                "called contains() with a null Point2D");
        if (isEmpty()) return null;
        return nearest(root, target, root.point, true);
    }
    
    private Point2D nearest(Node n, Point2D target, Point2D champion, boolean evenLevel) {
        
        // Handle reaching the end of the tree
        if (n == null) return champion;
        
        // Handle the given point exactly overlapping a point in the BST
        if (n.point.equals(target)) return target;
        
        // Determine if the current Node's point beats the existing champion
        if (n.point.distanceSquaredTo(target) < champion.distanceSquaredTo(target))
            champion = n.point;
        
        /**
         * Calculate the distance from the search point to the current
         * Node's partition line.
         * 
         * Primarily, the sign of this calculation is useful in determining
         * which side of the Node to traverse next.
         * 
         * Additionally, the magnitude to toPartitionLine is useful for pruning.
         * 
         * Specifically, if we find a champion whose distance is shorter than
         * to a previous partition line, then we know we don't have to check any
         * of the points on the other side of that partition line, because none
         * can be closer.
         */
        double toPartitionLine = comparePoints(target, n, evenLevel);
        
        /**
         * Handle the search point being to the left of or below
         * the current Node's point.
         */
        if (toPartitionLine < 0) {
            champion = nearest(n.left, target, champion, !evenLevel);
            
            // Since champion may have changed, recalculate distance
            if (n.left != null && (champion.distanceSquaredTo(target) >= n.left.rect.distanceSquaredTo(target))){
                champion = nearest(n.right, target, champion, !evenLevel);
            }
        }
        
        /**
         * Handle the search point being to the right of or above
         * the current Node's point.
         * 
         * Note that, since insert() above breaks point comparison ties
         * by placing the inserted point on the right branch of the current
         * Node, traversal must also break ties by going to the right branch
         * of the current Node (i.e. to the right or top, depending on
         * the level of the current Node).
         */
        else {
            champion = nearest(n.right, target, champion, !evenLevel);
            
            // Since champion may have changed, recalculate distance
            if (n.right != null && (champion.distanceSquaredTo(target) >= n.right.rect.distanceSquaredTo(target))){
                champion = nearest(n.left, target, champion, !evenLevel);
            }
        }
        
        return champion;
    }
    
    /**
     * The distance and direction from the given point to the given Node's
     * partition line.
     * 
     * If the sign of the returned double is negative, then the given point
     * lies or should lie on the left branch of the given Node.
     * 
     * Otherwise (including where the difference is exactly 0), then the
     * given point lies or should lie on the right branch of the given Node.
     * 
     * @param p the point in question
     * @param n the Node in question
     * @param evenLevel is the current level even?  If so, then the Node's
     *        partition line is vertical, so the point will be to the left
     *        or right of the Node.  If not, then the Node's partition line
     *        is horizontal, so the point will be above or below the Node.
     * @return the distance and direction from p to n's partition line
     */
    private double comparePoints(Point2D target, Node n, boolean evenLevel) {
        if (evenLevel) {
            return target.x() - n.point.x();
        }
        else return target.y() - n.point.y();
    }                    

    
    public static void main(String[] args) throws FileNotFoundException {
        KdTreeST<Point2D,Integer> kdtree = new KdTreeST<Point2D,Integer>();
        File myFile = new File("input1M.txt");
        Scanner input = new Scanner(myFile);
        for (int i = 0; input.hasNextLine(); i++) {
            double x = input.nextDouble();
            double y = input.nextDouble();
            Point2D p = new Point2D(x, y);
            kdtree.put(p);
        }
        input.close();  
        System.out.println(" \n");
        int countKD = 0;
        long start = System.currentTimeMillis();
        while ( countKD < 10000000 ){
            Point2D queryRandom = new Point2D(RefinedRandom.uniform(0.0, 1.0), RefinedRandom.uniform(0.0, 1.0));
            Point2D kdtreeNearest = kdtree.nearest(queryRandom);
            long end = System.currentTimeMillis();
            long timeElapsed = end - start;
            if( timeElapsed >= 1000) {
                end = System.currentTimeMillis();
                timeElapsed = (end - start);
                System.out.println(" KD Tree ");
                System.out.println(" Time  : " + timeElapsed + " milliseconds");
                System.out.println(" calls : " + countKD);
                System.out.println(" \n");
                break;
            }
            countKD++;
        }

/*      long end = System.currentTimeMillis();
        long timeElapsed = (end - start);
        System.out.println(" KD Tree: ");
        System.out.println(" Time : " + timeElapsed);
        System.out.println(" calls : " + countKD); */

        /*RectHV rect = new RectHV(Math.min(0.0, 0.0), Math.min(0.0, 0.0),
        Math.max(1.1, 1.1), Math.max(1.1, 1.1));
        Draw.setPenColor(Draw.BLACK);
        Draw.setPenRadius(0.01);
        for (Point2D p : kdtree.range(rect)){
            System.out.println(p.toString());
            p.draw();
        } */
    }
}