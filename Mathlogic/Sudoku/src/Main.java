import java.io.PrintWriter;

public class Main {

    public static void main(String[] args) {
        Parser parser = new Parser();
        TheoryMaker theoryMaker = new TheoryMaker(parser.getInput("input.txt"));
        theoryMaker.generate();
        PrintWriter out = new PrintWriter(System.out, true);
        theoryMaker.solve();
        out.close();
    }
}
