/******************************************************************************
 *  Run breadth-first search on a digraph.
 *  Runs in O(E + V) time.
 ******************************************************************************/
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
 
/**
 *  The BreadthDirectedFirstPaths class represents a data type for
 *  finding shortest paths (number of edges) from a source vertex s
 *  (or set of source vertices) to every other vertex in the digraph.
 *  <p>
 *  This implementation uses breadth-first search.
 *  The constructor takes Î¸(V + E) time in the
 *  worst case, where V is the number of vertices and E is
 *  the number of edges.
 *  Each instance method takes Î¸(1) time.
 *  It uses Î¸(V) extra space (not including the digraph).
 */
public class BreadthFirstDirectedPaths {
    private static final int INFINITY = Integer.MAX_VALUE;
    private boolean[] marked;  // marked[v] = is there an s->v path?
    private int[] edgeTo;      // edgeTo[v] = last edge on shortest s->v path
    private int[] distTo;      // distTo[v] = length of shortest s->v path

    /**
     * Computes the shortest path from s and every other vertex in graph G.
     * @param G the digraph
     * @param s the source vertex
     * @throws IllegalArgumentException unless 0 <= v < V
     */
    public BreadthFirstDirectedPaths(Digraph G, int s) {
        marked = new boolean[G.V()];
        distTo = new int[G.V()];
        edgeTo = new int[G.V()];
        
        for (int v = 0; v < G.V(); v++)
            distTo[v] = INFINITY;
        validateVertex(s);
        bfs(G, s);
    }

    /**
     * Computes the shortest path from any one of the source vertices in sources
     * to every other vertex in graph G.
     * @param G the digraph
     * @param sources the source vertices
     * @throws IllegalArgumentException if sources is null
     * @throws IllegalArgumentException unless each vertex v in
     *         sources satisfies 0 <= v < V
     */
    public BreadthFirstDirectedPaths(Digraph G, Iterable<Integer> sources) {
        marked = new boolean[G.V()];
        distTo = new int[G.V()];
        edgeTo = new int[G.V()];
        
        for (int v = 0; v < G.V(); v++)
            distTo[v] = INFINITY;
        validateVertices(sources);
        bfs(G, sources);
    }

    // BFS from single source
    private void bfs(Digraph G, int s) {
        AbstractQueue<Integer> q = new LinkedBlockingQueue<Integer>();
        marked[s] = true;
        distTo[s] = 0;
        
        q.add(s);
        while (!q.isEmpty()) {
            int v = q.remove();
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    q.add(w);
                }
            }
        }
    }

    // BFS from multiple sources
    private void bfs(Digraph G, Iterable<Integer> sources) {
        AbstractQueue<Integer> q = new LinkedBlockingQueue<Integer>();
        for (int s : sources) {
            marked[s] = true;
            distTo[s] = 0;
            q.add(s);
        }
        
        while (!q.isEmpty()) {
            int v = q.remove();
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    q.add(w);
                }
            }
        }
    }

    /**
     * Is there a directed path from the source s (or sources) to vertex v?
     * @param v the vertex
     * @return true if there is a directed path, false otherwise
     * @throws IllegalArgumentException unless 0 <= v < V
     */
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return marked[v];
    }

    /**
     * Returns the number of edges in a shortest path from the source s
     * (or sources) to vertex v?
     * @param v the vertex
     * @return the number of edges in a shortest path
     * @throws IllegalArgumentException unless 0 <= v < V
     */
    public int distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }

    /**
     * Returns a shortest path from s (or sources) to v, or
     * null if no such path.
     * @param v the vertex
     * @return the sequence of vertices on a shortest path, as an Iterable
     * @throws IllegalArgumentException unless 0 <= v < V
     */
    public Iterable<Integer> pathTo(int v) {
        validateVertex(v);

        if (!hasPathTo(v)) 
            return null;
            
        Stack<Integer> path = new Stack<Integer>();
        int x;
        for (x = v; distTo[x] != 0; x = edgeTo[x])
            path.push(x);
        path.push(x);
        return path;
    }

    // throw an IllegalArgumentException unless 0 <= v < V
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    // throw an IllegalArgumentException if vertices is null, has zero vertices,
    // or has a vertex not between 0 and V-1
    private void validateVertices(Iterable<Integer> vertices) {
        if (vertices == null) {
            throw new IllegalArgumentException("argument is null");
        }
        
        // int V = marked.length;
        int count = 0;
        for (Integer v : vertices) {
            count++;
            if (v == null) {
                throw new IllegalArgumentException("vertex is null");
            }
            validateVertex(v);
        }
        
        if (count == 0) {
            throw new IllegalArgumentException("zero vertices");
        }
    }
}