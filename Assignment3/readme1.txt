/* *****************************************************************************
 *  Name:     Luis Gonzalez
 *  NetID:    1058634
 *
 *  Partner Name:       
 *  Partner NetID:          
 *
 *  Hours to complete assignment (optional): 18hrs
 *
 **************************************************************************** */

Programming Assignment 3: K-d Trees


/* *****************************************************************************
 *  Describe the Node data type you used to implement the
 *  2d-tree data structure.
 **************************************************************************** */
	private Point2D point;
        private RectHV rect;
        private Node left;
        private Node right;

/* *****************************************************************************
 *  Describe your method for range search in a k-d tree.
 **************************************************************************** */
	I have two range method calls in my K-D tree
	the first call intializes an abstract queue and then
	another range method is called with the root, rect, and queue 
	in the header. The next method is a recurive method that searches 
	the tree if the node's rectangel intersects with the query
	if it does, then does another recursive call. if the query contains
	a node then it would be added to the queue

/* *****************************************************************************
 *  Describe your method for nearest neighbor search in a k-d tree.
 **************************************************************************** */
	To find the nearest neighbor, i made two function calls as well
	the first call is to set up the next call. In the first one i called
	the second one with the headers root, the queryPoint, and the point 
	of the root, and a boolean
	
	The second call is also a recursive call. In the recursive call
	the first step is to set the min of the root point (min = mp)
	then check to see if the node is null, is null we return the min
	
	then we have the complicated pruning to optimize our method
	we first check to see if the current node point is < the current min
	then we partition the x's and y's subtree based on the level, to see
	which is closet. Then we do another call to the recursive method with 
	the closest point and continue til we find the min.


	

/* *****************************************************************************
 *  How many nearest-neighbor calculations can your PointST implementation
 *  perform per second for input1M.txt (1 million points), where the query
 *  points are random points in the unit square?
 *
 *  Fill in the table below, using one digit after the decimal point
 *  for each entry. Use at least 1 second of CPU time.
 *  (Do not count the time to read the points or to build the 2d-tree.)
 *
 *  Repeat the same question but with your KdTreeST implementation.
 *
 **************************************************************************** */


                 	# calls to         /   CPU time     =   # calls to nearest()
                 	client nearest()       (seconds)        per second
                	------------------------------------------------------
PointST:		480		    1 seconds	        ~480

KdTreeST:		244,951		    1 seconds	        ~244,951

Note: more calls per second indicates better performance.


/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */
	none that I am aware of.

/* *****************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings and lectures, but do
 *  include any help from people (including classmates and friends) and 
 *  attribute them by name.
 **************************************************************************** */
	Andy and Emanual were great at giving me help
	Andy - gave me the docs i was missing from black board
	Emanual - helped me to understand the time complexity from both trees

/* *****************************************************************************
 *  Describe any serious problems you encountered.                    
 **************************************************************************** */
	nearest() was the worst and trying to figure out the node type was 
	also difficult. 

/* *****************************************************************************
 *  If you worked with a partner, give one
 *  sentence explaining what each of you contributed.
 **************************************************************************** */
	worked on it alone


/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback   
 *  on  how helpful the class meeting was and on how much you learned 
 *  from doing the assignment, and whether you enjoyed doing it.
 **************************************************************************** */
	I feel like we hardly ever went over the kd tree and it was hard
	and it was hard to implement it alone without much knowledge about it


/* *****************************************************************************
 *  Include the screenshots of your output.
 **************************************************************************** */


/* *****************************************************************************
 **************************************************************************** */
/* ******************************************************************************
 *  Read points from a file and
 *  draw to standard draw. Also draw all of the points in the rectangle
 *  the user selects by dragging the mouse.
 *
 *  The range search results using the brute-force algorithm are drawn
 *  in red; the results using the k-d tree algorithms are drawn in blue.
 *
 **************************************************************************** */
 
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
 
public class RangeSearchVisualizer {
 
    public static void main(String[] args) throws FileNotFoundException {
        File myFile = new File("input10.txt");
        Scanner input = new Scanner(myFile);
        
        // initialize the data structures with n points from file
        PointST<Point2D,Integer> brute = new PointST<Point2D,Integer>();
        KdTreeST<Point2D,Integer> kdtree = new KdTreeST<Point2D,Integer>();
        for (int i = 0; input.hasNextLine(); i++) {
            double x = input.nextDouble();
            double y = input.nextDouble();
            Point2D p = new Point2D(x, y); // p: KEY    i: Value
            kdtree.put(p);
            brute.put(p, i);
        }
        
        input.close();  
        // the rectangle drawn by the user
        double x0 = 0.0, y0 = 0.0;      // initial endpoint of rectangle
        double x1 = 0.0, y1 = 0.0;      // current location of mouse
        boolean isDragging = false;     // is the user dragging a rectangle
 
        // draw the points
        Draw.clear();
        Draw.setPenColor(Draw.BLACK);
        Draw.setPenRadius(0.01);
        for (Point2D p : brute.keys()){
            p.draw();
        }
           
        // process query rectangle drawn by user
        Draw.enableDoubleBuffering();
        while (true) {
            // user starts to drag a rectangle
            if (Draw.isMousePressed() && !isDragging) {
                x0 = x1 = Draw.mouseX();
                y0 = y1 = Draw.mouseY();
                isDragging = true;
            }
 
            // user is dragging a rectangle
            else if (Draw.isMousePressed() && isDragging) {
                x1 = Draw.mouseX();
                y1 = Draw.mouseY();
            }
 
            // user stops dragging the rectangle
            else if (!Draw.isMousePressed() && isDragging) {
                isDragging = false;
            }
 
            // draw the points
            Draw.clear();
            Draw.setPenColor(Draw.BLACK);
            Draw.setPenRadius(0.01);
            for (Point2D p : brute.keys()){
                 p.draw();
            }
 
            // draw the rectangle
            RectHV rect = new RectHV(Math.min(x0, x1), Math.min(y0, y1),
                                     Math.max(x0, x1), Math.max(y0, y1));
            Draw.setPenColor(Draw.BLACK);
            Draw.setPenRadius();
            rect.draw();
 
            // draw the range search results for brute-force data structure in red
            Draw.setPenRadius(0.03);
            Draw.setPenColor(Draw.RED);
             for (Point2D p : brute.keys(rect)){
                 p.draw();
            }  
 
            // draw the range search results for k-d tree in blue
            Draw.setPenRadius(0.02);
            Draw.setPenColor(Draw.BLUE);
            for (Point2D p : kdtree.range(rect))
                p.draw();
 
            // display everything on screen
            Draw.show();
            Draw.pause(20);
        }
    }
}

/* *****************************************************************************
 **************************************************************************** */
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

/* *****************************************************************************
 **************************************************************************** */
/******************************************************************************
 *  A symbol table implemented using a left-leaning red-black BST.
 *  This is the 2-3 version.
 ******************************************************************************/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*; 
//import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingQueue; 

/**
 *  The BST class represents an ordered symbol table of generic
 *  key-value pairs.
 *  It supports the usual put, get, contains,
 *  delete, size, and is-empty methods.
 *  It also provides ordered methods for finding the minimum,
 *  maximum, floor, and ceiling.
 *  It also provides a keys method for iterating over all of the keys.
 *  A symbol table implements the associative array abstraction:
 *  when associating a value with a key that is already in the symbol table,
 *  the convention is to replace the old value with the new value.
 *  Unlike java.util.Map, this class uses the convention that
 *  values cannot be nullâ€”setting the
 *  value associated with a key to null is equivalent to deleting the key
 *  from the symbol table.
 *  
 *  It requires that
 *  the key type implements the Comparable interface and calls the
 *  compareTo() method to compare two keys. It does not call either
 *  equals() or hashCode().
 *  
 *  This implementation uses a left-leaning red-black BST. 
 *  The put, get, contains, remove,
 *  minimum, maximum, ceiling, floor,
 *  rank, and select operations each take
 *  Î˜(logn) time in the worst case, where n is the
 *  number of key-value pairs in the symbol table.
 *  The size, and is-empty operations take Î˜(1) time.
 *  The keys methods take
 *  O(logn + m) time, where m is
 *  the number of keys returned by the iterator.
 *  Construction takes Î˜(1) time.
 */

public class PointST<Key extends Comparable<Point2D>, Value> {
    private static final boolean RED   = true;
    private static final boolean BLACK = false;
    private Node root;     // root of the BST

    // BST helper node data type
    private class Node {
        private Point2D key;       // key
        private Value val;         // associated data
        private Node left, right;  // links to left and right subtrees
        private boolean color;     // color of parent link
        private int size;          // subtree count

        public Node(Point2D key, Value val, boolean color, int size) {
            this.key = key;
            this.val = val;
            this.color = color;
            this.size = size;
        }
    }

    // Initializes an empty symbol table.
    public PointST() {
    }

   /***************************************************************************
    *  Node helper methods.
    ***************************************************************************/
    // is node x red; false if x is null ?
    private boolean isRed(Node x) {
        if (x == null) 
            return false;
            
        return x.color == RED;
    }

    // number of node in subtree rooted at x; 0 if x is null
    private int size(Node x) {
        if (x == null) 
            return 0;
            
        return x.size;
    } 


     /**
     * Returns the number of key-value pairs in this symbol table.
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return size(root);
    }

   /**
     * Is this symbol table empty?
     * @return true if this symbol table is empty and false otherwise
     */
    public boolean isEmpty() {
        return root == null;
    }

   /***************************************************************************
    *  Standard BST search.
    ***************************************************************************/

    /**
     * Returns the value associated with the given key.
     * @param key the key
     * @return the value associated with the given key if the key is in the symbol table
     *     and null if the key is not in the symbol table
     * @throws IllegalArgumentException if key is null
     */
    public Value get(Point2D key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        
        return get(root, key);
    }

