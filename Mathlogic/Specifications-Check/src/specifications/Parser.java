package specifications;

import java.util.Arrays;
import java.util.List;

public class Parser {
  private List<String> lines;

  Parser(List<String> lines) {
    this.lines = lines;
  }

  Structure parse() {
      int[][] result = lines.stream()
              .map(line -> line.split(" "))
              .map(arrayOfStrings -> Arrays.stream(arrayOfStrings)
                      .mapToInt(Integer::parseInt)
                      .toArray())
              .toArray(int[][]::new);
      return Structure.fromTable(result);
  }
}
