package specifications.properties;

import specifications.Structure;

public class Semigroup extends Magma {
  @Override
  public boolean matches(Structure structure) {
    if (!super.matches(structure)) return false;
    int numberOfElements = structure.getNumberOfElements();
    for (int a = 0; a < numberOfElements; a++) {
      for (int b = 0; b < numberOfElements; b++) {
        for (int c = 0; c < numberOfElements; c++) {
          int ab = structure.operation(a, b);
          int bc = structure.operation(b, c);
          int a_bc = structure.operation(a, bc);
          int ab_c = structure.operation(ab, c);
          if (a_bc != ab_c) return false;
        }
      }
    }
    return true;
  }

  @Override
  public String getName() {
    return "Semigroup";
  }
}