    // value associated with the given key in subtree rooted at x; null if no such key
    private Value get(Node x, Point2D key) {
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) 
                x = x.left;
            else if (cmp > 0) 
                x = x.right;
            else              
                return x.val;
        }
        return null;
    }

    /**
     * Does this symbol table contain the given key?
     * @param key the key
     * @return true if this symbol table contains key and
     *  false otherwise
     * @throws IllegalArgumentException if key is null
     */
    public boolean contains(Point2D key) {
        return get(key) != null;
    }

   /***************************************************************************
    *  Red-black tree insertion.
    ***************************************************************************/

    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old 
     * value with the new value if the symbol table already contains the specified key.
     * Deletes the specified key (and its associated value) from this symbol table
     * if the specified value is null.
     *
     * @param key the key
     * @param val the value
     * @throws IllegalArgumentException if key is {@code null}
     */
    public void put(Point2D key, Value val) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");
        
        if (val == null) {
            delete(key);
            return;
        }

        root = put(root, key, val);
        root.color = BLACK;
        assert check();
    }

    // insert the key-value pair in the subtree rooted at h
    private Node put(Node h, Point2D key, Value val) { 
        if (h == null) 
            return new Node(key, val, RED, 1);

        int cmp = key.compareTo(h.key);
        if (cmp < 0) 
            h.left  = put(h.left,  key, val); 
        else if (cmp > 0) 
            h.right = put(h.right, key, val); 
        else              
            h.val   = val;

        // fix-up any right-leaning links
        if (isRed(h.right) && !isRed(h.left))      
            h = rotateLeft(h);
        if (isRed(h.left)  &&  isRed(h.left.left)) 
            h = rotateRight(h);
        if (isRed(h.left)  &&  isRed(h.right))     
            flipColors(h);
        h.size = size(h.left) + size(h.right) + 1;

        return h;
    }

   /***************************************************************************
    *  Red-black tree deletion.
    ***************************************************************************/

    /**
     * Removes the smallest key and associated value from the symbol table.
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("BST underflow");

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = deleteMin(root);
        if (!isEmpty()) 
            root.color = BLACK;
        assert check();
    }

    // delete the key-value pair with the minimum key rooted at h
    private Node deleteMin(Node h) { 
        if (h.left == null)
            return null;

        if (!isRed(h.left) && !isRed(h.left.left))
            h = moveRedLeft(h);

        h.left = deleteMin(h.left);
        return balance(h);
    }


    /**
     * Removes the largest key and associated value from the symbol table.
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("BST underflow");

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = deleteMax(root);
        if (!isEmpty()) 
            root.color = BLACK;
        assert check();
    }

    // delete the key-value pair with the maximum key rooted at h
    private Node deleteMax(Node h) { 
        if (isRed(h.left))
            h = rotateRight(h);

        if (h.right == null)
            return null;

        if (!isRed(h.right) && !isRed(h.right.left))
            h = moveRedRight(h);

        h.right = deleteMax(h.right);

        return balance(h);
    }

    /**
     * Removes the specified key and its associated value from this symbol table     
     * (if the key is in this symbol table).    
     *
     * @param  key the key
     * @throws IllegalArgumentException if key is null
     */
    public void delete(Point2D key) { 
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        if (!contains(key)) 
            return;

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = delete(root, key);
        if (!isEmpty()) 
            root.color = BLACK;
        assert check();
    }

    // delete the key-value pair with the given key rooted at h
    private Node delete(Node h, Point2D key) { 
        assert get(h, key) != null;

        if (key.compareTo(h.key) < 0)  {
            if (!isRed(h.left) && !isRed(h.left.left))
                h = moveRedLeft(h);
            h.left = delete(h.left, key);
        }
        else {
            if (isRed(h.left))
                h = rotateRight(h);
            if (key.compareTo(h.key) == 0 && (h.right == null))
                return null;
            if (!isRed(h.right) && !isRed(h.right.left))
                h = moveRedRight(h);
            if (key.compareTo(h.key) == 0) {
                Node x = min(h.right);
                h.key = x.key;
                h.val = x.val;
                h.val = get(h.right, min(h.right).key);
                h.key = min(h.right).key;
                h.right = deleteMin(h.right);
            }
            else h.right = delete(h.right, key);
        }
        return balance(h);
    }

   /***************************************************************************
    *  Red-black tree helper functions.
    ***************************************************************************/

    // make a left-leaning link lean to the right
    private Node rotateRight(Node h) {
        assert (h != null) && isRed(h.left);
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = x.right.color;
        x.right.color = RED;
        x.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;
        return x;
    }

    // make a right-leaning link lean to the left
    private Node rotateLeft(Node h) {
        assert (h != null) && isRed(h.right);
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = x.left.color;
        x.left.color = RED;
        x.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;
        return x;
    }

    // flip the colors of a node and its two children
    private void flipColors(Node h) {
        // h must have opposite color of its two children
        assert (h != null) && (h.left != null) && (h.right != null);
        assert (!isRed(h) &&  isRed(h.left) &&  isRed(h.right))
           || (isRed(h)  && !isRed(h.left) && !isRed(h.right));
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }

    // Assuming that h is red and both h.left and h.left.left
    // are black, make h.left or one of its children red.
    private Node moveRedLeft(Node h) {
        assert (h != null);
        assert isRed(h) && !isRed(h.left) && !isRed(h.left.left);

        flipColors(h);
        if (isRed(h.right.left)) { 
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            flipColors(h);
        }
        return h;
    }

    // Assuming that h is red and both h.right and h.right.left
    // are black, make h.right or one of its children red.
    private Node moveRedRight(Node h) {
        assert (h != null);
        assert isRed(h) && !isRed(h.right) && !isRed(h.right.left);
        flipColors(h);
        if (isRed(h.left.left)) { 
            h = rotateRight(h);
            flipColors(h);
        }
        return h;
    }

    // restore red-black tree invariant
    private Node balance(Node h) {
        assert (h != null);

        if (isRed(h.right) && !isRed(h.left))    
            h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) 
            h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right))     
            flipColors(h);

        h.size = size(h.left) + size(h.right) + 1;
        return h;
    }

   /***************************************************************************
    *  Utility functions.
    ***************************************************************************/

    /**
     * Returns the height of the BST (for debugging).
     * @return the height of the BST (a 1-node tree has height 0)
     */
    public int height() {
        return height(root);
    }
    private int height(Node x) {
        if (x == null) 
            return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }

   /***************************************************************************
    *  Ordered symbol table methods.
    ***************************************************************************/

    /**
     * Returns the smallest key in the symbol table.
     * @return the smallest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public Point2D min() {
        if (isEmpty()) throw new NoSuchElementException("calls min() with empty symbol table");
        return min(root).key;
    } 

    // the smallest key in subtree rooted at x; null if no such key
    private Node min(Node x) { 
        assert x != null;
        if (x.left == null) 
            return x; 
        else                
            return min(x.left); 
    } 

    /**
     * Returns the largest key in the symbol table.
     * @return the largest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public Point2D max() {
        if (isEmpty()) throw new NoSuchElementException("calls max() with empty symbol table");
        return max(root).key;
    } 

    // the largest key in the subtree rooted at x; null if no such key
    private Node max(Node x) { 
        assert x != null;
        if (x.right == null) 
            return x; 
        else                 
            return max(x.right); 
    } 

    /**
     * Returns the largest key in the symbol table less than or equal to key.
     * @param key the key
     * @return the largest key in the symbol table less than or equal to key
     * @throws NoSuchElementException if there is no such key
     * @throws IllegalArgumentException if key is null
     */
    public Point2D floor(Point2D key) {
        if (key == null) throw new IllegalArgumentException("argument to floor() is null");
        if (isEmpty()) throw new NoSuchElementException("calls floor() with empty symbol table");
        
        Node x = floor(root, key);
        if (x == null) throw new NoSuchElementException("argument to floor() is too small");
        else           
            return x.key;
    }    

    // the largest key in the subtree rooted at x less than or equal to the given key
    private Node floor(Node x, Point2D key) {
        if (x == null) 
            return null;
            
        int cmp = key.compareTo(x.key);
        if (cmp == 0) 
            return x;
        if (cmp < 0)  
            return floor(x.left, key);
            
        Node t = floor(x.right, key);
        if (t != null) 
            return t; 
        else           
            return x;
    }

    /**
     * Returns the smallest key in the symbol table greater than or equal to key.
     * @param key the key
     * @return the smallest key in the symbol table greater than or equal to key
     * @throws NoSuchElementException if there is no such key
     * @throws IllegalArgumentException if key is null
     */
    public Point2D ceiling(Point2D key) {
        if (key == null) throw new IllegalArgumentException("argument to ceiling() is null");
        if (isEmpty()) throw new NoSuchElementException("calls ceiling() with empty symbol table");
        
        Node x = ceiling(root, key);
        if (x == null) throw new NoSuchElementException("argument to ceiling() is too small");
        else           
            return x.key;  
    }

    // the smallest key in the subtree rooted at x greater than or equal to the given key
    private Node ceiling(Node x, Point2D key) {  
        if (x == null) 
            return null;
            
        int cmp = key.compareTo(x.key);
        if (cmp == 0) 
            return x;
        if (cmp > 0)  
            return ceiling(x.right, key);
            
        Node t = ceiling(x.left, key);
        if (t != null) 
            return t; 
        else           
            return x;
    }

    /**
     * Return the key in the symbol table of a given rank.
     * This key has the property that there are rank keys in
     * the symbol table that are smaller. In other words, this key is the
     * (rank+1)st smallest key in the symbol table.
     *
     * @param  rank the order statistic
     * @return the key in the symbol table of given rank
     * @throws IllegalArgumentException unless rank is between 0 and
     *        nâ€“1
     */
    public Point2D select(int rank) {
        if (rank < 0 || rank >= size()) {
            throw new IllegalArgumentException("argument to select() is invalid: " + rank);
        }
        return select(root, rank);
    }

    // Return key in BST rooted at x of given rank.
    // Precondition: rank is in legal range.
    private Point2D select(Node x, int rank) {
        if (x == null) 
            return null;
            
        int leftSize = size(x.left);
        if (leftSize > rank) 
            return select(x.left,  rank);
        else if (leftSize < rank) 
            return select(x.right, rank - leftSize - 1); 
        else                      
            return x.key;
    }

    /**
     * Return the number of keys in the symbol table strictly less than key.
     * @param key the key
     * @return the number of keys in the symbol table strictly less than key
     * @throws IllegalArgumentException if key is null
     */
    public int rank(Point2D key) {
        if (key == null) throw new IllegalArgumentException("argument to rank() is null");
        return rank(key, root);
    } 

    // number of keys less than key in the subtree rooted at x
    private int rank(Point2D key, Node x) {
        if (x == null) 
            return 0; 
            
        int cmp = key.compareTo(x.key); 
        if (cmp < 0) 
            return rank(key, x.left); 
        else if (cmp > 0) 
            return 1 + size(x.left) + rank(key, x.right); 
        else              
            return size(x.left); 
    } 

   /***************************************************************************
    *  Range count and range search.
    ***************************************************************************/

    /**
     * Returns all keys in the symbol table as an Iterable.
     * To iterate over all of the keys in the symbol table named st,
     * use the foreach notation: for (Key key : st.keys()).
     * @return all keys in the symbol table as an Iterable
     */
    public Iterable<Point2D> keys() {
        if (isEmpty()) 
            return new LinkedBlockingQueue<Point2D>();
            
        return keys(min(), max());
    }

    /**
     * Returns all keys in the symbol table in the given range,
     * as an Iterable.
     *
     * @param  lo minimum endpoint
     * @param  hi maximum endpoint
     * @return all keys in the symbol table between lo
     *    (inclusive) and hi (inclusive) as an Iterable
     * @throws IllegalArgumentException if either lo or hi
     *    is null
     */
    public Iterable<Point2D> keys(Point2D lo, Point2D hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to keys() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to keys() is null");

        AbstractQueue<Point2D> queue = new LinkedBlockingQueue<Point2D>();
        if (isEmpty() || lo.compareTo(hi) > 0) 
            return queue;
        keys(root, queue, lo, hi);
        return queue;
    } 

    // add the keys between lo and hi in the subtree rooted at x
    // to the queue
    private void keys(Node x, AbstractQueue<Point2D> queue, Point2D lo, Point2D hi) { 
        if (x == null) 
            return; 
            
        int cmplo = lo.compareTo(x.key); 
        int cmphi = hi.compareTo(x.key); 
        if (cmplo < 0) 
            keys(x.left, queue, lo, hi); 
        if (cmplo <= 0 && cmphi >= 0) 
            queue.add(x.key); 
        if (cmphi > 0) 
            keys(x.right, queue, lo, hi); 
    } 

    /**
     * Returns the number of keys in the symbol table in the given range.
     *
     * @param  lo minimum endpoint
     * @param  hi maximum endpoint
     * @return the number of keys in the symbol table between lo
     *    (inclusive) and hi (inclusive)
     * @throws IllegalArgumentException if either lo or hi
     *    is null
     */
    public int size(Point2D lo, Point2D hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to size() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to size() is null");

        if (lo.compareTo(hi) > 0) 
            return 0;
        if (contains(hi)) 
            return rank(hi) - rank(lo) + 1;
        else              
            return rank(hi) - rank(lo);
    }

   /***************************************************************************
    *  Check integrity of red-black tree data structure.
    ***************************************************************************/
    private boolean check() {
        if (!isBST())            
            System.out.println("Not in symmetric order");
        if (!isSizeConsistent()) 
            System.out.println("Subtree counts not consistent");
        if (!isRankConsistent()) 
            System.out.println("Ranks not consistent");
        if (!is23())             
            System.out.println("Not a 2-3 tree");
        if (!isBalanced())       
            System.out.println("Not balanced");
        return isBST() && isSizeConsistent() && isRankConsistent() && is23() && isBalanced();
    }

    // does this binary tree satisfy symmetric order?
    // Note: this test also ensures that data structure is a binary tree since order is strict
    private boolean isBST() {
        return isBST(root, null, null);
    }

    // is the tree rooted at x a BST with all keys strictly between min and max
    // (if min or max is null, treat as empty constraint)
    // Credit: Bob Dondero's elegant solution
    private boolean isBST(Node x, Point2D min, Point2D max) {
        if (x == null) 
            return true;
        if (min != null && x.key.compareTo(min) <= 0) 
            return false;
        if (max != null && x.key.compareTo(max) >= 0) 
            return false;
        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
    } 

    // are the size fields correct?
    private boolean isSizeConsistent() { return isSizeConsistent(root); }
    private boolean isSizeConsistent(Node x) {
        if (x == null) 
            return true;
        if (x.size != size(x.left) + size(x.right) + 1) 
            return false;
        return isSizeConsistent(x.left) && isSizeConsistent(x.right);
    } 

    // check that ranks are consistent
    private boolean isRankConsistent() {
        for (int i = 0; i < size(); i++)
            if (i != rank(select(i))) 
                return false;
        for (Point2D key : keys())
            if (key.compareTo(select(rank(key))) != 0) 
                return false;
        return true;
    }

    // Does the tree have no red right links, and at most one (left)
    // red links in a row on any path?
    private boolean is23() { return is23(root); }
    private boolean is23(Node x) {
        if (x == null) 
            return true;
        if (isRed(x.right)) 
            return false;
        if (x != root && isRed(x) && isRed(x.left))
            return false;
        return is23(x.left) && is23(x.right);
    } 

    // do all paths from root to leaf have same number of black edges?
    private boolean isBalanced() { 
        int black = 0;     // number of black links on path from root to min
        Node x = root;
        
        while (x != null) {
            if (!isRed(x)) 
                black++;
            x = x.left;
        }
        return isBalanced(root, black);
    }

    // does every path from the root to a leaf have the given number of black links?
    private boolean isBalanced(Node x, int black) {
        if (x == null) 
            return black == 0;
        if (!isRed(x)) 
            black--;
        return isBalanced(x.left, black) && isBalanced(x.right, black);
    }

    /**
     * Returns all keys in the symbol table as an Iterable.
     * To iterate over all of the keys in the symbol table named st,
     * use the foreach notation: for (Key key : st.keys()).
     * @return all keys in the symbol table as an Iterable
     */
    public Iterable<Point2D> keys(RectHV rect) {
        if (isEmpty()) 
            return new LinkedBlockingQueue<Point2D>();
            
        return keys(min(), max(), rect);
    }

    /**
     * Returns all keys in the symbol table in the given range,
     * as an Iterable.
     *
     * @param  lo minimum endpoint
     * @param  hi maximum endpoint
     * @return all keys in the symbol table between lo
     *    (inclusive) and hi (inclusive) as an Iterable
     * @throws IllegalArgumentException if either lo or hi
     *    is null
     */
    public Iterable<Point2D> keys(Point2D lo, Point2D hi, RectHV rect) {
        if (lo == null) throw new IllegalArgumentException("first argument to keys() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to keys() is null");

        AbstractQueue<Point2D> queue = new LinkedBlockingQueue<Point2D>();
        if (isEmpty() || lo.compareTo(hi) > 0) 
            return queue;
        keys(root, queue, lo, hi, rect);
        return queue;
    } 

    // add the keys between lo and hi in the subtree rooted at x
    // to the queue
    private void keys(Node x, AbstractQueue<Point2D> queue, Point2D lo, Point2D hi, RectHV rect){ 
        if (x == null) 
            return; 
            
        int cmplo = lo.compareTo(x.key); 
        int cmphi = hi.compareTo(x.key); 
        if (cmplo < 0){
            keys(x.left, queue, lo, hi, rect); 
        }
        if (cmplo <= 0 && cmphi >= 0){
            if (rect.contains((Point2D) x.key)){
                queue.add(x.key); 
            }
        }
        if (cmphi > 0){
            keys(x.right, queue, lo, hi, rect); 
        }
    }

    // a nearest neighbor of point p; null if the symbol table is empty 
    /**
     * Returns the value associated with the given key.
     * @param key the key
     * @return the value associated with the given key if the key is in the symbol table
     *     and null if the key is not in the symbol table
     * @throws IllegalArgumentException if key is null
     */
    public Point2D nearest(Point2D target) {
        
        if (target == null) throw new IllegalArgumentException("argument to get() is null");
        return nearest(root, target, root.key);
    }

    // value associated with the given key in subtree rooted at x; null if no such key
    private Point2D nearest(Node x, Point2D target, Point2D champion) {
        Point2D min = champion;
        if ( x == null){
            return champion;
        }
        if (target.distanceSquaredTo(x.key) < target.distanceSquaredTo(min))
            min = x.key;
        int cmp = target.compareTo(x.key);
        if (cmp < 0) 
            min = nearest(x.left, target, min);
        else if (cmp == 0) 
                min = nearest(x, target, min);
        else    min = nearest(x.right, target, min);
       
        return min;
    }

	private Point2D bruteForce(Node root, Point2D target, Point2D min ) {
        Point2D champ = min;
		if (root != null) {
            if ( root.key.distanceSquaredTo(target) < champ.distanceSquaredTo(target)){
                champ = root.key;
        }
                champ = bruteForce(root.left, target, champ);
			//System.out.print(root.key.toString() + " ");
            champ =	bruteForce(root.right, target, champ);
		}
        return champ;
	}

    public Point2D bruteForceHelper(Point2D target) {
		return bruteForce(this.root, target, root.key);
	}

    public static void main(String[] args) throws FileNotFoundException {
        PointST<Point2D,Integer> bruteForce = new PointST<Point2D,Integer>();
        File myFile = new File("input1M.txt");
        Scanner input = new Scanner(myFile);
        for (int i = 0; input.hasNextLine(); i++) {
            double x = input.nextDouble();
            double y = input.nextDouble();
            Point2D p = new Point2D(x, y);
            bruteForce.put(p, i);
        }
        input.close();  
        System.out.println(" \n");
        long start = System.currentTimeMillis();
        int countKD = 0;
        while (true){
            Point2D queryRandom = new Point2D(RefinedRandom.uniform(0.0, 1.0), RefinedRandom.uniform(0.0, 1.0));
            Point2D kdtreeNearest = bruteForce.bruteForceHelper(queryRandom);
            long end = System.currentTimeMillis();
            long timeElapsed = end - start;
            if( timeElapsed >= 1000) {
                end = System.currentTimeMillis();
                timeElapsed = (end - start);
                System.out.println(" Brute Force: ");
                System.out.println(" Time : " + timeElapsed + " milliseconds");
                System.out.println(" calls : " + countKD);
                System.out.println(" \n");
                break;
            }
            countKD++;
        }
    }
}

