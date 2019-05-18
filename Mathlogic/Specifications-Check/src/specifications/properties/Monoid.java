package specifications.properties;

import specifications.Structure;

public class Monoid extends Semigroup {
  @Override
  public boolean matches(Structure structure) {
    if (!super.matches(structure)) return false;
    return findIdentityElement(structure) != null;
  }

  Integer findIdentityElement(Structure structure) {
    for (int element = 0; element < structure.getNumberOfElements(); element++) {
      if (isIdentity(element, structure)) {
        return element;
      }
    }
    return null;
  }

  private boolean isIdentity(int e, Structure structure) {
    for (int a = 0; a < structure.getNumberOfElements(); a++) {
      int ea = structure.operation(e, a);
      int ae = structure.operation(a, e);
      if (ea != a || ae != a) return false;
    }
    return true;
  }

  @Override
  public String getName() {
    return "Monoid";
  }
}
