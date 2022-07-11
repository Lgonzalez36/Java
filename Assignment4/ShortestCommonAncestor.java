import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;
/**
 * ShortestCommonAncestor Class for program 4
 * April 12, 2021
 * @author Luis Gonzalez
 */
public class ShortestCommonAncestor {
    private Digraph wordNetGraph;
    private static final int INFINITY = Integer.MAX_VALUE;
    private int minLenght;
    private int ancestor;

    /**
     * Ctor for the class
     * intializes the class graph
     * and then checks for rooted DAG
     * 
     * @param G graph
     */
    public ShortestCommonAncestor(Digraph G){
        this.wordNetGraph = new Digraph(G);
        DirectedCycle checkDAG = new DirectedCycle(this.wordNetGraph);
    }

    /**
     * length of shortest ancestral path between v and w
     * Creates two BFS objects to see if they both have a path to each other
     * @param v first vertex
     * @param w second vertex
     * @return min distance from each other
     */
    public int length(int v, int w){
        BreadthFirstDirectedPaths vbfs = new BreadthFirstDirectedPaths(wordNetGraph, v);
        BreadthFirstDirectedPaths wbfs = new BreadthFirstDirectedPaths(wordNetGraph, w);
        this.minLenght = INFINITY;
        this.ancestor = -1;
        for (int V = 0; V < wordNetGraph.V(); V++) {
            if (vbfs.hasPathTo(V) && wbfs.hasPathTo(V)) {
                int dist = vbfs.distTo(V) + wbfs.distTo(V);
                if (dist < minLenght){
                    this.minLenght = dist;
                    this.ancestor = V;
                }
            }
        }
        return this.minLenght;
    }

    /**
     * 
     * @return the ancestor 
     */
    public int getancestor(){
        return this.ancestor;
    }

    // a shortest common ancestor of vertices v and w
    /**
     * Method gets the ancestor from the giving input
     * 
     * @param v
     * @param w
     * @return
     */
    public int ancestor(int v, int w){
        return this.ancestor;
    }

    /**
     * length of shortest ancestral path of vertex subsets A and B
     * Takes two subjests and finds the shortest lenght and stores
     * the shortest ancestor
     * 
     * @param subsetA first subset
     * @param subsetB second sunset
     * @return the min distance of two subsets
     */
    public int lengthSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB){
        BreadthFirstDirectedPaths vbfsSubset = new BreadthFirstDirectedPaths(wordNetGraph, subsetA);
        BreadthFirstDirectedPaths wbfsSubset = new BreadthFirstDirectedPaths(wordNetGraph, subsetB);
        this.minLenght = INFINITY;
        this.ancestor = -1;
        for (int V = 0; V < wordNetGraph.V(); V++) {
            if (vbfsSubset.hasPathTo(V) && wbfsSubset.hasPathTo(V)) {
                int dist = vbfsSubset.distTo(V) + wbfsSubset.distTo(V);
                if (dist < minLenght){
                    this.minLenght = dist;
                    this.ancestor = V;
                }
            }
        }
        return this.minLenght;
    }


    /**
     * a shortest common ancestor of vertex subsets A and B
     * 
     * @param subsetA first subset
     * @param subsetB second subset
     * @return the graph number of the sca of two subsets
     */
    public int ancestorSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB){
        return this.ancestor;
    }

    /**
     * Method prints all the possible connections
     * from a giving source
     */
    public void printAllPathsFromV(){
        Scanner input1 = new Scanner(System.in);
        int s = input1.nextInt();
        BreadthFirstDirectedPaths vbfs = new BreadthFirstDirectedPaths(wordNetGraph, s);
        for (int v = 0; v < wordNetGraph.V(); v++) {
            if (vbfs.hasPathTo(v)) {
                System.out.printf("\n%d to %d (%d): ", s, v, vbfs.distTo(v));
                Stack<Integer> elements =  (Stack<Integer>) vbfs.pathTo(v);
                while (!elements.empty()){
                    int x = elements.pop();
                    if (x == s){
                        System.out.print(x);
                    }
                    else{        
                        System.out.print("->" + x);
                    }
                }
            }
        }
    }

    // unit testing (required)
    public static void main(String[] args) throws FileNotFoundException{
        File inFile = new File("Data\\hypernyms.txt");
        Scanner input = new Scanner(inFile);
        Digraph G = new Digraph(83127);
        
        while (input.hasNextLine()){
            String line = input.nextLine();
            String[] tokens = line.split(",");
            for (int i =1; i<tokens.length; i++){
                G.addEdge(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[i]));
            }
        } 
        input.close();

        ShortestCommonAncestor sca = new ShortestCommonAncestor(G);
        //while (input1.hasNext()) {
       // read in sources from standard input (e.g., 1 2 6)
        Scanner input1 = new Scanner(System.in);
        System.out.print("Enter your sources: ");
        int s = input1.nextInt();
        int s2 = input1.nextInt();

        BreadthFirstDirectedPaths vbfs = new BreadthFirstDirectedPaths(sca.wordNetGraph, s);
        BreadthFirstDirectedPaths wbfs = new BreadthFirstDirectedPaths(sca.wordNetGraph, s2);
        int min = INFINITY;
        int ancestor = -1;
        for (int v = 0; v < sca.wordNetGraph.V(); v++) {
            if (vbfs.hasPathTo(v) && wbfs.hasPathTo(v)) {
                System.out.printf("\n%d to %d (%d): ", s, v, vbfs.distTo(v));
                System.out.printf("\n%d to %d (%d): ", s2, v, wbfs.distTo(v));
                System.out.println(" ");
                int dist = vbfs.distTo(v) + wbfs.distTo(v);
                if (dist < min){
                    min = dist;
                    ancestor = v;
                }
            }
        }
    }
}
 