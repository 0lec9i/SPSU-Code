import org.sat4j.minisat.SolverFactory;
import org.sat4j.reader.DimacsReader;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.reader.Reader;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

import java.io.*;
import java.util.List;

class TheoryMaker {

    private List<Cell> input;
    private static final int NUM_OF_CLAUSES = 14661;

    TheoryMaker(List<Cell> input) {
        this.input = input;
    }

    void generate() {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("generated.txt")))) {
            //head of file with number of variables and clauses
            writer.write("c This file is machine generated! Don't change it!");
            writer.newLine();
            writer.write("p cnf 729 " + (NUM_OF_CLAUSES + input.size()));
            writer.newLine();

            //p_{i, j, 1} v ... v p_{i, j, 9} = 1 forall i, j
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    for (int k = 1; k < 10; k++) {
                        writer.write(Cell.encode(i, j, k) + " ");
                    }
                    writer.write("0");
                    writer.newLine();
                }
            }


            //p_{i, j, k} -> !p_{i, j, t} forall i, j, k, t!=j
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    for (int k = 1; k < 10; k++) {
                        for (int t = 1; t < 10; t++) {
                            if (t == k) {
                                break;
                            } else {
                                writer.write("-" + Cell.encode(i, j, k) + " " + "-" + Cell.encode(i, j, t) + " 0");
                                writer.newLine();
                            }
                        }
                    }
                }
            }

            //p_{i, j, k} -> !p_{i, p, k} i = 0, 1, 2; j = 0, 1, 2; i != t; j != p;
            int q = 0, s = 0;
            while (q <= 6) {
                while (s <= 6) {
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            for (int t = 0; t < 3; t++) {
                                for (int p = 0; p < 3; p++) {
                                    for (int k = 1; k < 10; k++) {
                                        if ((i + q) != (t + q) || (j + s) != (p + s)) {
                                            writer.write("-" + Cell.encode(i + q, j + s, k) + " " + "-" + Cell.encode(t + q, p + s, k) + " 0");
                                            writer.newLine();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    s += 3;
                }
                s = 0;
                q += 3;
            }

            //p_{i, j, k} -> !p_{i, t, k} forall i, j, k, t!=j
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    for (int t = 0; t < 9; t++) {
                        if (j == t) {
                            break;
                        }
                        for (int k = 1; k < 10; k++) {
                            writer.write("-" + Cell.encode(i, j, k) + " " + "-" + Cell.encode(i, t, k) + " 0");
                            writer.newLine();
                        }
                    }
                }
            }

            //p_{i, j, k} -> !p_{t, j, k} forall i, j, k, t!=i
            for (int i = 0; i < 9; i++) {
                for (int t = 0; t < 9; t++) {
                    if (i == t) {
                        break;
                    }
                    for (int j = 0; j < 9; j++) {
                        for (int k = 1; k < 10; k++) {
                            writer.write("-" + Cell.encode(i, j, k) + " " + "-" + Cell.encode(t, j, k) + " 0");
                            writer.newLine();
                        }
                    }
                }
            }

            for (Cell c : input) {
                writer.write(Cell.encode(c.getRow(), c.getColumn(), c.getDigit()) + " 0");
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void solve() {
        ISolver solver = SolverFactory.newDefault();
        solver.setTimeout(3600);
        Reader reader = new DimacsReader(solver);
        try {
            IProblem problem = reader.parseInstance("generated.txt");
            if (problem.isSatisfiable()) {
                System.out.println("Satisfiable !");
                Parser.print(problem.model(), "output.txt");
            } else {
                System.out.println("Unsatisfiable !");
            }
        } catch (ParseFormatException | IOException e) {
            e.printStackTrace();
        } catch (ContradictionException e) {
            System.out.println("Unsatisfiable (trivial)!");
        } catch (
                TimeoutException e) {
            System.out.println("Timeout, sorry!");
        }
    }
}