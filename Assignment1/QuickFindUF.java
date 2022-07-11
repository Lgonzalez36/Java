public class QuickFindUF {
    private int[] id;    // id[i] = component identifier of i
    private int count;   // number of components
    
    // initialize an empty union-find data structure with elements 0 through n-1. 
    // initially, each element is in its own set.
    public QuickFindUF(int n) {
        count = n;
        id = new int[n];
        
        for (int i = 0; i < n; i++)
            id[i] = i;
    }
    
    // return the number of sets.
    public int count() {
        return count;
    }
  
    // return the canonical element of the set containing element p.
    public int find(int p) {
        validate(p);
        while ( p!= id[p]){
            p = id[p];
        }
        return id[p];
    }

    // validate that p is a valid index.
    private void validate(int p) {
        int n = id.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n-1));
        }
    }
    
    // return true if the two elements are in the same set.
    public boolean connected(int p, int q) {
        validate(p);
        validate(q);
        return id[p] == id[q];
    }
    
    // merge the set containing element p with the set containing element q.
    public void union(int p, int q) {
        validate(p);
        validate(q);
        
        int pID = id[p];   // needed for correctness
        int qID = id[q];   // to reduce the number of array accesses

        // p and q are already in the same component
        if (pID == qID) 
            return;

        for (int i = 0; i < id.length; i++)
            if (id[i] == pID) 
                id[i] = qID;
        count--;
    }
}