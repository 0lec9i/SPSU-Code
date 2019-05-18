package specifications.properties;

import specifications.Structure;

public class Utils {

  public static boolean isCommutative(Structure structure) {
    int numberOfElements = structure.getNumberOfElements();
    for (int a = 0; a < numberOfElements; a++) {
      for (int b = 0; b < numberOfElements; b++) {
        int ab = structure.operation(a, b);
        int ba = structure.operation(b, a);
        if (ab != ba) return false;
      }
    }
    return true;
  }
}