/* *****************************************************************************
 **************************************************************************** */
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

/* *****************************************************************************
 **************************************************************************** */
public class Main {
    // Unit tests the point data type.
   public static void main(String[] args) {
       int x0 = 0;
       int y0 = 0;
       int n = 50;

       Draw.setCanvasSize(800, 800);
       Draw.setXscale(0, 100);
       Draw.setYscale(0, 100);
       Draw.setPenRadius(0.005);
       Draw.enableDoubleBuffering();

       Point2D[] points = new Point2D[n];
       for (int i = 0; i < n; i++) {
           int x = RefinedRandom.uniform(100);
           int y = RefinedRandom.uniform(100);
           points[i] = new Point2D(x, y);
           points[i].draw();
       }

       // draw p = (x0, x1) in red
       Point2D p = new Point2D(x0, y0);
       Draw.setPenColor(Draw.RED);
       Draw.setPenRadius(0.02);
       p.draw();


       // draw line segments from p to each point, one at a time, in polar order
       Draw.setPenRadius();
       Draw.setPenColor(Draw.BLUE);
       for (int i = 0; i < n; i++) {
           p.drawTo(points[i]);
           Draw.show();
           Draw.pause(100);
       }
   }

}

/* *****************************************************************************
 **************************************************************************** */
/******************************************************************************
 *  Immutable point data type for points in the plane.
 ******************************************************************************/

import java.util.Comparator;


/**
 *  The Point class is an immutable data type to encapsulate a
 *  two-dimensional point with real-value coordinates.
 *  <p>
 *  Note: in order to deal with the difference behavior of double and 
 *  Double with respect to -0.0 and +0.0, the Point2D constructor converts
 *  any coordinates that are -0.0 to +0.0.
 */
public final class Point2D implements Comparable<Point2D> {

    // Compares two points by x-coordinate.
    public static final Comparator<Point2D> X_ORDER = new XOrder();

    // Compares two points by y-coordinate.
    public static final Comparator<Point2D> Y_ORDER = new YOrder();

    // Compares two points by polar radius.
    public static final Comparator<Point2D> R_ORDER = new ROrder();

    private final double x;    // x coordinate
    private final double y;    // y coordinate

    /**
     * Initializes a new point (x, y).
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @throws IllegalArgumentException if either x or y
     *    is Double.NaN, Double.POSITIVE_INFINITY or
     *    Double.NEGATIVE_INFINITY
     */
    public Point2D(double x, double y) {
        if (Double.isInfinite(x) || Double.isInfinite(y))
            throw new IllegalArgumentException("Coordinates must be finite");
        if (Double.isNaN(x) || Double.isNaN(y))
            throw new IllegalArgumentException("Coordinates cannot be NaN");
        if (x == 0.0) 
            this.x = 0.0;  // convert -0.0 to +0.0
        else          
            this.x = x;

        if (y == 0.0) 
            this.y = 0.0;  // convert -0.0 to +0.0
        else          
            this.y = y;
    }

    /**
     * Returns the x-coordinate.
     * @return the x-coordinate
     */
    public double x() {
        return x;
    }

    /**
     * Returns the y-coordinate.
     * @return the y-coordinate
     */
    public double y() {
        return y;
    }

    /**
     * Returns the polar radius of this point.
     * @return the polar radius of this point in polar coordiantes: sqrt(x*x + y*y)
     */
    public double r() {
        return Math.sqrt(x*x + y*y);
    }

    /**
     * Returns the angle of this point in polar coordinates.
     * @return the angle (in radians) of this point in polar coordiantes (between â€“&pi; and &pi;)
     */
    public double theta() {
        return Math.atan2(y, x);
    }

    /**
     * Returns the angle between this point and that point.
     * @return the angle in radians (between â€“&pi; and &pi;) between this point and that point (0 if equal)
     */
    private double angleTo(Point2D that) {
        double dx = that.x - this.x;
        double dy = that.y - this.y;
        return Math.atan2(dy, dx);
    }

    /**
     * Returns true if aâ†’bâ†’c is a counterclockwise turn.
     * @param a first point
     * @param b second point
     * @param c third point
     * @return { -1, 0, +1 } if aâ†’bâ†’c is a { clockwise, collinear; counterclocwise } turn.
     */
    public static int ccw(Point2D a, Point2D b, Point2D c) {
        double area2 = (b.x-a.x)*(c.y-a.y) - (b.y-a.y)*(c.x-a.x);
        if (area2 < 0) 
            return -1;
        else if (area2 > 0) 
            return +1;
        else                
            return  0;
    }

    /**
     * Returns twice the signed area of the triangle a-b-c.
     * @param a first point
     * @param b second point
     * @param c third point
     * @return twice the signed area of the triangle a-b-c
     */
    public static double area2(Point2D a, Point2D b, Point2D c) {
        return (b.x-a.x)*(c.y-a.y) - (b.y-a.y)*(c.x-a.x);
    }

    /**
     * Returns the Euclidean distance between this point and that point.
     * @param that the other point
     * @return the Euclidean distance between this point and that point
     */
    public double distanceTo(Point2D that) {
        double dx = this.x - that.x;
        double dy = this.y - that.y;
        return Math.sqrt(dx*dx + dy*dy);
    }

    /**
     * Returns the square of the Euclidean distance between this point and that point.
     * @param that the other point
     * @return the square of the Euclidean distance between this point and that point
     */
    public double distanceSquaredTo(Point2D that) {
        double dx = this.x - that.x;
        double dy = this.y - that.y;
        return dx*dx + dy*dy;
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point (x1, y1)
     * if and only if either y0 < y1 or if y0 == y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value 0 if this string is equal to the argument
     *         string (precisely when equals() returns true);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point2D that) {
        if (this.y < that.y) 
            return -1;
        if (this.y > that.y) 
            return +1;
        if (this.x < that.x) 
            return -1;
        if (this.x > that.x) 
            return +1;
        return 0;
    }

    /**
     * Compares two points by polar angle (between 0 and 2&pi;) with respect to this point.
     *
     * @return the comparator
     */
    public Comparator<Point2D> polarOrder() {
        return new PolarOrder();
    }

    /**
     * Compares two points by atan2() angle (between â€“&pi; and &pi;) with respect to this point.
     *
     * @return the comparator
     */
    public Comparator<Point2D> atan2Order() {
        return new Atan2Order();
    }

    /**
     * Compares two points by distance to this point.
     *
     * @return the comparator
     */
    public Comparator<Point2D> distanceToOrder() {
        return new DistanceToOrder();
    }

    // compare points according to their x-coordinate
    private static class XOrder implements Comparator<Point2D> {
        public int compare(Point2D p, Point2D q) {
            if (p.x < q.x) 
                return -1;
            if (p.x > q.x) 
                return +1;
            return 0;
        }
    }

    // compare points according to their y-coordinate
    private static class YOrder implements Comparator<Point2D> {
        public int compare(Point2D p, Point2D q) {
            if (p.y < q.y) 
                return -1;
            if (p.y > q.y) 
                return +1;
            return 0;
        }
    }

    // compare points according to their polar radius
    private static class ROrder implements Comparator<Point2D> {
        public int compare(Point2D p, Point2D q) {
            double delta = (p.x*p.x + p.y*p.y) - (q.x*q.x + q.y*q.y);
            
            if (delta < 0) 
                return -1;
            if (delta > 0) 
                return +1;
            return 0;
        }
    }
 
    // compare other points relative to atan2 angle (bewteen -pi/2 and pi/2) they make with this Point
    private class Atan2Order implements Comparator<Point2D> {
        public int compare(Point2D q1, Point2D q2) {
            double angle1 = angleTo(q1);
            double angle2 = angleTo(q2);
            
            if (angle1 < angle2) 
                return -1;
            else if (angle1 > angle2) 
                return +1;
            else                      
                return  0;
        }
    }

    // compare other points relative to polar angle (between 0 and 2pi) they make with this Point
    private class PolarOrder implements Comparator<Point2D> {
        public int compare(Point2D q1, Point2D q2) {
            double dx1 = q1.x - x;
            double dy1 = q1.y - y;
            double dx2 = q2.x - x;
            double dy2 = q2.y - y;

            if (dy1 >= 0 && dy2 < 0) 
                return -1;    // q1 above; q2 below
            else if (dy2 >= 0 && dy1 < 0) 
                return +1;    // q1 below; q2 above
            else if (dy1 == 0 && dy2 == 0) {            // 3-collinear and horizontal
                if (dx1 >= 0 && dx2 < 0) 
                    return -1;
                else if (dx2 >= 0 && dx1 < 0) 
                    return +1;
                else                          
                    return  0;
            }
            else return -ccw(Point2D.this, q1, q2);     // both above or below

            // Note: ccw() recomputes dx1, dy1, dx2, and dy2
        }
    }

    // compare points according to their distance to this point
    private class DistanceToOrder implements Comparator<Point2D> {
        public int compare(Point2D p, Point2D q) {
            double dist1 = distanceSquaredTo(p);
            double dist2 = distanceSquaredTo(q);
            
            if (dist1 < dist2) 
                return -1;
            else if (dist1 > dist2) 
                return +1;
            else                    
                return  0;
        }
    }


    /**       
     * Compares this point to the specified point.
     *       
     * @param  other the other point
     * @return true if this point equals other;
     *         false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) 
            return true;
        if (other == null) 
            return false;
        if (other.getClass() != this.getClass()) 
            return false;
            
        Point2D that = (Point2D) other;
        return this.x == that.x && this.y == that.y;
    }

    /**
     * Return a string representation of this point.
     * @return a string representation of this point in the format (x, y)
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Returns an integer hash code for this point.
     * @return an integer hash code for this point
     */
    @Override
    public int hashCode() {
        int hashX = ((Double) x).hashCode();
        int hashY = ((Double) y).hashCode();
        return 31*hashX + hashY;
    }

    /**
     * Plot this point using standard draw.
     */
    public void draw() {
        Draw.point(x, y);
    }

    /**
     * Plot a line from this point to that point using standard draw.
     * @param that the other point
     */
    public void drawTo(Point2D that) {
        Draw.line(this.x, this.y, that.x, that.y);
    }
}

/* *****************************************************************************
 **************************************************************************** */
/******************************************************************************
 *  Immutable data type for 2D axis-aligned rectangle.
 ******************************************************************************/

/**
 *  The RectHV class is an immutable data type to encapsulate a
 *  two-dimensional axis-aligned rectangle with real-value coordinates.
 *  The rectangle is closedâ€”it includes the points on the boundary.
 */

public final class RectHV {
    private final double xmin, ymin;   // minimum x- and y-coordinates
    private final double xmax, ymax;   // maximum x- and y-coordinates

