package specifications;

import specifications.properties.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
  /**
   * Input is expected to have the following format:
   * 1) elements are zero-indexed:
   *    a0, a1, a2, ..., an
   * 2) input[i][j] = m means that
   *    ai * aj = am
   */

  private static List<StructureProperty> properties = Arrays.asList(
          new AbelianGroup(),
          new Group(),
          new CommutativeMonoid(),
          new Monoid(),
          new Semigroup(),
          new Magma()
  );

  public static void main(String[] args) {
    System.out.print("Please enter file path:");
    Scanner in = new Scanner(System.in);
    String fileName = in.nextLine();
    Structure structure;
    try {
      List<String> input = Files.readAllLines(Paths.get(fileName));
      Parser parser = new Parser(input);
      structure = parser.parse();
    } catch (IOException | NumberFormatException e) {
      System.out.println("Could not read data");
      return;
    }

    for (StructureProperty property : properties) {
      if (property.matches(structure)) {
        System.out.println(property.getName());
        break;
      }
    }
  }
}
