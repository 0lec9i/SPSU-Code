package specifications;

public class Structure {
  private int numberOfElements;
  private int[][] CayleyTable;

  private Structure(int numberOfElements, int[][] CayleyTable) {
    this.numberOfElements = numberOfElements;
    this.CayleyTable = CayleyTable;
  }

  public int getNumberOfElements() {
    return numberOfElements;
  }

  public int operation(int a, int b) {
    return CayleyTable[a][b];
  }

  static Structure fromTable(int[][] CayleyTable) {
    int size = CayleyTable.length;
    checkBounds(size, CayleyTable);
    return new Structure(size, CayleyTable);
  }

  private static void checkBounds(int size, int[][] CayleyTable) {
    for (int[] line : CayleyTable) {
      if (line.length != size) {
        throw new IllegalArgumentException("Multiplication table is not square");
      }
    }
  }
}
