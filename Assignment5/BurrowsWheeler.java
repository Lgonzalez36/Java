public class BurrowsWheeler {
    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform(){
        String s = "ABRACADABRA!"; //BinaryStdIn.readString();
        CircularSuffixArray csa = new CircularSuffixArray(s);
        int length = csa.length();
        for (int i = 0; i < length; i++) {
            if (csa.index(i) == 0) {
                BinaryStdOut.write(i);
                break;
            }
        }
        for (int i = 0; i < length; i++) {
            BinaryStdOut.write(s.charAt((csa.index(i) + length - 1) % length));
        }
        BinaryStdOut.flush();
        MoveToFront.encode();
    }
    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform(){
    }
    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        String par =  "-"; //BinaryStdIn.readString();
        if (par.equals("-")) {
            transform();
        }
        if (par.equals("+")) {
            inverseTransform();
        }
    }
}