package specifications.properties;

import specifications.Structure;

public interface StructureProperty {
  boolean matches(Structure structure);

  String getName();
}