    /**
     * Initializes a new rectangle [xmin, xmax]
     * x [ymin, ymax].
     *
     * @param  xmin the x-coordinate of the lower-left endpoint
     * @param  ymin the y-coordinate of the lower-left endpoint
     * @param  xmax the x-coordinate of the upper-right endpoint
     * @param  ymax the y-coordinate of the upper-right endpoint
     * @throws IllegalArgumentException if any of xmin,
     *         ymin, xmax, or ymax
     *         is Double.NaN.
     * @throws IllegalArgumentException if xmax < xmin or ymax < ymin.
     */
    public RectHV(double xmin, double ymin, double xmax, double ymax) {
        this.xmin = xmin;
        this.ymin = ymin;
        this.xmax = xmax;
        this.ymax = ymax;
        if (Double.isNaN(xmin) || Double.isNaN(xmax)) {
            throw new IllegalArgumentException("x-coordinate is NaN: " + toString());
        }
        if (Double.isNaN(ymin) || Double.isNaN(ymax)) {
            throw new IllegalArgumentException("y-coordinate is NaN: " + toString());
        }
        if (xmax < xmin) {
            throw new IllegalArgumentException("xmax < xmin: " + toString());
        }
        if (ymax < ymin) {
            throw new IllegalArgumentException("ymax < ymin: " + toString());
        }
    }

    /**
     * Returns the minimum x-coordinate of any point in this rectangle.
     *
     * @return the minimum x-coordinate of any point in this rectangle
     */
    public double xmin() {
        return xmin;
    }

    /**
     * Returns the maximum x-coordinate of any point in this rectangle.
     *
     * @return the maximum x-coordinate of any point in this rectangle
     */
    public double xmax() {
        return xmax;
    }

    /**
     * Returns the minimum y-coordinate of any point in this rectangle.
     *
     * @return the minimum y-coordinate of any point in this rectangle
     */
    public double ymin() {
        return ymin;
    }

    /**
     * Returns the maximum y-coordinate of any point in this rectangle.
     *
     * @return the maximum y-coordinate of any point in this rectangle
     */
    public double ymax() {
        return ymax;
    }

    /**
     * Returns the width of this rectangle.
     *
     * @return the width of this rectangle xmax - xmin
     */
    public double width() {
        return xmax - xmin;
    }

    /**
     * Returns the height of this rectangle.
     *
     * @return the height of this rectangle ymax - ymin
     */
    public double height() {
        return ymax - ymin;
    }

    /**
     * Returns true if the two rectangles intersect. This includes
     * improper intersections (at points on the boundary
     * of each rectangle) and nested intersctions
     * (when one rectangle is contained inside the other)
     *
     * @param  that the other rectangle
     * @return true if this rectangle intersect the argument
               rectangle at one or more points
     */
    public boolean intersects(RectHV that) {
        return this.xmax >= that.xmin && this.ymax >= that.ymin
            && that.xmax >= this.xmin && that.ymax >= this.ymin;
    }

    /**
     * Returns true if this rectangle contain the point.
     * @param  key the point
     * @return true if this rectangle contain the point p,
               possibly at the boundary; false otherwise
     */
    public boolean contains(Point2D key) {
        return (key.x() >= xmin) && (key.x() <= xmax)
            && (key.y() >= ymin) && (key.y() <= ymax);
    }

    /**
     * Returns the Euclidean distance between this rectangle and the point p.
     *
     * @param  p the point
     * @return the Euclidean distance between the point p and the closest point
               on this rectangle; 0 if the point is contained in this rectangle
     */
    public double distanceTo(Point2D p) {
        return Math.sqrt(this.distanceSquaredTo(p));
    }

    /**
     * Returns the square of the Euclidean distance between this rectangle and the point p.
     *
     * @param  p the point
     * @return the square of the Euclidean distance between the point p and
     *         the closest point on this rectangle; 0 if the point is contained
     *         in this rectangle
     */
    public double distanceSquaredTo(Point2D p) {
        double dx = 0.0, dy = 0.0;
        if      (p.x() < xmin) dx = p.x() - xmin;
        else if (p.x() > xmax) dx = p.x() - xmax;
        if      (p.y() < ymin) dy = p.y() - ymin;
        else if (p.y() > ymax) dy = p.y() - ymax;
        return dx*dx + dy*dy;
    }

    /**
     * Compares this rectangle to the specified rectangle.
     *
     * @param  other the other rectangle
     * @return true if this rectangle equals other;
     *         false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        RectHV that = (RectHV) other;
        if (this.xmin != that.xmin) return false;
        if (this.ymin != that.ymin) return false;
        if (this.xmax != that.xmax) return false;
        if (this.ymax != that.ymax) return false;
        return true;
    }

    /**
     * Returns an integer hash code for this rectangle.
     * @return an integer hash code for this rectangle
     */
    @Override
    public int hashCode() {
        int hash1 = ((Double) xmin).hashCode();
        int hash2 = ((Double) ymin).hashCode();
        int hash3 = ((Double) xmax).hashCode();
        int hash4 = ((Double) ymax).hashCode();
        return 31*(31*(31*hash1 + hash2) + hash3) + hash4;
    }

    /**
     * Returns a string representation of this rectangle.
     *
     * @return a string representation of this rectangle, using the format
     *         [xmin, xmax] x [ymin, ymax]
     */
    @Override
    public String toString() {
        return "[" + xmin + ", " + xmax + "] x [" + ymin + ", " + ymax + "]";
    }

    /**
     * Draws this rectangle.
     */
    public void draw() {
        Draw.line(xmin, ymin, xmax, ymin);
        Draw.line(xmax, ymin, xmax, ymax);
        Draw.line(xmax, ymax, xmin, ymax);
        Draw.line(xmin, ymax, xmin, ymin);
    }


}

/* *****************************************************************************
 **************************************************************************** */
import java.util.Random;

/**
 *  The RefinedRandom class provides static methods for generating
 *  random number from various discrete and continuous distributions, 
 *  including uniform, Bernoulli, geometric, Gaussian, exponential, Pareto,
 *  Poisson, and Cauchy. It also provides method for shuffling an
 *  array or subarray and generating random permutations.
 *  <p>
 *  By convention, all intervals are half open. For example,
 *  uniform(-1.0, 1.0) returns a random number between
 *  -1.0 (inclusive) and 1.0 (exclusive).
 *  Similarly, shuffle(a, lo, hi) shuffles the hi - lo
 *  elements in the array a[], starting at index lo
 *  (inclusive) and ending at index hi (exclusive).
 */
public final class RefinedRandom {

    private static Random random;    // pseudo-random number generator
    private static long seed;        // pseudo-random number generator seed

    // static initializer
    static {
        // this is how the seed was set in Java 1.4
        seed = System.currentTimeMillis();
        random = new Random(seed);
    }

    // don't instantiate
    RefinedRandom() { }

    /**
     * Sets the seed of the pseudo-random number generator.
     * This method enables you to produce the same sequence of "random"
     * number for each execution of the program.
     * Ordinarily, you should call this method at most once per program.
     *
     * @param s the seed
     */
    public static void setSeed(long s) {
        seed   = s;
        random = new Random(seed);
    }

    /**
     * Returns the seed of the pseudo-random number generator.
     *
     * @return the seed
     */
    public static long getSeed() {
        return seed;
    }

    /**
     * Returns a random real number uniformly in [0, 1).
     *
     * @return a random real number uniformly in [0, 1)
     */
    public static double uniform() {
        return random.nextDouble();
    }

    /**
     * Returns a random integer uniformly in [0, n).
     * 
     * @param n number of possible integers
     * @return a random integer uniformly between 0 (inclusive) and n (exclusive)
     * @throws IllegalArgumentException if n <= 0
     */
    public static int uniform(int n) {
        if (n <= 0) throw new IllegalArgumentException("argument must be positive: " + n);
        return random.nextInt(n);
    }


    /**
     * Returns a random long integer uniformly in [0, n).
     * 
     * @param n number of possible long integers
     * @return a random long integer uniformly between 0 (inclusive) and n (exclusive)
     * @throws IllegalArgumentException if n <= 0
     */
    public static long uniform(long n) {
        if (n <= 0L) throw new IllegalArgumentException("argument must be positive: " + n);

        // https://docs.oracle.com/javase/8/docs/api/java/util/Random.html#longs-long-long-long-
        long r = random.nextLong();
        long m = n - 1;

        // power of two
        if ((n & m) == 0L) {
            return r & m;
        }

        // reject over-represented candidates
        long u = r >>> 1;
        while (u + m - (r = u % n) < 0L) {
            u = random.nextLong() >>> 1;
        }
        return r;
    }

    ///////////////////////////////////////////////////////////////////////////
    //  STATIC METHODS BELOW RELY ON JAVA.UTIL.RANDOM ONLY INDIRECTLY VIA
    //  THE STATIC METHODS ABOVE.
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Returns a random real number uniformly in [0, 1).
     * 
     * @return     a random real number uniformly in [0, 1)
     * @deprecated Replaced by uniform().
     */
    @Deprecated
    public static double random() {
        return uniform();
    }

    /**
     * Returns a random integer uniformly in [a, b).
     * 
     * @param  a the left endpoint
     * @param  b the right endpoint
     * @return a random integer uniformly in [a, b)
     * @throws IllegalArgumentException if b <= a
     * @throws IllegalArgumentException if b - a >= Integer.MAX_VALUE
     */
    public static int uniform(int a, int b) {
        if ((b <= a) || ((long) b - a >= Integer.MAX_VALUE)) {
            throw new IllegalArgumentException("invalid range: [" + a + ", " + b + ")");
        }
        return a + uniform(b - a);
    }

    /**
     * Returns a random real number uniformly in [a, b).
     * 
     * @param  a the left endpoint
     * @param  b the right endpoint
     * @return a random real number uniformly in [a, b)
     * @throws IllegalArgumentException unless a < b
     */
    public static double uniform(double a, double b) {
        if (!(a < b)) {
            throw new IllegalArgumentException("invalid range: [" + a + ", " + b + ")");
        }
        return a + uniform() * (b-a);
    }

    /**
     * Returns a random boolean from a Bernoulli distribution with success
     * probability p.
     *
     * @param  p the probability of returning true
     * @return true with probability p and
     *         false with probability 1 - p
     */
    public static boolean bernoulli(double p) {
        if (!(p >= 0.0 && p <= 1.0))
            throw new IllegalArgumentException("probability p must be between 0.0 and 1.0: " + p);
        return uniform() < p;
    }

    /**
     * Returns a random boolean from a Bernoulli distribution with success
     * probability 1/2.
     * 
     * @return true with probability 1/2 and
     *         false with probability 1/2
     */
    public static boolean bernoulli() {
        return bernoulli(0.5);
    }

    /**
     * Returns a random real number from a standard Gaussian distribution.
     * 
     * @return a random real number from a standard Gaussian distribution
     *         (mean 0 and standard deviation 1).
     */
    public static double gaussian() {
        // use the polar form of the Box-Muller transform
        double r, x, y;
        do {
            x = uniform(-1.0, 1.0);
            y = uniform(-1.0, 1.0);
            r = x*x + y*y;
        } while (r >= 1 || r == 0);
        return x * Math.sqrt(-2 * Math.log(r) / r);

        // Remark:  y * Math.sqrt(-2 * Math.log(r) / r)
        // is an independent random gaussian
    }

    /**
     * Returns a random real number from a Gaussian distribution with mean mu;
     * and standard deviation sigma;.
     * 
     * @param  mu the mean
     * @param  sigma the standard deviation
     * @return a real number distributed according to the Gaussian distribution
     *         with mean mu and standard deviation sigma
     */
    public static double gaussian(double mu, double sigma) {
        return mu + sigma * gaussian();
    }

    /**
     * Returns a random integer from a geometric distribution with success
     * probability p.
     * The integer represents the number of independent trials
     * before the first success.
     * 
     * @param  p the parameter of the geometric distribution
     * @return a random integer from a geometric distribution with success
     *         probability p; or Integer.MAX_VALUE if
     *         p is (nearly) equal to 1.0.
     * @throws IllegalArgumentException unless p >= 0.0 and p <= 1.0
     */
    public static int geometric(double p) {
        if (!(p >= 0)) {
            throw new IllegalArgumentException("probability p must be greater than 0: " + p);
        }
        if (!(p <= 1.0)) {
            throw new IllegalArgumentException("probability p must not be larger than 1: " + p);
        }
        // using algorithm given by Knuth
        return (int) Math.ceil(Math.log(uniform()) / Math.log(1.0 - p));
    }

    /**
     * Returns a random integer from a Poisson distribution with mean lambda;.
     *
     * @param  lambda the mean of the Poisson distribution
     * @return a random integer from a Poisson distribution with mean lambda
     * @throws IllegalArgumentException unless lambda > 0.0 and not infinite
     */
    public static int poisson(double lambda) {
        if (!(lambda > 0.0))
            throw new IllegalArgumentException("lambda must be positive: " + lambda);
        if (Double.isInfinite(lambda))
            throw new IllegalArgumentException("lambda must not be infinite: " + lambda);
        // using algorithm given by Knuth
        int k = 0;
        double p = 1.0;
        double expLambda = Math.exp(-lambda);
        do {
            k++;
            p *= uniform();
        } while (p >= expLambda);
        return k-1;
    }

    /**
     * Returns a random real number from the standard Pareto distribution.
     *
     * @return a random real number from the standard Pareto distribution
     */
    public static double pareto() {
        return pareto(1.0);
    }

    /**
     * Returns a random real number from a Pareto distribution with
     * shape parameter alpha;.
     *
     * @param  alpha shape parameter
     * @return a random real number from a Pareto distribution with shape
     *         parameter alpha
     * @throws IllegalArgumentException unless alpha > 0.0
     */
    public static double pareto(double alpha) {
        if (!(alpha > 0.0))
            throw new IllegalArgumentException("alpha must be positive: " + alpha);
        return Math.pow(1 - uniform(), -1.0/alpha) - 1.0;
    }

    /**
     * Returns a random real number from the Cauchy distribution.
     *
     * @return a random real number from the Cauchy distribution.
     */
    public static double cauchy() {
        return Math.tan(Math.PI * (uniform() - 0.5));
    }

