package specifications.properties;

import specifications.Structure;

public class Magma implements StructureProperty {
  @Override
  public boolean matches(Structure structure) {
    int numberOfElements = structure.getNumberOfElements();
    for (int i = 0; i < numberOfElements; i ++) {
      for (int j = 0; j < numberOfElements; j ++) {
        int ij = structure.operation(i, j);
        if (ij < 0 || ij >= numberOfElements) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public String getName() {
    return "Magma";
  }
}
