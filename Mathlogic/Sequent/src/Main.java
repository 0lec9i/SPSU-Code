import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static final String FILENAME = "output.txt";

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        ArrayList<Formula> list = new ArrayList<>();
        Parser parser = new Parser();
        list.add(parser.parse(in.nextLine()));
        Sequent s = new Sequent(new ArrayList<>(), list);
        Evaluator eval = new Evaluator(FILENAME);
        eval.solve(s, parser.getVariables());
    }
}
