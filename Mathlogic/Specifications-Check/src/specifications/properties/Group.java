package specifications.properties;

import specifications.Structure;

public class Group extends Monoid {
  @Override
  public boolean matches(Structure structure) {
    if (!super.matches(structure)) return false;
    int identityElement = findIdentityElement(structure);
    for (int a = 0; a < structure.getNumberOfElements(); a++) {
      if (!hasInverseElement(a, identityElement, structure)) return false;
    }
    return true;
  }

  private boolean hasInverseElement(int a, int identityElement, Structure structure) {
    for (int b = 0; b < structure.getNumberOfElements(); b++) {
      int ab = structure.operation(a, b);
      int ba = structure.operation(b, a);
      if (ab == identityElement && ba == identityElement) return true;
    }
    return false;
  }

  @Override
  public String getName() {
    return "Group";
  }
}
