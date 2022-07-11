import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * Outcast Class for program 4
 * April 12, 2021
 * @author Luis Gonzalez
 */
public class Outcast {
    private WordNet wordn;

    /**
     * Ctor of the class
     * 
     * @param wordnet save the input graph to the
     * class graph
     */
    public Outcast(WordNet wordnet){
        this.wordn = wordnet;
    }

    /**
     * given an array of WordNet nouns, return an outcast
     * 
     * @param filename name of the file
     * @param fileSize size of the file
     * @return
     * @throws FileNotFoundException
     */
    public String outcast(String filename, int fileSize) throws FileNotFoundException{
        File inFile = new File(filename);
        Scanner input1 = new Scanner(inFile);
        String[] nouns = new String[fileSize];
        int icount=0;
        while(input1.hasNextLine()){
            nouns[icount] = input1.nextLine();
            icount++;
        }
        int maxDist = -1;
        int currentDist = -1;
        int index = 0;
        for (int i=0; i<nouns.length; i++){
            for (int j=i+1; j<nouns.length; j++){
                currentDist = wordn.distance(nouns[i], nouns[j]);
                if (currentDist > maxDist){
                    maxDist = currentDist;
                    index = j;
                }
            }
        }
        input1.close();
        System.out.println("\nMAX DISTANCE: " + maxDist);
        return nouns[index];
    }

    /**
     * From the Main menu, user enters the submenu
     * for OutCast to see output
     * @throws FileNotFoundException
     */
    public void enterOutCast() throws FileNotFoundException{
        Scanner menuInput = new Scanner(System.in);
        Scanner nounInput = new Scanner(System.in);
        String filename;
        int menuSelection;
        int fileSize;
        while (true) {
            System.out.println("\n************************************************************");
            System.out.println("     Welcome to OutCast Menu!! Here is the Main Menu        ");
            System.out.println("       Pick a File to see Which word does not Belong        ");
            System.out.println("************************************************************");
            System.out.println("outcast5.txt.............................................. 1");
            System.out.println("outcast8.txt.............................................. 2");
            System.out.println("outcast11.txt............................................. 3");
            System.out.println("Enter your own Words...................................... 4");
            System.out.println("Enter your own File ...................................... 5");
            System.out.println("Exit Program.............................................. 6");
            System.out.println("\n************************************************************");
            menuSelection = menuInput.nextInt();
            if (menuSelection == 1){
                filename = "Data\\outcast5.txt";
                System.out.println("OUTCAST: " + outcast(filename,5));
            }
            else if (menuSelection == 2){
                filename = "Data\\outcast8.txt";
                System.out.println("OUTCAST: " + outcast(filename,8));
            }
            else if (menuSelection == 3){
                filename = "Data\\outcast11.txt";
                System.out.println("OUTCAST: " + outcast(filename,11));
            }
            else if (menuSelection == 4){
                System.out.println("Working on it.... stay tune");
            }
            else if (menuSelection == 5){
                System.out.print("Enter the file name: ");
                filename = nounInput.nextLine();
                System.out.print("Enter the file size: ");
                fileSize = menuInput.nextInt();
                System.out.println("OUTCAST: " + outcast(filename,5));
            }
            else if (menuSelection == 6){
                break;
            }
            else{
                System.out.println("Invalid input: Try again!\n");
            }
        }
    }
    

    // test client (see below)
    public static void main(String[] args) throws FileNotFoundException {
    WordNet wordnet = new WordNet("Data\\synsets.txt", "Data\\hypernyms.txt");
    Outcast outcast = new Outcast(wordnet);
    
    for (int t = 0; t < 3; t++) {
        System.out.print("Enter the file name: ");
        Scanner input = new Scanner(System.in);
        String fileName = input.nextLine();
        System.out.print("Enter the file size: ");
        int fileSize = input.nextInt();
            File inFile = new File(fileName);
            Scanner input1 = new Scanner(inFile);
            String[] nouns = new String[fileSize];
            int i=0;
            while(input1.hasNextLine()){
                nouns[i] = input1.nextLine();
                i++;
            }
            System.out.println("OUTCAST: " + outcast.outcast(fileName, fileSize));
        }
    }

}