    /**
     * Returns a random integer from the specified discrete distribution.
     *
     * @param  probabilities the probability of occurrence of each integer
     * @return a random integer from a discrete distribution:
     *         i with probability probabilities[i]
     * @throws IllegalArgumentException if probabilities is null
     * @throws IllegalArgumentException if sum of array entries is not (very nearly) equal to 1.0
     * @throws IllegalArgumentException unless probabilities[i] >= 0.0 for each index i
     */
    public static int discrete(double[] probabilities) {
        if (probabilities == null) throw new IllegalArgumentException("argument array must not be null");
        double EPSILON = 1.0E-14;
        double sum = 0.0;
        for (int i = 0; i < probabilities.length; i++) {
            if (!(probabilities[i] >= 0.0))
                throw new IllegalArgumentException("array entry " + i + " must be non-negative: " + probabilities[i]);
            sum += probabilities[i];
        }
        if (sum > 1.0 + EPSILON || sum < 1.0 - EPSILON)
            throw new IllegalArgumentException("sum of array entries does not approximately equal 1.0: " + sum);

        // the for loop may not return a value when both r is (nearly) 1.0 and when the
        // cumulative sum is less than 1.0 (as a result of floating-point roundoff error)
        while (true) {
            double r = uniform();
            sum = 0.0;
            for (int i = 0; i < probabilities.length; i++) {
                sum = sum + probabilities[i];
                if (sum > r) return i;
            }
        }
    }

    /**
     * Returns a random integer from the specified discrete distribution.
     *
     * @param  frequencies the frequency of occurrence of each integer
     * @return a random integer from a discrete distribution:
     *         i with probability proportional to frequencies[i]
     * @throws IllegalArgumentException if frequencies is null
     * @throws IllegalArgumentException if all array entries are 0
     * @throws IllegalArgumentException if frequencies[i] is negative for any index i
     * @throws IllegalArgumentException if sum of frequencies exceeds Integer.MAX_VALUE (2<superscript>31 - 1)
     */
    public static int discrete(int[] frequencies) {
        if (frequencies == null) throw new IllegalArgumentException("argument array must not be null");
        long sum = 0;
        for (int i = 0; i < frequencies.length; i++) {
            if (frequencies[i] < 0)
                throw new IllegalArgumentException("array entry " + i + " must be non-negative: " + frequencies[i]);
            sum += frequencies[i];
        }
        if (sum == 0)
            throw new IllegalArgumentException("at least one array entry must be positive");
        if (sum >= Integer.MAX_VALUE)
            throw new IllegalArgumentException("sum of frequencies overflows an int");

        // pick index i with probabilitity proportional to frequency
        double r = uniform((int) sum);
        sum = 0;
        for (int i = 0; i < frequencies.length; i++) {
            sum += frequencies[i];
            if (sum > r) return i;
        }

        // can't reach here
        assert false;
        return -1;
    }

    /**
     * Returns a random real number from an exponential distribution
     * with rate lambda;.
     * 
     * @param  lambda the rate of the exponential distribution
     * @return a random real number from an exponential distribution with
     *         rate lambda
     * @throws IllegalArgumentException unless lambda > 0.0
     */
    public static double exp(double lambda) {
        if (!(lambda > 0.0))
            throw new IllegalArgumentException("lambda must be positive: " + lambda);
        return -Math.log(1 - uniform()) / lambda;
    }

