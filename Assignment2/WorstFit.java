/**
 * Main class for Assignment 2 | WorstFit
 * March 4, 2020
 * @author Luis Gonzalez
 */
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

public class WorstFit {
    public int sum = 0;

    public static void main(String[] args) throws IOException {
        System.out.println("\nHello, Program 2!!\n");

        System.out.print("How many files do you want to add?: ");
        Scanner nFiles = new Scanner(System.in);
        int numFile = nFiles.nextInt();
        int randomSize;
        int sumOfFiles = 0;
        Random rand = new Random();
        File mainFile = new File("files.txt");

        if (!mainFile.exists()) {
        	mainFile.createNewFile();
        }
        try
        {   
            FileWriter fill = new FileWriter(mainFile);
            for (int i = 0; i < numFile; i++) { //puts in random ints for file sizes
                randomSize = rand.nextInt(1000001);
                sumOfFiles = sumOfFiles + randomSize;
                fill.write(randomSize + System.getProperty( "line.separator" ));
            }               
            fill.close();
            nFiles.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        double sumFileDub = sumOfFiles;
        DiskComparator cmp = new DiskComparator();
        ArrayList<Disk> diskArray = new ArrayList<Disk>();
        MaxPQ pq = new MaxPQ(numFile);
        Scanner scanner = new Scanner(new File("files.txt"));
        int numDisk = 0;

        while (scanner.hasNextInt()) {
            Key k = new Key(scanner.nextInt());
            pq.insert(k.getKey());
            if (k.moreThanHalf()) {
                Disk d = new Disk();
                diskArray.add(d);
                d.setID(numDisk);
                numDisk++;
            }
        }

        Iterator<Key> itr = pq.iterator();
        Key k = new Key();

        while (itr.hasNext()) {
            k = itr.next();
            for (int i=0; i< diskArray.size(); i++) {
                if ( (i == diskArray.size()-1)) {
                    Disk d2 = new Disk();
                    d2.addKeytoDisk(k);
                }
                if (diskArray.get(i).spaceLeft(k)) {
                    diskArray.get(i).addKeytoDisk(k);
                    break;
                }
            }
            //System.out.println(k.getKeyInt());
        }
        
        Collections.sort(diskArray, Collections.reverseOrder(cmp));
        System.out.println("_________________________________________________________________________\n");
        System.out.println("Sum of all files: " + (sumFileDub /1000000) + " GB");
        System.out.println("Disks used: " + diskArray.size());
        System.out.println("Maximum space left : " + Collections.max(diskArray, cmp).getspaceLeft() + "\n");
        
        if (numFile <= 100) {
            for (int i=0; i < diskArray.size(); i++) {
                System.out.print("Id: " + diskArray.get(i).getID()  + "\t" + diskArray.get(i).getspaceLeft()  + ":" );
                diskArray.get(i).printfiles();
                System.out.println("\n");
            }
        }
    }
}