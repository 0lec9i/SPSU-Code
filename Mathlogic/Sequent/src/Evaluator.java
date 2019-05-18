import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class Evaluator {

    private FileWriter out;
    private Queue<Pair<Sequent, Integer>> workQueue;

    Evaluator(String filename) throws IOException {
        workQueue = new LinkedList<>();
        out = new FileWriter(filename);
    }

    private void printToFile(String str) throws IOException {
        out.write(str);
    }

    void solve(Sequent sequent, Set<String> variables) throws IOException {
        // next free node index
        int nodeCounter = 1;
        printToFile("digraph G {\nrankdir = BT;\n");
        printToFile("node0 [label = \"" + sequent.print() + "\"]\n");
        workQueue.add(new Pair<>(sequent, 0));
        Sequent current;
        Pair<Sequent, Integer> currentPair;
        String counterexample = "";
        Pair<Sequent, Sequent> parentSequent;
        boolean counterexampleFoundFlag = false;
        while (!workQueue.isEmpty()) {
            currentPair = workQueue.poll();
            current = currentPair.getFst();
            parentSequent = current.useRule();
            if (parentSequent == null) {
                String tmp = findCounterexample(current, variables);
                if (!tmp.equals("")) {
                    if (!counterexampleFoundFlag) {
                        counterexampleFoundFlag = true;
                        counterexample = tmp;
                        printToFile("node" + currentPair.getSnd() + " [color = \"red\"]\n");
                    } else {
                        printToFile("node" + currentPair.getSnd() + " [color = \"green\"]\n");
                    }
                } else {
                    printToFile("node" + currentPair.getSnd() + " [color = \"blue\"]\n");
                }
                continue;
            }

            int leftNodeNum = nodeCounter;
            nodeCounter++;
            printToFile("node" + leftNodeNum + " [label = \"" + parentSequent.getFst().print() + "\"]\n");
            printToFile("node" + currentPair.getSnd() + " -> " + "node" + leftNodeNum + ";\n");
            workQueue.add(new Pair<>(parentSequent.getFst(), leftNodeNum));
            if (parentSequent.getSnd() != null) {
                int rightNodeNum = nodeCounter;
                nodeCounter++;
                printToFile("node" + rightNodeNum + " [label = \"" + parentSequent.getSnd().print() + "\"]\n");
                workQueue.add(new Pair<>(parentSequent.getSnd(), rightNodeNum));
                printToFile("node" + currentPair.getSnd() + " -> " + "node" + rightNodeNum + ";\n");
            }
        }

        printToFile("}");

        if (counterexampleFoundFlag) {
            System.out.println("Counterexample exists! Counterexample: " + counterexample);
        } else {
            System.out.println("This formula is tautology!");
        }
        out.close();
    }

    /**
     * Invariant:
     * sequent should contain no logic operations
     */
    private String findCounterexample(Sequent sequent, Set<String> variables) {
        ArrayList<Formula> leftArg = sequent.getAntecedent();
        ArrayList<Formula> rightArg = sequent.getSucedent();
        sequent.deleteDuplicates();
        for (Formula formula1 : leftArg) {
            for (Formula formula : rightArg) {
                if (formula1.equals(formula)) {
                    return "";
                }
            }
        }
        String counterexample = "True: ";
        for (Formula formula : leftArg) {
            counterexample += formula.print() + " ";
            variables.remove(formula.print());
        }
        counterexample += "| False: ";
        for (Formula formula : rightArg) {
            counterexample += formula.print() + " ";
            variables.remove(formula.print());
        }
        for (String item : variables) {
            counterexample += item + " ";
        }
        System.out.println(counterexample);
        return counterexample;
    }
}
