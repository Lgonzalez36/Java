import java.io.File;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
/**
 * WordNet Class for program 4
 * April 12, 2021
 * @author Luis Gonzalez
 */
public class WordNet {
    final static String DELIMS1 = "[,]+";
    final static String DELIMS2 = "[ ]+";
    private Digraph graph;
    private ShortestCommonAncestor sca;
    private int totalSynsets;
    private boolean Initialized;
    SeparateChainingHashST<String, Queue<Integer>> wordToIntegerMap;
	SeparateChainingHashST<Integer, String> integerToWordMap;

    /**
     * The Ctor for WordNet
     * The Ctor Takes in two files as arguments and reads
     * and process the two files
     * my files were under a Data folder. delete Data\\ part in main to test 
     * files in the same folder of program.java
     * @param file1 synset.txt
     * @param file2 hypernyms.txt
     * @throws FileNotFoundException if files are not found
     */
    public WordNet(String file1, String file2) throws FileNotFoundException {
        if (file1 == null || file2 == null) throw new NullPointerException("Files can't be null");
        File synsetFile = new File(file1);
        Scanner scanner = new Scanner(synsetFile);
        totalSynsets = 0;
        boolean Initialized = false;
        wordToIntegerMap = new SeparateChainingHashST<>();
		integerToWordMap = new SeparateChainingHashST<>();

        // Reads in synset.txt first and stores them in two ST
        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split(DELIMS1);
			String[] words = line[1].split(DELIMS2); // the line
            Integer numberVal = Integer.valueOf(line[0]);
            integerToWordMap.put(Integer.valueOf(line[0]), line[1]);
            for (int i = 0; i < words.length; i++) {
				Queue<Integer> wordToIntegerMapQueue = wordToIntegerMap.get(words[i]);
				if (wordToIntegerMapQueue == null) {
					wordToIntegerMapQueue = new PriorityQueue<>();
					wordToIntegerMapQueue.add(numberVal);
					wordToIntegerMap.put(words[i], wordToIntegerMapQueue);
				}
				else {
					if (!contains(wordToIntegerMapQueue, numberVal)) {
						wordToIntegerMapQueue.add(numberVal);
					}
				}
			}
            totalSynsets++;
        }
        scanner.close();
        File hypernyms = new File(file2);
        Scanner scanner2 = new Scanner(hypernyms);
        graph = new Digraph(totalSynsets);

        // Reads hypernyms.txt and adds them to the graph
        while (scanner2.hasNextLine()){
            String line = scanner2.nextLine();
            String[] tokens = line.split(DELIMS1);
            for (int i =1; i<tokens.length; i++){
                graph.addEdge(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[i]));
            }
        }
        scanner2.close();
    }

    /**
     * Method returns the total number of Synsets
     * @return totalSynsets
     */
    public int getTotalSynsets(){
        return this.totalSynsets;
    }

    /**
     * Method gets the boolean value
     * if SCA has been initialized
     * @return boolean
     */
    public boolean getInitialized(){
        return this.Initialized;
    }

    /**
     * Method returns the SCA object
     * @return sca
     */
    public ShortestCommonAncestor getsca(){
        return this.sca;
    }

    /**
     * Checks to see if word is in the queue
     * 
     * @param wordToIntegerMapQueue the queue
     * @param numberVal query number to check
     * @return boolean if found or not
     */
    private boolean contains(Queue<Integer> wordToIntegerMapQueue, Integer numberVal) {
        for (Integer query : wordToIntegerMapQueue)
			if (query == numberVal) return true;
		return false;
    }

    /**
     * Finds all the keys(index) of a word in all the synets 
     * 
     * @return Iterable of of the keys
     */
    public Iterable<String> nouns(){
        return wordToIntegerMap.keys();
    }
 
    /**
     * Checks to see if word is in the ST
     * 
     * @param word
     * @return boolean if found or not
     */
    public boolean isNoun(String word){
        return wordToIntegerMap.contains(word);
    }
 
    /**
     * Method calls getancestor to get the numeric value 
     * and find the noun
     * 
     * @param noun1 first word
     * @param noun2 second word
     * @return string of a word that is the sca
     */
    public String sca(String noun1, String noun2){
        return integerToWordMap.get(sca.getancestor());
    }
 
    // distance between noun1 and noun2 (defined below)
    /**
     * Method first checks to see if input nouns are in the wordNet
     * Then initializes a new sca with the graph
     * and gets all they subsets of whcih the nouns occur in
     * then sends two subsets to the SCA
     * 
     * @param noun1 first noun
     * @param noun2 second noun
     * @return the min distance from two subsets
     */
    public int distance(String noun1, String noun2){
        if (!isNoun(noun1)){
            System.out.println(noun1 + " is not a noun in WordNet.. sorry try Another! ");
            return -1; 
        }
        if (!isNoun(noun2)){
            System.out.println(noun2 + " is not a noun in WordNet.. sorry try Another! ");
            return -1; 
        }
        else {
            this.sca = new ShortestCommonAncestor(graph);
            Initialized = true;
            return sca.lengthSubset(wordToIntegerMap.get(noun1), wordToIntegerMap.get(noun2));
        }
    }

    /**
     * Metho initializes the SCA class if it hasnt already
     * 
     */
    public void initializeSca(){
        this.sca = new ShortestCommonAncestor(graph);
        Initialized = true;
    }
 
    // unit testing (required)
    public static void main(String[] args) throws FileNotFoundException{
        WordNet wordn = new WordNet("Data\\synsets.txt", "Data\\hypernyms.txt");
        String Word1 = "concept";
        String Word2 = "shirt";
        System.out.println("\nFinding the shortest lenght between: " + Word1 + " and "+ Word2 );
        System.out.println( "\t\n >>> " + wordn.distance(Word1,Word2));
        System.out.println("\nFinding the shortest common Ancestor: " + Word1 + " and "+ Word2 );
        System.out.println( "\t\n >>> " + wordn.sca(Word1, Word2));


        String word = "edible_fruit";
        System.out.println("\nFinding the noun: " + word + " " + wordn.isNoun(word));


        System.out.print("Synsets of : " + Word1 + " >>> ");
		for (Integer i : wordn.wordToIntegerMap.get(word)){
			System.out.print(" " + i);
        }
        

        System.out.println(" ");
        System.out.println(" ");


        Integer number = 4821;
		int graphNumber = 69;


        System.out.println(number + ": " + wordn.integerToWordMap.get(number));
        for (int num : wordn.graph.adj(graphNumber)){
			System.out.print(" " + num);
        }
        System.out.println(" ");
        System.out.println(" ");
    }
}
 