    /**
     * Rearranges the elements of the specified array in uniformly random order.
     *
     * @param  a the array to shuffle
     * @throws IllegalArgumentException if a is null
     */
    public static void shuffle(Object[] a) {
        validateNotNull(a);
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int r = i + uniform(n-i);     // between i and n-1
            Object temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Rearranges the elements of the specified array in uniformly random order.
     *
     * @param  a the array to shuffle
     * @throws IllegalArgumentException if a is null
     */
    public static void shuffle(double[] a) {
        validateNotNull(a);
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int r = i + uniform(n-i);     // between i and n-1
            double temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Rearranges the elements of the specified array in uniformly random order.
     *
     * @param  a the array to shuffle
     * @throws IllegalArgumentException if a is null
     */
    public static void shuffle(int[] a) {
        validateNotNull(a);
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int r = i + uniform(n-i);     // between i and n-1
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Rearranges the elements of the specified array in uniformly random order.
     *
     * @param  a the array to shuffle
     * @throws IllegalArgumentException if a is null
     */
    public static void shuffle(char[] a) {
        validateNotNull(a);
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int r = i + uniform(n-i);     // between i and n-1
            char temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Rearranges the elements of the specified subarray in uniformly random order.
     *
     * @param  a the array to shuffle
     * @param  lo the left endpoint (inclusive)
     * @param  hi the right endpoint (exclusive)
     * @throws IllegalArgumentException if a is null
     * @throws IllegalArgumentException unless (0 <= lo) && (lo < hi) && (hi <= a.length)
     * 
     */
    public static void shuffle(Object[] a, int lo, int hi) {
        validateNotNull(a);
        validateSubarrayIndices(lo, hi, a.length);

        for (int i = lo; i < hi; i++) {
            int r = i + uniform(hi-i);     // between i and hi-1
            Object temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Rearranges the elements of the specified subarray in uniformly random order.
     *
     * @param  a the array to shuffle
     * @param  lo the left endpoint (inclusive)
     * @param  hi the right endpoint (exclusive)
     * @throws IllegalArgumentException if a is null
     * @throws IllegalArgumentException unless (0 <= lo) && (lo < hi) && (hi <= a.length)
     */
    public static void shuffle(double[] a, int lo, int hi) {
        validateNotNull(a);
        validateSubarrayIndices(lo, hi, a.length);

        for (int i = lo; i < hi; i++) {
            int r = i + uniform(hi-i);     // between i and hi-1
            double temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Rearranges the elements of the specified subarray in uniformly random order.
     *
     * @param  a the array to shuffle
     * @param  lo the left endpoint (inclusive)
     * @param  hi the right endpoint (exclusive)
     * @throws IllegalArgumentException if a is null
     * @throws IllegalArgumentException unless (0 <= lo) && (lo < hi) && (hi <= a.length)
     */
    public static void shuffle(int[] a, int lo, int hi) {
        validateNotNull(a);
        validateSubarrayIndices(lo, hi, a.length);

        for (int i = lo; i < hi; i++) {
            int r = i + uniform(hi-i);     // between i and hi-1
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Returns a uniformly random permutation of n elements.
     *
     * @param  n number of elements
     * @throws IllegalArgumentException if n is negative
     * @return an array of length n that is a uniformly random permutation
     *         of 0, 1, ..., n-1
     */
    public static int[] permutation(int n) {
        if (n < 0) throw new IllegalArgumentException("n must be non-negative: " + n);
        int[] perm = new int[n];
        for (int i = 0; i < n; i++)
            perm[i] = i;
        shuffle(perm);
        return perm;
    }

    /**
     * Returns a uniformly random permutation of k of n elements.
     *
     * @param  n number of elements
     * @param  k number of elements to select
     * @throws IllegalArgumentException if n is negative
     * @throws IllegalArgumentException unless 0 <= k <= n
     * @return an array of length k that is a uniformly random permutation
     *         of k of the elements from 0, 1, ..., n-1
     */
    public static int[] permutation(int n, int k) {
        if (n < 0) throw new IllegalArgumentException("n must be non-negative: " + n);
        if (k < 0 || k > n) throw new IllegalArgumentException("k must be between 0 and n: " + k);
        int[] perm = new int[k];
        for (int i = 0; i < k; i++) {
            int r = uniform(i+1);    // between 0 and i
            perm[i] = perm[r];
            perm[r] = i;
        }
        for (int i = k; i < n; i++) {
            int r = uniform(i+1);    // between 0 and i
            if (r < k) perm[r] = i;
        }
        return perm;
    }

    // throw an IllegalArgumentException if x is null
    // (x can be of type Object[], double[], int[], ...)
    private static void validateNotNull(Object x) {
        if (x == null) {
            throw new IllegalArgumentException("argument must not be null");
        }
    }

    // throw an exception unless 0 <= lo <= hi <= length
    private static void validateSubarrayIndices(int lo, int hi, int length) {
        if (lo < 0 || hi > length || lo > hi) {
            throw new IllegalArgumentException("subarray indices out of bounds: [" + lo + ", " + hi + ")");
        }
    }
}

/* *****************************************************************************
 **************************************************************************** */
/******************************************************************************
 *  This class provides a basic capability for
 *  creating drawings with your programs. It uses a simple graphics model that
 *  allows you to create drawings consisting of geometric shapes (e.g.,
 *  points, lines, circles, rectangles) in a window on your computer
 *  and to save the drawings to a file.
 ******************************************************************************/

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import java.awt.image.BufferedImage;
import java.awt.image.DirectColorModel;
import java.awt.image.WritableRaster;

import java.io.File;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.LinkedList;
import java.util.TreeSet;
import java.util.NoSuchElementException;
import javax.imageio.ImageIO;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public final class Draw implements ActionListener, MouseListener, MouseMotionListener, KeyListener {

    public static final Color BLACK = Color.BLACK;   //  The color black.
    public static final Color BLUE = Color.BLUE;   // The color blue.
    public static final Color CYAN = Color.CYAN;   //  The color cyan.
    public static final Color DARK_GRAY = Color.DARK_GRAY;   //  The color dark gray.
    public static final Color GRAY = Color.GRAY;   //  The color grayã€‚
    public static final Color GREEN  = Color.GREEN;   // The color green.
    public static final Color LIGHT_GRAY = Color.LIGHT_GRAY;   //  The color light gray.
    public static final Color MAGENTA = Color.MAGENTA;   //  The color magenta.
    public static final Color ORANGE = Color.ORANGE;   //  The color orange.
    public static final Color PINK = Color.PINK;   //  The color pink.
    public static final Color RED = Color.RED;   //  The color red.
    public static final Color WHITE = Color.WHITE;   //  The color white.
    public static final Color YELLOW = Color.YELLOW;   //  The color yellow.

    /**
     * Shade of blue.
     * It is Pantone 300U. The RGB values are approximately (9, 90, 166).
     */
    public static final Color BOOK_BLUE = new Color(9, 90, 166);

    /**
     * Shade of light blue.
     * The RGB values are approximately (103, 198, 243).
     */
    public static final Color BOOK_LIGHT_BLUE = new Color(103, 198, 243);

    /**
     * Shade of red.
     * It is Pantone 1805U. The RGB values are approximately (150, 35, 31).
     */
    public static final Color BOOK_RED = new Color(150, 35, 31);

    /**
     * Shade of orange.
     * It is PMS 158. The RGB values are approximately (245, 128, 37).
     */
    public static final Color PRINCETON_ORANGE = new Color(245, 128, 37);

    // default colors
    private static final Color DEFAULT_PEN_COLOR   = BLACK;
    private static final Color DEFAULT_CLEAR_COLOR = WHITE;

    // current pen color
    private static Color penColor;

    // default canvas size is DEFAULT_SIZE-by-DEFAULT_SIZE
    private static final int DEFAULT_SIZE = 512;
    private static int width  = DEFAULT_SIZE;
    private static int height = DEFAULT_SIZE;

    // default pen radius
    private static final double DEFAULT_PEN_RADIUS = 0.002;

    // current pen radius
    private static double penRadius;

    // show we draw immediately or wait until next show?
    private static boolean defer = false;

    // boundary of drawing canvas, 0% border
    // private static final double BORDER = 0.05;
    private static final double BORDER = 0.00;
    private static final double DEFAULT_XMIN = 0.0;
    private static final double DEFAULT_XMAX = 1.0;
    private static final double DEFAULT_YMIN = 0.0;
    private static final double DEFAULT_YMAX = 1.0;
    private static double xmin, ymin, xmax, ymax;

    // for synchronization
    private static Object mouseLock = new Object();
    private static Object keyLock = new Object();

    // default font
    private static final Font DEFAULT_FONT = new Font("SansSerif", Font.PLAIN, 16);

    // current font
    private static Font font;

    // double buffered graphics
    private static BufferedImage offscreenImage, onscreenImage;
    private static Graphics2D offscreen, onscreen;

    // singleton for callbacks: avoids generation of extra .class files
    private static Draw std = new Draw();

    // the frame for drawing to the screen
    private static JFrame frame;

    // mouse state
    private static boolean isMousePressed = false;
    private static double mouseX = 0;
    private static double mouseY = 0;

    // queue of typed key characters
    private static LinkedList<Character> keysTyped;

    // set of key codes currently pressed down
    private static TreeSet<Integer> keysDown;

    // singleton pattern: client can't instantiate
    private Draw() { }


    // static initializer
    static {
        init();
    }

    /**
     * Sets the canvas (drawing area) to be 512-by-512 pixels.
     * This also erases the current drawing and resets the coordinate system,
     * pen radius, pen color, and font back to their default values.
     * Ordinarly, this method is called once, at the very beginning
     * of a program.
     */
    public static void setCanvasSize() {
        setCanvasSize(DEFAULT_SIZE, DEFAULT_SIZE);
    }

    /**
     * Sets the canvas (drawing area) to be width-by-height pixels.
     * This also erases the current drawing and resets the coordinate system,
     * pen radius, pen color, and font back to their default values.
     * Ordinarly, this method is called once, at the very beginning
     * of a program.
     *
     * @param  canvasWidth the width as a number of pixels
     * @param  canvasHeight the height as a number of pixels
     * @throws IllegalArgumentException unless both canvasWidth and
     *         canvasHeight are positive
     */
    public static void setCanvasSize(int canvasWidth, int canvasHeight) {
        if (canvasWidth <= 0) throw new IllegalArgumentException("width must be positive");
        if (canvasHeight <= 0) throw new IllegalArgumentException("height must be positive");
        width = canvasWidth;
        height = canvasHeight;
        init();
    }

    // init
    private static void init() {
        if (frame != null) frame.setVisible(false);
        frame = new JFrame();
        offscreenImage = new BufferedImage(2*width, 2*height, BufferedImage.TYPE_INT_ARGB);
        onscreenImage  = new BufferedImage(2*width, 2*height, BufferedImage.TYPE_INT_ARGB);
        offscreen = offscreenImage.createGraphics();
        onscreen  = onscreenImage.createGraphics();
        offscreen.scale(2.0, 2.0);  // since we made it 2x as big

        setXscale();
        setYscale();
        offscreen.setColor(DEFAULT_CLEAR_COLOR);
        offscreen.fillRect(0, 0, width, height);
        setPenColor();
        setPenRadius();
        setFont();
        clear();

        // initialize keystroke buffers
        keysTyped = new LinkedList<Character>();
        keysDown = new TreeSet<Integer>();

        // add antialiasing
        RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                                                  RenderingHints.VALUE_ANTIALIAS_ON);
        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        offscreen.addRenderingHints(hints);

        // frame stuff
        RetinaImageIcon icon = new RetinaImageIcon(onscreenImage);
        JLabel draw = new JLabel(icon);

        draw.addMouseListener(std);
        draw.addMouseMotionListener(std);

        frame.setContentPane(draw);
        frame.addKeyListener(std);    // JLabel cannot get keyboard focus
        frame.setFocusTraversalKeysEnabled(false);  // allow VK_TAB with isKeyPressed()
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);            // closes all windows
        // frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);      // closes only current window
        frame.setTitle("Standard Draw");
        frame.setJMenuBar(createMenuBar());
        frame.pack();
        frame.requestFocusInWindow();
        frame.setVisible(true);
    }

    // create the menu bar (changed to private)
    private static JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menuBar.add(menu);
        JMenuItem menuItem1 = new JMenuItem(" Save...   ");
        menuItem1.addActionListener(std);
        menuItem1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        menu.add(menuItem1);
        return menuBar;
    }

   /***************************************************************************
    *  User and screen coordinate systems.
    ***************************************************************************/

    // throw an IllegalArgumentException if x is NaN or infinite
    private static void validate(double x, String name) {
        if (Double.isNaN(x)) throw new IllegalArgumentException(name + " is NaN");
        if (Double.isInfinite(x)) throw new IllegalArgumentException(name + " is infinite");
    }

    // throw an IllegalArgumentException if s is null
    private static void validateNonnegative(double x, String name) {
        if (x < 0) throw new IllegalArgumentException(name + " negative");
    }

    // throw an IllegalArgumentException if s is null
    private static void validateNotNull(Object x, String name) {
        if (x == null) throw new IllegalArgumentException(name + " is null");
    }


    // Sets the x-scale to be the default (between 0.0 and 1.0).
    public static void setXscale() {
        setXscale(DEFAULT_XMIN, DEFAULT_XMAX);
    }

    // Sets the y-scale to be the default (between 0.0 and 1.0).
    public static void setYscale() {
        setYscale(DEFAULT_YMIN, DEFAULT_YMAX);
    }

    //** Sets the x-scale and y-scale to be the default (between 0.0 and 1.0).
    public static void setScale() {
        setXscale();
        setYscale();
    }

    /**
     * Sets the x-scale to the specified range.
     *
     * @param  min the minimum value of the x-scale
     * @param  max the maximum value of the x-scale
     * @throws IllegalArgumentException if (max == min)
     * @throws IllegalArgumentException if either min or max is either NaN or infinite
     */
    public static void setXscale(double min, double max) {
        validate(min, "min");
        validate(max, "max");
        double size = max - min;
        if (size == 0.0) throw new IllegalArgumentException("the min and max are the same");
        synchronized (mouseLock) {
            xmin = min - BORDER * size;
            xmax = max + BORDER * size;
        }
    }

    /**
     * Sets the y-scale to the specified range.
     *
     * @param  min the minimum value of the y-scale
     * @param  max the maximum value of the y-scale
     * @throws IllegalArgumentException if (max == min)
     * @throws IllegalArgumentException if either min or max is either NaN or infinite
     */
    public static void setYscale(double min, double max) {
        validate(min, "min");
        validate(max, "max");
        double size = max - min;
        if (size == 0.0) throw new IllegalArgumentException("the min and max are the same");
        synchronized (mouseLock) {
            ymin = min - BORDER * size;
            ymax = max + BORDER * size;
        }
    }

    /**
     * Sets both the x-scale and y-scale to the (same) specified range.
     *
     * @param  min the minimum value of the x- and y-scales
     * @param  max the maximum value of the x- and y-scales
     * @throws IllegalArgumentException if (max == min)
     * @throws IllegalArgumentException if either min or max is either NaN or infinite
     */
    public static void setScale(double min, double max) {
        validate(min, "min");
        validate(max, "max");
        double size = max - min;
        if (size == 0.0) throw new IllegalArgumentException("the min and max are the same");
        synchronized (mouseLock) {
            xmin = min - BORDER * size;
            xmax = max + BORDER * size;
            ymin = min - BORDER * size;
            ymax = max + BORDER * size;
        }
    }

    // helper functions that scale from user coordinates to screen coordinates and back
    private static double  scaleX(double x) { return width  * (x - xmin) / (xmax - xmin); }
    private static double  scaleY(double y) { return height * (ymax - y) / (ymax - ymin); }
    private static double factorX(double w) { return w * width  / Math.abs(xmax - xmin);  }
    private static double factorY(double h) { return h * height / Math.abs(ymax - ymin);  }
    private static double   userX(double x) { return xmin + x * (xmax - xmin) / width;    }
    private static double   userY(double y) { return ymax - y * (ymax - ymin) / height;   }


    // Clears the screen to the default color (white).
    public static void clear() {
        clear(DEFAULT_CLEAR_COLOR);
    }

    /**
     * Clears the screen to the specified color.
     *
     * @param color the color to make the background
     * @throws IllegalArgumentException if color is null
     */
    public static void clear(Color color) {
        validateNotNull(color, "color");
        offscreen.setColor(color);
        offscreen.fillRect(0, 0, width, height);
        offscreen.setColor(penColor);
        draw();
    }

    /**
     * Returns the current pen radius.
     *
     * @return the current value of the pen radius
     */
    public static double getPenRadius() {
        return penRadius;
    }

    /**
     * Sets the pen size to the default size (0.002).
     * The pen is circular, so that lines have rounded ends, and when you set the
     * pen radius and draw a point, you get a circle of the specified radius.
     * The pen radius is not affected by coordinate scaling.
     */
    public static void setPenRadius() {
        setPenRadius(DEFAULT_PEN_RADIUS);
    }

    /**
     * Sets the radius of the pen to the specified size.
     * The pen is circular, so that lines have rounded ends, and when you set the
     * pen radius and draw a point, you get a circle of the specified radius.
     * The pen radius is not affected by coordinate scaling.
     *
     * @param  radius the radius of the pen
     * @throws IllegalArgumentException if radius is negative, NaN, or infinite
     */
    public static void setPenRadius(double radius) {
        validate(radius, "pen radius");
        validateNonnegative(radius, "pen radius");

        penRadius = radius;
        float scaledPenRadius = (float) (radius * DEFAULT_SIZE);
        BasicStroke stroke = new BasicStroke(scaledPenRadius, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        offscreen.setStroke(stroke);
    }

    /**
     * Returns the current pen color.
     *
     * @return the current pen color
     */
    public static Color getPenColor() {
        return penColor;
    }

    /**
     * Sets the pen color to the default color (black).
     */
    public static void setPenColor() {
        setPenColor(DEFAULT_PEN_COLOR);
    }

    /**
     * Sets the pen color to the specified color.
     * <p>
     * The predefined pen colors are
     * Draw.BLACK, Draw.BLUE, Draw.CYAN,
     * Draw.DARK_GRAY, Draw.GRAY, Draw.GREEN,
     * Draw.LIGHT_GRAY, Draw.MAGENTA, Draw.ORANGE,
     * Draw.PINK, Draw.RED, Draw.WHITE, and
     * Draw.YELLOW.
     *
     * @param color the color to make the pen
     * @throws IllegalArgumentException if color is null
     */
    public static void setPenColor(Color color) {
        validateNotNull(color, "color");
        penColor = color;
        offscreen.setColor(penColor);
    }

    /**
     * Sets the pen color to the specified RGB color.
     *
     * @param  red the amount of red (between 0 and 255)
     * @param  green the amount of green (between 0 and 255)
     * @param  blue the amount of blue (between 0 and 255)
     * @throws IllegalArgumentException if red, green,
     *         or blue is outside its prescribed range
     */
    public static void setPenColor(int red, int green, int blue) {
        if (red   < 0 || red   >= 256) throw new IllegalArgumentException("red must be between 0 and 255");
        if (green < 0 || green >= 256) throw new IllegalArgumentException("green must be between 0 and 255");
        if (blue  < 0 || blue  >= 256) throw new IllegalArgumentException("blue must be between 0 and 255");
        setPenColor(new Color(red, green, blue));
    }

    /**
     * Returns the current font.
     *
     * @return the current font
     */
    public static Font getFont() {
        return font;
    }

    /**
     * Sets the font to the default font (sans serif, 16 point).
     */
    public static void setFont() {
        setFont(DEFAULT_FONT);
    }

    /**
     * Sets the font to the specified value.
     *
     * @param font the font
     * @throws IllegalArgumentException if font is null
     */
    public static void setFont(Font font) {
        validateNotNull(font, "font");
        Draw.font = font;
    }


   /***************************************************************************
    *  Drawing geometric shapes.
    ***************************************************************************/

    /**
     * Draws a line segment between (x0, y0) and
     * (x1, y1).
     *
     * @param  x0 the x-coordinate of one endpoint
     * @param  y0 the y-coordinate of one endpoint
     * @param  x1 the x-coordinate of the other endpoint
     * @param  y1 the y-coordinate of the other endpoint
     * @throws IllegalArgumentException if any coordinate is either NaN or infinite
     */
    public static void line(double x0, double y0, double x1, double y1) {
        validate(x0, "x0");
        validate(y0, "y0");
        validate(x1, "x1");
        validate(y1, "y1");
        offscreen.draw(new Line2D.Double(scaleX(x0), scaleY(y0), scaleX(x1), scaleY(y1)));
        draw();
    }

    /**
     * Draws one pixel at (x, y).
     * This method is private because pixels depend on the display.
     * To achieve the same effect, set the pen radius to 0 and call point().
     *
     * @param  x the x-coordinate of the pixel
     * @param  y the y-coordinate of the pixel
     * @throws IllegalArgumentException if x or y is either NaN or infinite
     */
    private static void pixel(double x, double y) {
        validate(x, "x");
        validate(y, "y");
        offscreen.fillRect((int) Math.round(scaleX(x)), (int) Math.round(scaleY(y)), 1, 1);
    }

    /**
     * Draws a point centered at (x, y).
     * The point is a filled circle whose radius is equal to the pen radius.
     * To draw a single-pixel point, first set the pen radius to 0.
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     * @throws IllegalArgumentException if either x or y is either NaN or infinite
     */
    public static void point(double x, double y) {
        validate(x, "x");
        validate(y, "y");

        double xs = scaleX(x);
        double ys = scaleY(y);
        double r = penRadius;
        float scaledPenRadius = (float) (r * DEFAULT_SIZE);

        if (scaledPenRadius <= 1) pixel(x, y);
        else offscreen.fill(new Ellipse2D.Double(xs - scaledPenRadius/2, ys - scaledPenRadius/2,
                                                 scaledPenRadius, scaledPenRadius));
        draw();
    }

    /**
     * Draws a circle of the specified radius, centered at (x, y).
     *
     * @param  x the x-coordinate of the center of the circle
     * @param  y the y-coordinate of the center of the circle
     * @param  radius the radius of the circle
     * @throws IllegalArgumentException if radius is negative
     * @throws IllegalArgumentException if any argument is either NaN or infinite
     */
    public static void circle(double x, double y, double radius) {
        validate(x, "x");
        validate(y, "y");
        validate(radius, "radius");
        validateNonnegative(radius, "radius");

        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2*radius);
        double hs = factorY(2*radius);
        if (ws <= 1 && hs <= 1) pixel(x, y);
        else offscreen.draw(new Ellipse2D.Double(xs - ws/2, ys - hs/2, ws, hs));
        draw();
    }

    /**
     * Draws a filled circle of the specified radius, centered at (x, y).
     *
     * @param  x the x-coordinate of the center of the circle
     * @param  y the y-coordinate of the center of the circle
     * @param  radius the radius of the circle
     * @throws IllegalArgumentException if radius is negative
     * @throws IllegalArgumentException if any argument is either NaN or infinite
     */
    public static void filledCircle(double x, double y, double radius) {
        validate(x, "x");
        validate(y, "y");
        validate(radius, "radius");
        validateNonnegative(radius, "radius");

        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2*radius);
        double hs = factorY(2*radius);
        if (ws <= 1 && hs <= 1) pixel(x, y);
        else offscreen.fill(new Ellipse2D.Double(xs - ws/2, ys - hs/2, ws, hs));
        draw();
    }


    /**
     * Draws an ellipse with the specified semimajor and semiminor axes,
     * centered at (x, y).
     *
     * @param  x the x-coordinate of the center of the ellipse
     * @param  y the y-coordinate of the center of the ellipse
     * @param  semiMajorAxis is the semimajor axis of the ellipse
     * @param  semiMinorAxis is the semiminor axis of the ellipse
     * @throws IllegalArgumentException if either {@code semiMajorAxis}
     *         or semiMinorAxis is negative
     * @throws IllegalArgumentException if any argument is either NaN or infinite
     */
    public static void ellipse(double x, double y, double semiMajorAxis, double semiMinorAxis) {
        validate(x, "x");
        validate(y, "y");
        validate(semiMajorAxis, "semimajor axis");
        validate(semiMinorAxis, "semiminor axis");
        validateNonnegative(semiMajorAxis, "semimajor axis");
        validateNonnegative(semiMinorAxis, "semiminor axis");

        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2*semiMajorAxis);
        double hs = factorY(2*semiMinorAxis);
        if (ws <= 1 && hs <= 1) pixel(x, y);
        else offscreen.draw(new Ellipse2D.Double(xs - ws/2, ys - hs/2, ws, hs));
        draw();
    }

    /**
     * Draws a filled ellipse with the specified semimajor and semiminor axes,
     * centered at (x, y).
     *
     * @param  x the x-coordinate of the center of the ellipse
     * @param  y the y-coordinate of the center of the ellipse
     * @param  semiMajorAxis is the semimajor axis of the ellipse
     * @param  semiMinorAxis is the semiminor axis of the ellipse
     * @throws IllegalArgumentException if either semiMajorAxis
     *         or semiMinorAxis is negative
     * @throws IllegalArgumentException if any argument is either NaN or infinite
     */
    public static void filledEllipse(double x, double y, double semiMajorAxis, double semiMinorAxis) {
        validate(x, "x");
        validate(y, "y");
        validate(semiMajorAxis, "semimajor axis");
        validate(semiMinorAxis, "semiminor axis");
        validateNonnegative(semiMajorAxis, "semimajor axis");
        validateNonnegative(semiMinorAxis, "semiminor axis");

        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2*semiMajorAxis);
        double hs = factorY(2*semiMinorAxis);
        if (ws <= 1 && hs <= 1) pixel(x, y);
        else offscreen.fill(new Ellipse2D.Double(xs - ws/2, ys - hs/2, ws, hs));
        draw();
    }


    /**
     * Draws a circular arc of the specified radius,
     * centered at (x, y), from angle1 to angle2 (in degrees).
     *
     * @param  x the x-coordinate of the center of the circle
     * @param  y the y-coordinate of the center of the circle
     * @param  radius the radius of the circle
     * @param  angle1 the starting angle. 0 would mean an arc beginning at 3 o'clock.
     * @param  angle2 the angle at the end of the arc. For example, if
     *         you want a 90 degree arc, then angle2 should be angle1 + 90.
     * @throws IllegalArgumentException if radius is negative
     * @throws IllegalArgumentException if any argument is either NaN or infinite
     */
    public static void arc(double x, double y, double radius, double angle1, double angle2) {
        validate(x, "x");
        validate(y, "y");
        validate(radius, "arc radius");
        validate(angle1, "angle1");
        validate(angle2, "angle2");
        validateNonnegative(radius, "arc radius");

        while (angle2 < angle1) angle2 += 360;
        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2*radius);
        double hs = factorY(2*radius);
        if (ws <= 1 && hs <= 1) pixel(x, y);
        else offscreen.draw(new Arc2D.Double(xs - ws/2, ys - hs/2, ws, hs, angle1, angle2 - angle1, Arc2D.OPEN));
        draw();
    }

    /**
     * Draws a square of the specified size, centered at (x, y).
     *
     * @param  x the x-coordinate of the center of the square
     * @param  y the y-coordinate of the center of the square
     * @param  halfLength one half the length of any side of the square
     * @throws IllegalArgumentException if halfLength is negative
     * @throws IllegalArgumentException if any argument is either NaN or infinite
     */
    public static void square(double x, double y, double halfLength) {
        validate(x, "x");
        validate(y, "y");
        validate(halfLength, "halfLength");
        validateNonnegative(halfLength, "half length");

        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2*halfLength);
        double hs = factorY(2*halfLength);
        if (ws <= 1 && hs <= 1) pixel(x, y);
        else offscreen.draw(new Rectangle2D.Double(xs - ws/2, ys - hs/2, ws, hs));
        draw();
    }

    /**
     * Draws a filled square of the specified size, centered at (x, y).
     *
     * @param  x the x-coordinate of the center of the square
     * @param  y the y-coordinate of the center of the square
     * @param  halfLength one half the length of any side of the square
     * @throws IllegalArgumentException if halfLength is negative
     * @throws IllegalArgumentException if any argument is either NaN or infinite
     */
    public static void filledSquare(double x, double y, double halfLength) {
        validate(x, "x");
        validate(y, "y");
        validate(halfLength, "halfLength");
        validateNonnegative(halfLength, "half length");

        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2*halfLength);
        double hs = factorY(2*halfLength);
        if (ws <= 1 && hs <= 1) pixel(x, y);
        else offscreen.fill(new Rectangle2D.Double(xs - ws/2, ys - hs/2, ws, hs));
        draw();
    }


    /**
     * Draws a rectangle of the specified size, centered at (x, y).
     *
     * @param  x the x-coordinate of the center of the rectangle
     * @param  y the y-coordinate of the center of the rectangle
     * @param  halfWidth one half the width of the rectangle
     * @param  halfHeight one half the height of the rectangle
     * @throws IllegalArgumentException if either halfWidth or halfHeight is negative
     * @throws IllegalArgumentException if any argument is either NaN or infinite
     */
    public static void rectangle(double x, double y, double halfWidth, double halfHeight) {
        validate(x, "x");
        validate(y, "y");
        validate(halfWidth, "halfWidth");
        validate(halfHeight, "halfHeight");
        validateNonnegative(halfWidth, "half width");
        validateNonnegative(halfHeight, "half height");

        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2*halfWidth);
        double hs = factorY(2*halfHeight);
        if (ws <= 1 && hs <= 1) pixel(x, y);
        else offscreen.draw(new Rectangle2D.Double(xs - ws/2, ys - hs/2, ws, hs));
        draw();
    }

