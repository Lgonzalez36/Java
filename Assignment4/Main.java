// Unit tests the DirectedCycle data type.
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        final String DELIMS = "[,]+";
        File inFile = new File("Data\\hypernyms.txt"); // for windows '\\' for linux i think its '//'
        Scanner scanner2 = new Scanner(inFile);
        Digraph G = new Digraph(83127);
        while (scanner2.hasNextLine()){
            String line = scanner2.nextLine();
            line = line.trim();
            String[] tokens = line.split(DELIMS);
            for (int i =1; i<tokens.length; i++){
                G.addEdge(0, i);
            }
        }
        scanner2.close();
        System.out.println("EDGES: " + G.E() + " VERTS: " + G.V());
        DirectedCycle finder = new DirectedCycle(G);
        if (finder.hasCycle()) {
            System.out.print("Directed cycle: ");
            for (int v : finder.cycle()) {
                System.out.print(v + " ");
            }
            System.out.println();
        }
        else 
            System.out.println("No directed cycle");
        System.out.println();
    }
}