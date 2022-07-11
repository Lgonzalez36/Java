/******************************************************************************
 *  Determine reachability in a digraph from a given vertex using
 *  depth-first search.
 *  Runs in O(E + V) time.
 ******************************************************************************/
import java.util.*;

/**
 *  The DepthFirstDirectedPaths class represents a data type for
 *  finding directed paths from a source vertex s to every
 *  other vertex in the digraph.
 *  <p>
 *  This implementation uses depth-first search.
 *  The constructor takes Î¸(V + E) time in the
 *  worst case, where V is the number of vertices and E
 *  is the number of edges.
 *  Each instance method takes Î¸(1) time.
 *  It uses Î¸(V) extra space (not including the digraph).
 */
public class DepthFirstDirectedPaths {
    private boolean[] marked;  // marked[v] = true iff v is reachable from s
    private int[] edgeTo;      // edgeTo[v] = last edge on path from s to v
    private final int s;       // source vertex

    /**
     * Computes a directed path from s to every other vertex in digraph G.
     * @param  G the digraph
     * @param  s the source vertex
     * @throws IllegalArgumentException unless 0 <= s < V
     */
    public DepthFirstDirectedPaths(Digraph G, int s) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        
        this.s = s;
        validateVertex(s);
        dfs(G, s);
    }

    private void dfs(Digraph G, int v) { 
        marked[v] = true;
        
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            }
        }
    }

    /**
     * Is there a directed path from the source vertex s to vertex v?
     * @param  v the vertex
     * @return true if there is a directed path from the source
     *         vertex s to vertex v, false otherwise
     * @throws IllegalArgumentException unless 0 <= v < V
     */
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return marked[v];
    }
    
    /**
     * Returns a directed path from the source vertex s to vertex v, or
     * null if no such path.
     * @param  v the vertex
     * @return the sequence of vertices on a directed path from the source vertex
     *         s to vertex v, as an Iterable
     * @throws IllegalArgumentException unless 0 <= v < V
     */
    public Iterable<Integer> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v)) 
            return null;
            
        Stack<Integer> path = new Stack<Integer>();
        for (int x = v; x != s; x = edgeTo[x])
            path.push(x);
        path.push(s);
        return path;
    }

    // throw an IllegalArgumentException unless 0 <= v < V
    private void validateVertex(int v) {
        int V = marked.length;
        
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }
}