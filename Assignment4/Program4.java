import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * Main client for program 4
 * April 12, 2021
 * @author Luis Gonzalez
 */
public class Program4 {
    public static void main(String[] args) throws FileNotFoundException {
        WordNet wordnet = new WordNet("Data\\synsets.txt", "Data\\hypernyms.txt");
        Outcast outcast = new Outcast(wordnet);
        Scanner menuInput = new Scanner(System.in);
        Scanner nounInput = new Scanner(System.in);
        while (true) {
            System.out.println("\n************************************************************");
            System.out.println("         Welcome to WordNet!! Here is the Main Menu         ");
            System.out.println("************************************************************");
            System.out.println("Find a Set of Synsets from a Noun......................... 1");
            System.out.println("Check if a Desired Noun is in WordNet..................... 2");
            System.out.println("Get the Shortest Common Ancestor from two Nouns........... 3");
            System.out.println("Find the Shortest Lenght from Two Nouns................... 4");
            System.out.println("Enter a Number 0-83126 to Print all Path from Vertex...... 5");
            System.out.println("Enter two Numbers to Find Shortest Path................... 6");
            System.out.println("Find the Least Common Word................................ 7");
            System.out.println("Exit Program.............................................. 8");
            System.out.println("\n************************************************************");
            int menuSelection = menuInput.nextInt();
            if (menuSelection == 1){
                printMenu1(wordnet, nounInput);
            }
            else if (menuSelection == 2){
                printMenu2(wordnet, nounInput);
            }
            else if (menuSelection== 3){
                printMenu3(wordnet, nounInput);
            }
            else if (menuSelection == 4){
                printMenu4(wordnet, nounInput);
            }
            else if (menuSelection == 5){
                System.out.println("Enter your source :\n");
                if (wordnet.getInitialized())
                    wordnet.getsca().printAllPathsFromV();
                else{
                    wordnet.initializeSca();
                    wordnet.getsca().printAllPathsFromV();
                }
            }
            else if (menuSelection == 6){
                if (wordnet.getInitialized())
                    printMenu6(wordnet, nounInput);
                else{
                    wordnet.initializeSca();
                    printMenu6(wordnet, nounInput);
                }
            }
            else if (menuSelection == 7){
                outcast.enterOutCast();
            }
            else if (menuSelection == 8){
                break;
            }
            else{
                System.out.println("Invalid input: Try again!\n");
            }
        }
        menuInput.close();
        nounInput.close();
    }

    static public void printMenu6(WordNet wordnet, Scanner nounInput){
        System.out.println("This Graph has " + wordnet.getTotalSynsets());
        System.out.println("Enter first Vertex 0 to " + wordnet.getTotalSynsets());
        int v = nounInput.nextInt();
        System.out.println("Enter second Vertex: ");
        int w = nounInput.nextInt();
        if ( v == w){
            System.out.println("Pick two Separate Vertices!!");
        }
        else{
            System.out.println("Distance between them: " + wordnet.getsca().length(v, w));
            System.out.println("Shortest Common Ancestor: " + wordnet.getsca().ancestor(v, w));
        }
    }

    static public void printMenu4(WordNet wordnet, Scanner nounInput){
        System.out.print("Enter first Noun: ");
        String noun1 = nounInput.nextLine();
        System.out.print("Enter second Noun: ");
        String noun2 = nounInput.nextLine();
        if ( noun1.equals(noun2)) {
            System.out.println("Pick Two Distint Words!!!");
        }
        else{
            System.out.println("Distance between them: " + wordnet.distance(noun1, noun2));
        }
    }

    static public void printMenu3(WordNet wordnet, Scanner nounInput){
        System.out.print("Enter first Noun: ");
        String noun1 = nounInput.nextLine();
        System.out.print("Enter second Noun: ");
        String noun2 = nounInput.nextLine();
        if ( noun1.equals(noun2)){
            System.out.println("Pick Two Distint Words!!!");
        }
        else{
            wordnet.distance(noun1, noun2);
            System.out.println("Shortest Common Ancestor: " + wordnet.sca(noun1, noun2));
        }
    }

    static public void printMenu2(WordNet wordnet, Scanner nounInput){
        System.out.println("Enter a Noun. Nouns are Case Sensitve and use _ Between Words: ");
        String noun = nounInput.nextLine();
        System.out.println("\n" + noun + " Found: " + wordnet.isNoun(noun));
    }

    static public void printMenu1(WordNet wordnet, Scanner nounInput){
        System.out.print("Enter a Noun: ");
        String noun = nounInput.nextLine();
        System.out.print("\nSynsets of : " + noun + " >>> ");
        for (Integer i : wordnet.wordToIntegerMap.get(noun)){
            System.out.print(" " + i);
        }
        System.out.print("\n");
        for (Integer i : wordnet.wordToIntegerMap.get(noun)){
            System.out.println( i + " : " + wordnet.integerToWordMap.get(i));
        }
    }
}
