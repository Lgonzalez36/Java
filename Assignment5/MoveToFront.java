import java.util.LinkedList;

public class MoveToFront {
    public MoveToFront(){
    }

    // apply move-to-front encoding, reading from stdin and writing to stdout
    public static void encode(){
        LinkedList<Character> list = new LinkedList<Character>();
        for (int i = 0; i < 256; i++) {
            list.add((char) i);
        }
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar(8);
            // System.out.println(c);
            int idx = list.indexOf(c);
            BinaryStdOut.write(idx, 8);
            list.remove(idx);
            list.addFirst(c);
        }
        BinaryStdOut.flush();
    }
    // apply move-to-front decoding, reading from stdin and writing to stdout
    public static void decode(){

    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args){
        encode();

    }
}