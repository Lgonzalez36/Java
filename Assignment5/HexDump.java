/******************************************************************************
 *  Compilation:  javac HexDump.java
 *  Execution:    java HexDump < file
 *  Dependencies: BinaryStdIn.java 
 *  Data file:    abra.txt
 *  
 *  Reads in a binary file and writes out the bytes in hex, 16 per line.
 *
 *  % more abra.txt
 *  ABRACADABRA!
 *
 *  % java HexDump 16 < abra.txt
 *  41 42 52 41 43 41 44 41 42 52 41 21
 *  96 bits
 *
 ******************************************************************************/

/**
 *  The HexDump class provides a client for displaying the contents
 *  of a binary file in hexadecimal.
 */
public class HexDump {

    // Do not instantiate.
    private HexDump() { }

    /**
     * Reads in a sequence of bytes from standard input and writes
     * them to standard output using hexademical notation, k hex digits
     * per line, where k is given as a command-line integer (defaults
     * to 16 if no integer is specified); also writes the number
     * of bits.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        int bytesPerLine = 16;
        if (args.length == 1) {
            bytesPerLine = Integer.parseInt(args[0]);
        }

        int i;
        for (i = 0; !BinaryStdIn.isEmpty(); i++) {
            if (bytesPerLine == 0) {
                BinaryStdIn.readChar();
                continue;
            }
            if (i == 0) 
                System.out.printf("");
            else if (i % bytesPerLine == 0) 
                System.out.printf("\n", i);
            else 
                System.out.print(" ");
                
            char c = BinaryStdIn.readChar();
            System.out.printf("%02x", c & 0xff);
        }
        
        if (bytesPerLine != 0){
            System.out.println();
            System.out.println((i*8) + " bits");
        }
    }
}