    /**
     * Draws a filled rectangle of the specified size, centered at (x, y).
     *
     * @param  x the x-coordinate of the center of the rectangle
     * @param  y the y-coordinate of the center of the rectangle
     * @param  halfWidth one half the width of the rectangle
     * @param  halfHeight one half the height of the rectangle
     * @throws IllegalArgumentException if either halfWidth or halfHeight is negative
     * @throws IllegalArgumentException if any argument is either NaN or infinite
     */
    public static void filledRectangle(double x, double y, double halfWidth, double halfHeight) {
        validate(x, "x");
        validate(y, "y");
        validate(halfWidth, "halfWidth");
        validate(halfHeight, "halfHeight");
        validateNonnegative(halfWidth, "half width");
        validateNonnegative(halfHeight, "half height");

        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2*halfWidth);
        double hs = factorY(2*halfHeight);
        if (ws <= 1 && hs <= 1) pixel(x, y);
        else offscreen.fill(new Rectangle2D.Double(xs - ws/2, ys - hs/2, ws, hs));
        draw();
    }


    /**
     * Draws a polygon with the vertices 
     * (x0, y0),
     * (x1, y1, ...,
     * (xnâ€“1, ynâ€“1).
     *
     * @param  x an array of all the x-coordinates of the polygon
     * @param  y an array of all the y-coordinates of the polygon
     * @throws IllegalArgumentException unless x[] and y[]
     *         are of the same length
     * @throws IllegalArgumentException if any coordinate is either NaN or infinite
     * @throws IllegalArgumentException if either x[] or y[] is null
     */
    public static void polygon(double[] x, double[] y) {
        validateNotNull(x, "x-coordinate array");
        validateNotNull(y, "y-coordinate array");
        for (int i = 0; i < x.length; i++) validate(x[i], "x[" + i + "]");
        for (int i = 0; i < y.length; i++) validate(y[i], "y[" + i + "]");

        int n1 = x.length;
        int n2 = y.length;
        if (n1 != n2) throw new IllegalArgumentException("arrays must be of the same length");
        int n = n1;
        if (n == 0) return;

        GeneralPath path = new GeneralPath();
        path.moveTo((float) scaleX(x[0]), (float) scaleY(y[0]));
        for (int i = 0; i < n; i++)
            path.lineTo((float) scaleX(x[i]), (float) scaleY(y[i]));
        path.closePath();
        offscreen.draw(path);
        draw();
    }

    /**
     * Draws a filled polygon with the vertices 
     * (x0, y0),
     * (x1, y1), ...,
     * (xnâ€“1, ynâ€“1).
     *
     * @param  x an array of all the x-coordinates of the polygon
     * @param  y an array of all the y-coordinates of the polygon
     * @throws IllegalArgumentException unless x[] and y[]
     *         are of the same length
     * @throws IllegalArgumentException if any coordinate is either NaN or infinite
     * @throws IllegalArgumentException if either x[] or y[] is null
     */
    public static void filledPolygon(double[] x, double[] y) {
        validateNotNull(x, "x-coordinate array");
        validateNotNull(y, "y-coordinate array");
        for (int i = 0; i < x.length; i++) validate(x[i], "x[" + i + "]");
        for (int i = 0; i < y.length; i++) validate(y[i], "y[" + i + "]");

        int n1 = x.length;
        int n2 = y.length;
        if (n1 != n2) throw new IllegalArgumentException("arrays must be of the same length");
        int n = n1;
        if (n == 0) return;

        GeneralPath path = new GeneralPath();
        path.moveTo((float) scaleX(x[0]), (float) scaleY(y[0]));
        for (int i = 0; i < n; i++)
            path.lineTo((float) scaleX(x[i]), (float) scaleY(y[i]));
        path.closePath();
        offscreen.fill(path);
        draw();
    }


   /***************************************************************************
    *  Drawing images.
    ***************************************************************************/
    // get an image from the given filename
    private static Image getImage(String filename) {
        if (filename == null) throw new IllegalArgumentException();

        // to read from file
        ImageIcon icon = new ImageIcon(filename);

        // try to read from URL
        if ((icon == null) || (icon.getImageLoadStatus() != MediaTracker.COMPLETE)) {
            try {
                URL url = new URL(filename);
                icon = new ImageIcon(url);
            }
            catch (MalformedURLException e) {
                /* not a url */
            }
        }

        // in case file is inside a .jar (classpath relative to Draw)
        if ((icon == null) || (icon.getImageLoadStatus() != MediaTracker.COMPLETE)) {
            URL url = Draw.class.getResource(filename);
            if (url != null)
                icon = new ImageIcon(url);
        }

        // in case file is inside a .jar (classpath relative to root of jar)
        if ((icon == null) || (icon.getImageLoadStatus() != MediaTracker.COMPLETE)) {
            URL url = Draw.class.getResource("/" + filename);
            if (url == null) throw new IllegalArgumentException("image " + filename + " not found");
            icon = new ImageIcon(url);
        }

        return icon.getImage();
    }

    /**
     * Draws the specified image centered at (x, y).
     * The supported image formats are JPEG, PNG, and GIF.
     * As an optimization, the picture is cached, so there is no performance
     * penalty for redrawing the same image multiple times (e.g., in an animation).
     * However, if you change the picture file after drawing it, subsequent
     * calls will draw the original picture.
     *
     * @param  x the center x-coordinate of the image
     * @param  y the center y-coordinate of the image
     * @param  filename the name of the image/picture, e.g., "ball.gif"
     * @throws IllegalArgumentException if the image filename is invalid
     * @throws IllegalArgumentException if either x or y is either NaN or infinite
     */
    public static void picture(double x, double y, String filename) {
        validate(x, "x");
        validate(y, "y");
        validateNotNull(filename, "filename");

        Image image = getImage(filename);
        double xs = scaleX(x);
        double ys = scaleY(y);
        int ws = image.getWidth(null);
        int hs = image.getHeight(null);
        if (ws < 0 || hs < 0) throw new IllegalArgumentException("image " + filename + " is corrupt");

        offscreen.drawImage(image, (int) Math.round(xs - ws/2.0), (int) Math.round(ys - hs/2.0), null);
        draw();
    }

    /**
     * Draws the specified image centered at (x, y),
     * rotated given number of degrees.
     * The supported image formats are JPEG, PNG, and GIF.
     *
     * @param  x the center x-coordinate of the image
     * @param  y the center y-coordinate of the image
     * @param  filename the name of the image/picture, e.g., "ball.gif"
     * @param  degrees is the number of degrees to rotate counterclockwise
     * @throws IllegalArgumentException if the image filename is invalid
     * @throws IllegalArgumentException if x, y, degrees is NaN or infinite
     * @throws IllegalArgumentException if filename is null
     */
    public static void picture(double x, double y, String filename, double degrees) {
        validate(x, "x");
        validate(y, "y");
        validate(degrees, "degrees");
        validateNotNull(filename, "filename");

        Image image = getImage(filename);
        double xs = scaleX(x);
        double ys = scaleY(y);
        int ws = image.getWidth(null);
        int hs = image.getHeight(null);
        if (ws < 0 || hs < 0) throw new IllegalArgumentException("image " + filename + " is corrupt");

        offscreen.rotate(Math.toRadians(-degrees), xs, ys);
        offscreen.drawImage(image, (int) Math.round(xs - ws/2.0), (int) Math.round(ys - hs/2.0), null);
        offscreen.rotate(Math.toRadians(+degrees), xs, ys);

        draw();
    }

    /**
     * Draws the specified image centered at (x, y),
     * rescaled to the specified bounding box.
     * The supported image formats are JPEG, PNG, and GIF.
     *
     * @param  x the center x-coordinate of the image
     * @param  y the center y-coordinate of the image
     * @param  filename the name of the image/picture, e.g., "ball.gif"
     * @param  scaledWidth the width of the scaled image (in screen coordinates)
     * @param  scaledHeight the height of the scaled image (in screen coordinates)
     * @throws IllegalArgumentException if either scaledWidth
     *         or scaledHeight is negative
     * @throws IllegalArgumentException if the image filename is invalid
     * @throws IllegalArgumentException if x or y is either NaN or infinite
     * @throws IllegalArgumentException if filename is null
     */
    public static void picture(double x, double y, String filename, double scaledWidth, double scaledHeight) {
        validate(x, "x");
        validate(y, "y");
        validate(scaledWidth, "scaled width");
        validate(scaledHeight, "scaled height");
        validateNotNull(filename, "filename");
        validateNonnegative(scaledWidth, "scaled width");
        validateNonnegative(scaledHeight, "scaled height");

        Image image = getImage(filename);
        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(scaledWidth);
        double hs = factorY(scaledHeight);
        if (ws < 0 || hs < 0) throw new IllegalArgumentException("image " + filename + " is corrupt");
        if (ws <= 1 && hs <= 1) pixel(x, y);
        else {
            offscreen.drawImage(image, (int) Math.round(xs - ws/2.0),
                                       (int) Math.round(ys - hs/2.0),
                                       (int) Math.round(ws),
                                       (int) Math.round(hs), null);
        }
        draw();
    }


    /**
     * Draws the specified image centered at (x, y), rotated
     * given number of degrees, and rescaled to the specified bounding box.
     * The supported image formats are JPEG, PNG, and GIF.
     *
     * @param  x the center x-coordinate of the image
     * @param  y the center y-coordinate of the image
     * @param  filename the name of the image/picture, e.g., "ball.gif"
     * @param  scaledWidth the width of the scaled image (in screen coordinates)
     * @param  scaledHeight the height of the scaled image (in screen coordinates)
     * @param  degrees is the number of degrees to rotate counterclockwise
     * @throws IllegalArgumentException if either scaledWidth
     *         or scaledHeight is negative
     * @throws IllegalArgumentException if the image filename is invalid
     */
    public static void picture(double x, double y, String filename, double scaledWidth, double scaledHeight, double degrees) {
        validate(x, "x");
        validate(y, "y");
        validate(scaledWidth, "scaled width");
        validate(scaledHeight, "scaled height");
        validate(degrees, "degrees");
        validateNotNull(filename, "filename");
        validateNonnegative(scaledWidth, "scaled width");
        validateNonnegative(scaledHeight, "scaled height");

        Image image = getImage(filename);
        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(scaledWidth);
        double hs = factorY(scaledHeight);
        if (ws < 0 || hs < 0) throw new IllegalArgumentException("image " + filename + " is corrupt");
        if (ws <= 1 && hs <= 1) pixel(x, y);

        offscreen.rotate(Math.toRadians(-degrees), xs, ys);
        offscreen.drawImage(image, (int) Math.round(xs - ws/2.0),
                                   (int) Math.round(ys - hs/2.0),
                                   (int) Math.round(ws),
                                   (int) Math.round(hs), null);
        offscreen.rotate(Math.toRadians(+degrees), xs, ys);

        draw();
    }

   /***************************************************************************
    *  Drawing text.
    ***************************************************************************/

    /**
     * Writes the given text string in the current font, centered at (x, y).
     *
     * @param  x the center x-coordinate of the text
     * @param  y the center y-coordinate of the text
     * @param  text the text to write
     * @throws IllegalArgumentException if text is null
     * @throws IllegalArgumentException if x or y is either NaN or infinite
     */
    public static void text(double x, double y, String text) {
        validate(x, "x");
        validate(y, "y");
        validateNotNull(text, "text");

        offscreen.setFont(font);
        FontMetrics metrics = offscreen.getFontMetrics();
        double xs = scaleX(x);
        double ys = scaleY(y);
        int ws = metrics.stringWidth(text);
        int hs = metrics.getDescent();
        offscreen.drawString(text, (float) (xs - ws/2.0), (float) (ys + hs));
        draw();
    }

    /**
     * Writes the given text string in the current font, centered at (x, y) and
     * rotated by the specified number of degrees.
     * @param  x the center x-coordinate of the text
     * @param  y the center y-coordinate of the text
     * @param  text the text to write
     * @param  degrees is the number of degrees to rotate counterclockwise
     * @throws IllegalArgumentException if text is null
     * @throws IllegalArgumentException if x, y, or degrees is either NaN or infinite
     */
    public static void text(double x, double y, String text, double degrees) {
        validate(x, "x");
        validate(y, "y");
        validate(degrees, "degrees");
        validateNotNull(text, "text");

        double xs = scaleX(x);
        double ys = scaleY(y);
        offscreen.rotate(Math.toRadians(-degrees), xs, ys);
        text(x, y, text);
        offscreen.rotate(Math.toRadians(+degrees), xs, ys);
    }


    /**
     * Writes the given text string in the current font, left-aligned at (x, y).
     * @param  x the x-coordinate of the text
     * @param  y the y-coordinate of the text
     * @param  text the text
     * @throws IllegalArgumentException if text is null
     * @throws IllegalArgumentException if x or y is either NaN or infinite
     */
    public static void textLeft(double x, double y, String text) {
        validate(x, "x");
        validate(y, "y");
        validateNotNull(text, "text");

        offscreen.setFont(font);
        FontMetrics metrics = offscreen.getFontMetrics();
        double xs = scaleX(x);
        double ys = scaleY(y);
        int hs = metrics.getDescent();
        offscreen.drawString(text, (float) xs, (float) (ys + hs));
        draw();
    }

    /**
     * Writes the given text string in the current font, right-aligned at (x, y).
     *
     * @param  x the x-coordinate of the text
     * @param  y the y-coordinate of the text
     * @param  text the text to write
     * @throws IllegalArgumentException if text is null
     * @throws IllegalArgumentException if x or y is either NaN or infinite
     */
    public static void textRight(double x, double y, String text) {
        validate(x, "x");
        validate(y, "y");
        validateNotNull(text, "text");

        offscreen.setFont(font);
        FontMetrics metrics = offscreen.getFontMetrics();
        double xs = scaleX(x);
        double ys = scaleY(y);
        int ws = metrics.stringWidth(text);
        int hs = metrics.getDescent();
        offscreen.drawString(text, (float) (xs - ws), (float) (ys + hs));
        draw();
    }


    /**
     * Copies the offscreen buffer to the onscreen buffer, pauses for t milliseconds
     * and enables double buffering.
     * @param t number of milliseconds
     * @deprecated replaced by {@link #enableDoubleBuffering()}, {@link #show()}, and {@link #pause(int t)}
     */
    @Deprecated
    public static void show(int t) {
        validateNonnegative(t, "t");
        show();
        pause(t);
        enableDoubleBuffering();
    }

    /**
     * Pauses for t milliseconds. This method is intended to support computer animations.
     * @param t number of milliseconds
     */
    public static void pause(int t) {
        validateNonnegative(t, "t");
        try {
            Thread.sleep(t);
        }
        catch (InterruptedException e) {
            System.out.println("Error sleeping");
        }
    }

    /**
     * Copies offscreen buffer to onscreen buffer. There is no reason to call
     * this method unless double buffering is enabled.
     */
    public static void show() {
        onscreen.drawImage(offscreenImage, 0, 0, null);
        frame.repaint();
    }

    // draw onscreen if defer is false
    private static void draw() {
        if (!defer) show();
    }

    /**
     * Enables double buffering. All subsequent calls to 
     * drawing methods such as line(), circle(),
     * and square() will be deferred until the next call
     * to show(). Useful for animations.
     */
    public static void enableDoubleBuffering() {
        defer = true;
    }

    /**
     * Disables double buffering. All subsequent calls to 
     * drawing methods such as line(), circle(),
     * and square() will be displayed on screen when called.
     * This is the default.
     */
    public static void disableDoubleBuffering() {
        defer = false;
    }


   /***************************************************************************
    *  Save drawing to a file.
    ***************************************************************************/

    /**
     * Saves the drawing to using the specified filename.
     * The supported image formats are JPEG and PNG;
     * the filename suffix must be .jpg or .png.
     *
     * @param  filename the name of the file with one of the required suffixes
     * @throws IllegalArgumentException if filename is null
     */
    public static void save(String filename) {
        validateNotNull(filename, "filename");
        File file = new File(filename);
        String suffix = filename.substring(filename.lastIndexOf('.') + 1);

        // png files
        if ("png".equalsIgnoreCase(suffix)) {
            try {
                ImageIO.write(onscreenImage, suffix, file);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        // need to change from ARGB to RGB for JPEG
        else if ("jpg".equalsIgnoreCase(suffix)) {
            WritableRaster raster = onscreenImage.getRaster();
            WritableRaster newRaster;
            newRaster = raster.createWritableChild(0, 0, width, height, 0, 0, new int[] {0, 1, 2});
            DirectColorModel cm = (DirectColorModel) onscreenImage.getColorModel();
            DirectColorModel newCM = new DirectColorModel(cm.getPixelSize(),
                                                          cm.getRedMask(),
                                                          cm.getGreenMask(),
                                                          cm.getBlueMask());
            BufferedImage rgbBuffer = new BufferedImage(newCM, newRaster, false,  null);
            try {
                ImageIO.write(rgbBuffer, suffix, file);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        else {
            System.out.println("Invalid image file type: " + suffix);
        }
    }


    /**
     * This method cannot be called directly.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        FileDialog chooser = new FileDialog(Draw.frame, "Use a .png or .jpg extension", FileDialog.SAVE);
        chooser.setVisible(true);
        String filename = chooser.getFile();
        if (filename != null) {
            Draw.save(chooser.getDirectory() + File.separator + chooser.getFile());
        }
    }


   /***************************************************************************
    *  Mouse interactions.
    ***************************************************************************/

    /**
     * Returns true if the mouse is being pressed.
     *
     * @return true if the mouse is being pressed; false otherwise
     */
    public static boolean isMousePressed() {
        synchronized (mouseLock) {
            return isMousePressed;
        }
    }

    /**
     * Returns true if the mouse is being pressed.
     *
     * @return true if the mouse is being pressed; false otherwise
     * @deprecated replaced by isMousePressed()
     */
    @Deprecated
    public static boolean mousePressed() {
        synchronized (mouseLock) {
            return isMousePressed;
        }
    }

    /**
     * Returns the x-coordinate of the mouse.
     *
     * @return the x-coordinate of the mouse
     */
    public static double mouseX() {
        synchronized (mouseLock) {
            return mouseX;
        }
    }

    /**
     * Returns the y-coordinate of the mouse.
     *
     * @return y-coordinate of the mouse
     */
    public static double mouseY() {
        synchronized (mouseLock) {
            return mouseY;
        }
    }


    /**
     * This method cannot be called directly.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        // this body is intentionally left empty
    }

    /**
     * This method cannot be called directly.
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        // this body is intentionally left empty
    }

    /**
     * This method cannot be called directly.
     */
    @Override
    public void mouseExited(MouseEvent e) {
        // this body is intentionally left empty
    }

    /**
     * This method cannot be called directly.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        synchronized (mouseLock) {
            mouseX = Draw.userX(e.getX());
            mouseY = Draw.userY(e.getY());
            isMousePressed = true;
        }
    }

    /**
     * This method cannot be called directly.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        synchronized (mouseLock) {
            isMousePressed = false;
        }
    }

    /**
     * This method cannot be called directly.
     */
    @Override
    public void mouseDragged(MouseEvent e)  {
        synchronized (mouseLock) {
            mouseX = Draw.userX(e.getX());
            mouseY = Draw.userY(e.getY());
        }
    }

    /**
     * This method cannot be called directly.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        synchronized (mouseLock) {
            mouseX = Draw.userX(e.getX());
            mouseY = Draw.userY(e.getY());
        }
    }


   /***************************************************************************
    *  Keyboard interactions.
    ***************************************************************************/

    /**
     * Returns true if the user has typed a key (that has not yet been processed).
     *
     * @return true if the user has typed a key (that has not yet been processed
     *         by nextKeyTyped(); false otherwise
     */
    public static boolean hasNextKeyTyped() {
        synchronized (keyLock) {
            return !keysTyped.isEmpty();
        }
    }

    /**
     * Returns the next key that was typed by the user (that your program has not already processed).
     * This method should be preceded by a call to hasNextKeyTyped() to ensure
     * that there is a next key to process.
     * This method returns a Unicode character corresponding to the key
     * typed (such as 'a' or 'A').
     * It cannot identify action keys (such as F1 and arrow keys)
     * or modifier keys (such as control).
     *
     * @return the next key typed by the user (that your program has not already processed).
     * @throws NoSuchElementException if there is no remaining key
     */
    public static char nextKeyTyped() {
        synchronized (keyLock) {
            if (keysTyped.isEmpty()) {
                throw new NoSuchElementException("your program has already processed all keystrokes");
            }
            return keysTyped.remove(keysTyped.size() - 1);
        }
    }

    /**
     * Returns true if the given key is being pressed.
     * <p>
     * This method takes the keycode (corresponding to a physical key)
    *  as an argument. It can handle action keys
     * (such as F1 and arrow keys) and modifier keys (such as shift and control).
     *
     * @param  keycode the key to check if it is being pressed
     * @return true if keycode is currently being pressed;
     *         false otherwise
     */
    public static boolean isKeyPressed(int keycode) {
        synchronized (keyLock) {
            return keysDown.contains(keycode);
        }
    }


    /**
     * This method cannot be called directly.
     */
    @Override
    public void keyTyped(KeyEvent e) {
        synchronized (keyLock) {
            keysTyped.addFirst(e.getKeyChar());
        }
    }

    /**
     * This method cannot be called directly.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        synchronized (keyLock) {
            keysDown.add(e.getKeyCode());
        }
    }

    /**
     * This method cannot be called directly.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        synchronized (keyLock) {
            keysDown.remove(e.getKeyCode());
        }
    }


   /***************************************************************************
    *  For improved resolution on Mac Retina displays.
    ***************************************************************************/

    private static class RetinaImageIcon extends ImageIcon {
    
        /**
     *
     */
    private static final long serialVersionUID = 1L;

        public RetinaImageIcon(Image image) {
            super(image);
        }

        public int getIconWidth() {
            return super.getIconWidth() / 2;
        }

        /**
         * Gets the height of the icon.
         *
         * @return the height in pixels of this icon
         */
        public int getIconHeight() {
            return super.getIconHeight() / 2;
        }

        public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            g2.scale(0.5, 0.5);
            super.paintIcon(c, g2, x * 2, y * 2);
            g2.dispose();
        }
    }
}





