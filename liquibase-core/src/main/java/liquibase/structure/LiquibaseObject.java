package liquibase.structure;

import liquibase.ExtensibleObject;

public interface LiquibaseObject extends Comparable, ExtensibleObject {

    String getObjectTypeName();

    String getName();

    boolean snapshotByDefault();

    ObjectReference toReference();
}

