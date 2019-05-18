package specifications.properties;

import specifications.Structure;

public class AbelianGroup extends Group {
  @Override
  public boolean matches(Structure structure) {
    if (!super.matches(structure)) return false;
    return Utils.isCommutative(structure);
  }

  @Override
  public String getName() {
    return "Abel group";
  }
}
