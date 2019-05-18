package specifications.properties;

import specifications.Structure;

public class CommutativeMonoid extends Monoid {
  @Override
  public boolean matches(Structure structure) {
    if (!super.matches(structure)) return false;
    return Utils.isCommutative(structure);
  }

  @Override
  public String getName() {
    return "Commutative monoid";
  }
}
